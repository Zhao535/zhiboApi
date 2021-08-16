package cn.maidaotech.smartapi.common.cache;

import com.sunnysuperman.kvcache.RepositoryProvider;

import java.util.Collection;
import java.util.Map;

public abstract class SingleRepositoryProvider<K, V> implements RepositoryProvider<K, V> {

    @Override
    public final Map<K, V> findByKeys(Collection<K> keys) throws Exception {
        throw new UnsupportedOperationException("findByKeys");
    }

}
