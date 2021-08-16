package cn.maidaotech.smartapi.common.cache;

import cn.maidaotech.smartapi.common.L;
import cn.maidaotech.smartapi.common.task.TaskService;
import com.sunnysuperman.kvcache.*;
import com.sunnysuperman.kvcache.converter.ModelConverter;

public class KvCacheWrap<K, T> extends DefaultKvCache<K, T> {
    private TaskService taskService;

    public KvCacheWrap(KvCacheExecutor executor, KvCachePolicy policy, RepositoryProvider<K, T> repository,
                       ModelConverter<T> converter, KvCacheSaveFilter<K, T> saveFilter, TaskService taskService) {
        super(executor, policy, repository, converter, saveFilter);
        this.taskService = taskService;
    }

    public String getCacheKey(K key) {
        return super.makeFullKey(key);
    }

    private class RemoveCacheTask implements Runnable {
        K key;
        int retry;

        public RemoveCacheTask(K key, int retry) {
            super();
            this.key = key;
            this.retry = retry;
        }

        @Override
        public void run() {
            try {
                remove(key);
            } catch (Exception e) {
                LOG.error(null, e);
                retry++;
                if (retry < 10) {
                    taskService.scheduleTask(new RemoveCacheTask(key, retry), 100);
                } else {
                    L.error("Failed to explicitly remove cache: " + key);
                }
            }
        }

    }

    // 安全删除
    public void removeSafely(K id) {
        try {
            remove(id);
        } catch (Exception ex) {
            L.error(ex);
        }
        // 防止大并发情况下，删除缓存操作时，另一个线程（或者进程）获取到脏数据（老数据），并把脏数据保存到缓存里
        try {
            taskService.scheduleTask(new RemoveCacheTask(id, 0), 15000);
        } catch (Exception ex) {
            L.error(ex);
        }
    }

    // 静默保存
    public void saveQuietly(K key, T value) {
        try {
            save(key, value);
        } catch (Exception e) {
            L.error(e);
        }
    }

    // 安全保存
    public void saveSafely(K key, T value) {
        try {
            save(key, value);
        } catch (Exception ex) {
            L.error(ex);
            taskService.scheduleTask(new RefreshCacheTask(key, 0), 5000);
        }
    }

    // 安全刷新
    public void refreshSafely(K key) {
        try {
            refresh(key);
        } catch (Exception ex) {
            L.error(ex);
            taskService.scheduleTask(new RefreshCacheTask(key, 0), 5000);
        }
    }

    private class RefreshCacheTask implements Runnable {
        private K key;
        private int retry;

        public RefreshCacheTask(K key, int retry) {
            super();
            this.key = key;
            this.retry = retry;
        }

        @Override
        public void run() {
            try {
                refresh(key);
            } catch (Exception e) {
                LOG.error(null, e);
                retry++;
                if (retry < 10) {
                    taskService.scheduleTask(new RefreshCacheTask(key, retry), 100);
                } else {
                    L.error("Failed to explicitly refresh cache: " + key);
                }
            }
        }

    }

}
