package com.utils.redis;


import com.alibaba.fastjson.JSON;
import com.utils.PropertiesUtils;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.record.formula.functions.T;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/***
 * 缓存对象 redis实现
 *
 * @author admin
 */
public class RedisCache implements CacheForKeyExpire {
    private static final String CACHE_IP = "redis.ip";
    private static final String CACHE_PORT = "redis.port";
    private static final String CACHE_PASSWARD = "redis.password";
    private JedisPool jedisPool = null;
    private static volatile RedisCache cache;

    protected Jedis getJedisNew() {
        return jedisPool.getResource();
    }

    protected void returnJedis(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }

    /**
     * 双重检查单例模式
     *
     * Double-Check概念对于多线程开发者来说不会陌生，
     * 如代码中所示，我们进行了两次if (singleton == null)检查，
     * 这样就可以保证线程安全了。这样，实例化代码只用执行一次，
     * 后面再次访问时，判断if (singleton == null)，直接return实例化对象。
     *
     * 优点：线程安全；延迟加载；效率较高。
     */
    public static RedisCache getInstance(){
        if (cache == null) {
            synchronized (RedisCache.class) {
                if (cache == null) {
                    cache = new RedisCache(PropertiesUtils.getGlobalProperties(CACHE_IP), Integer.parseInt(PropertiesUtils.getGlobalProperties(CACHE_PORT)), PropertiesUtils.getGlobalProperties(CACHE_PASSWARD));
                }
            }
        }
        return cache;
    }

    public RedisCache(String host, int port, String password) {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxActive(30000);
        config.setMaxIdle(300);
        config.setTestOnBorrow(true);
        config.setMaxWait(30000);

        jedisPool = new JedisPool(config, host, port, 20000, password);
    }



    @Override
    public void exprie(String key, int expire) {
        String tempKey = CacheForKeyExpire.ALL_KEYEXPIRE + key;
        Jedis jedis = getJedisNew();
        jedis.expire(tempKey, expire);
        returnJedis(jedis);
    }

    @Override
    public void addKeyExpire(String key, String val, int expire) {
        Jedis jedis = getJedisNew();
        jedis.del(CacheForKeyExpire.ALL_KEYEXPIRE + key);
        jedis.set(CacheForKeyExpire.ALL_KEYEXPIRE + key, val);
        jedis.expire(CacheForKeyExpire.ALL_KEYEXPIRE + key, expire);
        returnJedis(jedis);
    }

    @Override
    public String getKeyExpire(String key) {
        Jedis jedis = getJedisNew();
        String jsonString = jedis.get(CacheForKeyExpire.ALL_KEYEXPIRE + key);
        returnJedis(jedis);
        return jsonString;
    }

    @Override
    public void clearExValByKey(String exKey) {
        Jedis jedis = getJedisNew();
        Set<String> keys = jedis.keys(CacheForKeyExpire.ALL_KEYEXPIRE + exKey);
        for (String key : keys) {
            jedis.del(key);
        }
        returnJedis(jedis);
    }

    @Override
    public void addKeyExpireObject(String key, Object val, int expire) {
        Jedis jedis = getJedisNew();
        jedis.del(CacheForKeyExpire.ALL_KEYEXPIRE + key);
        String keystr = JSONObject.fromObject(val).toString();
        jedis.set(CacheForKeyExpire.ALL_KEYEXPIRE + key, keystr);
        jedis.expire(CacheForKeyExpire.ALL_KEYEXPIRE + key, expire);
        returnJedis(jedis);
    }


    @Override
    public Object getKeyExpireObject(String key, Class clazz) {
        Jedis jedis = getJedisNew();
        String value = jedis.get(CacheForKeyExpire.ALL_KEYEXPIRE + key);
        returnJedis(jedis);
        Object entity = com.alibaba.fastjson.JSONObject.parseObject(value, clazz);
        return entity;
    }

    @Override
    public void addKeyExpireList(String key, List<?> list, int expire) {
        Jedis jedis = getJedisNew();
        jedis.del(CacheForKeyExpire.ALL_KEYEXPIRE + key);
        String keystr = JSON.toJSONString(list);
        jedis.set(CacheForKeyExpire.ALL_KEYEXPIRE + key, keystr);
        jedis.expire(CacheForKeyExpire.ALL_KEYEXPIRE + key, expire);
        returnJedis(jedis);
    }

    @Override
    public List getKeyExpireList(String key, Class clazz) {
        Jedis jedis = getJedisNew();
        String value = jedis.get(CacheForKeyExpire.ALL_KEYEXPIRE + key);
        returnJedis(jedis);
        List entity = com.alibaba.fastjson.JSONArray.parseArray(value, clazz);
        return entity;
    }

    @Override
    public void addKeyObject(String key, Object val) {
        Jedis jedis = getJedisNew();
        jedis.del(CacheForKeyExpire.ALL_KEYEXPIRE + key);
        String keystr = JSONObject.fromObject(val).toString();
        jedis.set(CacheForKeyExpire.ALL_KEYEXPIRE + key, keystr);
        returnJedis(jedis);
    }

    @Override
    public Object getKeyObject(String key, Class clazz) {
        Jedis jedis = getJedisNew();
        String value = jedis.get(CacheForKeyExpire.ALL_KEYEXPIRE + key);
        returnJedis(jedis);
        Object entity = com.alibaba.fastjson.JSONObject.parseObject(value, clazz);
        return entity;
    }

    @Override
    public void addKeyVal(String key, String val) {
        Jedis jedis = getJedisNew();
        jedis.set(CacheForKeyExpire.PC_HOMEPAGE_KEY + key, val);

        returnJedis(jedis);
    }

    @Override
    public String getKeyVal(String key) {
        Jedis jedis = getJedisNew();
        String jsonString = jedis.get(CacheForKeyExpire.PC_HOMEPAGE_KEY + key);
        returnJedis(jedis);
        return jsonString;
    }

    @Override
    public void clearValByKey(String key) {
        Jedis jedis = getJedisNew();
        jedis.del(CacheForKeyExpire.PC_HOMEPAGE_KEY + key);
        returnJedis(jedis);
    }

    @Override
    public void clearKeyVal() {
        Jedis jedis = getJedisNew();
        Set<String> keys = jedis.keys(CacheForKeyExpire.PC_HOMEPAGE_KEY + "*");
        for (String key : keys) {
            jedis.del(key);
        }
        returnJedis(jedis);
    }


    @Override
    public Long getKeyExpireTime(String key) {
        Jedis jedis = getJedisNew();
        Long jsonString = jedis.ttl(CacheForKeyExpire.ALL_KEYEXPIRE + key);
        returnJedis(jedis);
        return jsonString;

    }


    /**
     * 向map里面存key value
     *
     * @param mapKey     Map的key
     * @param mapInKey   Map里面的Key
     * @param mapInValue Map里面的Value
     * @return
     */
    @Override
    public Long putInMap(String mapKey, String mapInKey, String mapInValue) {
        Jedis jedis = getJedisNew();
        Long jsonString = jedis.hset(CacheForKeyExpire.PC_HOMEPAGE_KEY + mapKey, mapInKey, mapInValue);
        returnJedis(jedis);
        return jsonString;
    }

    /**
     * 获取map里面的value
     *
     * @param mapKey   Map的key
     * @param mapInKey Map里面的Key
     * @return
     */
    @Override
    public String getInMap(String mapKey, String mapInKey) {
        Jedis jedis = getJedisNew();
        String jsonString = jedis.hget(CacheForKeyExpire.PC_HOMEPAGE_KEY + mapKey, mapInKey);
        returnJedis(jedis);
        return jsonString;
    }

    /**
     * 存储整个Map
     *
     * @param mapKey
     * @param map
     * @return
     */
    @Override
    public String setMap(String mapKey, Map map) {
        Jedis jedis = getJedisNew();
        String jsonString = jedis.hmset(CacheForKeyExpire.PC_HOMEPAGE_KEY + mapKey, map);
        returnJedis(jedis);
        return jsonString;
    }

    /**
     * 删除Map中的value
     *
     * @param mapKey   cache map key
     * @param mapInKey map里面value对应的key
     * @return
     */
    @Override
    public Long delValInMap(String mapKey, String mapInKey) {
        Jedis jedis = getJedisNew();
        Long jsonString = jedis.hdel(CacheForKeyExpire.PC_HOMEPAGE_KEY + mapKey, mapInKey);
        returnJedis(jedis);
        return jsonString;
    }

    /**
     * 获取caceh中的map
     *
     * @param mapKey
     * @return
     */
    @Override
    public Map getMap(String mapKey) {
        Jedis jedis = getJedisNew();
        Map jsonString = jedis.hgetAll(CacheForKeyExpire.PC_HOMEPAGE_KEY + mapKey);
        returnJedis(jedis);
        return jsonString;
    }


    /**
     * 对应key值得缓存的值，变化changeValue
     *
     * @param key
     */
    private void incrWithKey(String key, int changeValue) {
        Jedis jedis = getJedisNew();
        jedis.incrBy(key, changeValue);
        returnJedis(jedis);
    }


    @Override
    public void incrByKey(String key, int changeValue) {
        String tmepKey = CacheForKeyExpire.PC_HOMEPAGE_KEY + key.trim();
        incrWithKey(tmepKey, changeValue);
    }


}
