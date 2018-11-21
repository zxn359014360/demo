package com.utils.redis;



public final class CacheUtils {
    /**
     * 添加带失效时间的存储内容
     *
     * @return
     */
    public static CacheForKeyExpire getCacheForKeyExpire() {
        return RedisCache.getInstance();
    }

    protected static RedisCache getRedisCache() {
        return RedisCache.getInstance();
    }
}
