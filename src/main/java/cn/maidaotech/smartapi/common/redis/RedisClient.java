package cn.maidaotech.smartapi.common.redis;

import cn.maidaotech.smartapi.common.utils.StringUtils;
import com.sunnysuperman.commons.util.ByteUtil;
import com.sunnysuperman.commons.util.FormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.*;

public class RedisClient {
    private static final Logger LOG = LoggerFactory.getLogger(RedisClient.class);
    private static final String INCREASE_IF_EXISTS = "local v=redis.call('exists', KEYS[1]);if(v==0) then return nil;else return redis.call('incrby', KEYS[1], ARGV[1]);end";

    public static interface RedisWork<T> {
        T run(Jedis jedis);
    }

    private static class TestRedisWork implements RedisWork<Void> {

        @Override
        public Void run(Jedis jedis) {
            jedis.expire("0", 1);
            return null;
        }

    }

    private JedisPool pool;

    public RedisClient(JedisPool pool, boolean testOnStart) {
        this.pool = pool;
        if (testOnStart) {
            execute(new TestRedisWork());
        }
    }

    public <T> T execute(RedisWork<T> work) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return work.run(jedis);
        } finally {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Throwable t) {
                    LOG.error(null, t);
                }
            }
        }
    }

    private static class ExpireWork implements RedisWork<Boolean> {
        String key;
        int seconds;

        public ExpireWork(String key, int seconds) {
            super();
            this.key = key;
            this.seconds = seconds;
        }

        @Override
        public Boolean run(Jedis jedis) {
            return jedis.expire(key, seconds) > 0;
        }

    }

    private static class ExpireAtWork implements RedisWork<Boolean> {
        String key;
        long expireAt;

        public ExpireAtWork(String key, long expireAt) {
            super();
            this.key = key;
            this.expireAt = expireAt;
        }

        @Override
        public Boolean run(Jedis jedis) {
            return jedis.expireAt(key, expireAt) > 0;
        }

    }

    private static class GetWork implements RedisWork<byte[]> {
        String key;

        public GetWork(String key) {
            super();
            this.key = key;
        }

        @Override
        public byte[] run(Jedis jedis) {
            return jedis.get(key.getBytes(StringUtils.UTF8_CHARSET));
        }

    }

    private static class SetWork implements RedisWork<String> {
        String key;
        byte[] value;
        int seconds;

        public SetWork(String key, byte[] value, int seconds) {
            super();
            this.key = key;
            this.value = value;
            this.seconds = seconds;
        }

        @Override
        public String run(Jedis jedis) {
            if (seconds > 0) {
                return jedis.setex(key.getBytes(StringUtils.UTF8_CHARSET), seconds, value);
            } else {
                return jedis.set(key.getBytes(StringUtils.UTF8_CHARSET), value);
            }
        }

    }

    public boolean rename(String oldkey, String newkey) {
        return execute(new RenameWork(oldkey, newkey));
    }

    private static class RenameWork implements RedisWork<Boolean> {
        private String oldkey;
        private String newkey;

        public RenameWork(String oldkey, String newkey) {
            super();
            this.oldkey = oldkey;
            this.newkey = newkey;
        }

        @Override
        public Boolean run(Jedis jedis) {
            return "OK".equals(jedis.rename(oldkey, newkey));
        }

    }

    private static class SetAddWork implements RedisWork<Boolean> {
        String key;
        String member;

        public SetAddWork(String key, String member) {
            super();
            this.key = key;
            this.member = member;
        }

        @Override
        public Boolean run(Jedis jedis) {
            return jedis.sadd(key, member) > 0;
        }

    }

    private static class SetRemoveWork implements RedisWork<Boolean> {
        String key;
        String member;

        public SetRemoveWork(String key, String member) {
            super();
            this.key = key;
            this.member = member;
        }

        @Override
        public Boolean run(Jedis jedis) {
            return jedis.srem(key, member) > 0;
        }

    }

    private static class SetIsMemberWork implements RedisWork<Boolean> {
        String key;
        String member;

        public SetIsMemberWork(String key, String member) {
            super();
            this.key = key;
            this.member = member;
        }

        @Override
        public Boolean run(Jedis jedis) {
            return jedis.sismember(key, member);
        }

    }

    public boolean expire(String key, int seconds) {
        return execute(new ExpireWork(key, seconds));
    }

    public boolean expireAt(String key, Date expireAt) {
        return execute(new ExpireAtWork(key, expireAt.getTime() / 1000));
    }

    public boolean del(String key) {
        return execute(new DelWork(key));
    }

    private static class DelWork implements RedisWork<Boolean> {
        String key;

        public DelWork(String key) {
            super();
            this.key = key;
        }

        @Override
        public Boolean run(Jedis jedis) {
            return jedis.del(key) > 0;
        }

    }

    public byte[] get(String key) {
        return execute(new GetWork(key));
    }

    public String getString(String key) {
        return ByteUtil.toString(execute(new GetWork(key)));
    }

    public void set(String key, byte[] value) {
        execute(new SetWork(key, value, 0));
    }

    public void setex(String key, byte[] value, int seconds) {
        execute(new SetWork(key, value, seconds));
    }

    public long incrby(String key, long num) {
        return execute(new IncrbyWork(key, num));
    }

    private static class IncrbyWork implements RedisWork<Long> {
        String key;
        long num;

        public IncrbyWork(String key, long num) {
            super();
            this.key = key;
            this.num = num;
        }

        @Override
        public Long run(Jedis jedis) {
            return jedis.incrBy(key, num);
        }

    }

    public Long incrbyIfExists(String key, long num) {
        return execute(new IncrbyIfExistsWork(key, num));
    }

    private static class IncrbyIfExistsWork implements RedisWork<Long> {
        String key;
        long num;

        public IncrbyIfExistsWork(String key, long num) {
            super();
            this.key = key;
            this.num = num;
        }

        @Override
        public Long run(Jedis jedis) {
            Object result = jedis.eval(INCREASE_IF_EXISTS, Arrays.asList(key),
                    Collections.singletonList(String.valueOf(num)));
            return FormatUtil.parseLong(result);
        }

    }

    public boolean sadd(String key, String member) {
        return execute(new SetAddWork(key, member));
    }

    public boolean srem(String key, String member) {
        return execute(new SetRemoveWork(key, member));
    }

    public boolean sismember(String key, String member) {
        return execute(new SetIsMemberWork(key, member));
    }

    public long scard(String key) {
        return execute(new ScardWork(key));
    }

    private static class ScardWork implements RedisWork<Long> {
        private String key;

        public ScardWork(String key) {
            super();
            this.key = key;
        }

        @Override
        public Long run(Jedis jedis) {
            return jedis.scard(key);
        }

    }

    public Long zadd(String key, Map<String, Double> scoreMembers, int expireIn) {
        return execute(new ZaddWork(key, scoreMembers, expireIn));
    }

    private static class ZaddWork implements RedisWork<Long> {
        String key;
        Map<String, Double> scoreMembers;
        int expireIn;

        public ZaddWork(String key, Map<String, Double> scoreMembers, int expireIn) {
            super();
            this.key = key;
            this.scoreMembers = scoreMembers;
            this.expireIn = expireIn;
        }

        @Override
        public Long run(Jedis jedis) {
            if (expireIn <= 0) {
                return jedis.zadd(key, scoreMembers);
            } else {
                Transaction transaction = jedis.multi();
                transaction.zadd(key, scoreMembers);
                transaction.expire(key, expireIn);
                transaction.exec();
                return 0L;
            }
        }
    }

    public long zcard(String key) {
        return execute(new ZcardWork(key));
    }

    private static class ZcardWork implements RedisWork<Long> {
        String key;

        public ZcardWork(String key) {
            super();
            this.key = key;
        }

        @Override
        public Long run(Jedis jedis) {
            return jedis.zcard(key);
        }

    }

    public Double zscore(String key, String member) {
        return execute(new ZscoreWork(key, member));
    }

    private static class ZscoreWork implements RedisWork<Double> {
        String key;
        String member;

        public ZscoreWork(String key, String member) {
            super();
            this.key = key;
            this.member = member;
        }

        @Override
        public Double run(Jedis jedis) {
            return jedis.zscore(key, member);
        }

    }

    private static class HincrByWork implements RedisWork<Long> {
        String key;
        String field;
        long incrBy;

        public HincrByWork(String key, String field, long incrBy) {
            super();
            this.key = key;
            this.field = field;
            this.incrBy = incrBy;
        }

        @Override
        public Long run(Jedis jedis) {
            return jedis.hincrBy(key, field, incrBy);
        }

    }

    public Long hincrBy(String key, String field, long incrBy) {
        return execute(new HincrByWork(key, field, incrBy));
    }

    private static class HsetWork implements RedisWork<Boolean> {
        String key;
        String field;
        String value;

        public HsetWork(String key, String field, String value) {
            super();
            this.key = key;
            this.field = field;
            this.value = value;
        }

        @Override
        public Boolean run(Jedis jedis) {
            return jedis.hset(key, field, value) > 0;
        }

    }

    public boolean hset(String key, String field, String value) {
        return execute(new HsetWork(key, field, value));
    }

    private static class HgetAllWork implements RedisWork<Map<String, String>> {
        String key;

        public HgetAllWork(String key) {
            super();
            this.key = key;
        }

        @Override
        public Map<String, String> run(Jedis jedis) {
            return jedis.hgetAll(key);
        }

    }

    public Map<String, String> hgetAll(String key) {
        return execute(new HgetAllWork(key));
    }

    public String scriptLoad(String script) {
        return execute(new ScriptLoadWork(script));
    }

    private static class ScriptLoadWork implements RedisWork<String> {
        String script;

        public ScriptLoadWork(String script) {
            super();
            this.script = script;
        }

        @Override
        public String run(Jedis jedis) {
            return jedis.scriptLoad(script);
        }

    }

    public Object scriptRun(String script, List<String> keys, List<String> args) {
        return execute(new ScriptRunWork(script, keys, args));
    }

    private static class ScriptRunWork implements RedisWork<Object> {
        String script;
        List<String> keys;
        List<String> args;

        public ScriptRunWork(String script, List<String> keys, List<String> args) {
            super();
            this.script = script;
            this.keys = keys;
            this.args = args;
        }

        @Override
        public Object run(Jedis jedis) {
            return jedis.eval(script, keys != null ? keys : Collections.emptyList(),
                    args != null ? args : Collections.emptyList());
        }

    }
}
