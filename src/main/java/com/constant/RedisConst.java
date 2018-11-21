package com.constant;

public class RedisConst {

    public static class ExpireTime{
        public static final int ONE_DAY = 24 * 60 * 60;//保存1天
        public static final int ONE_WEEK = 24 * 60 * 60;//保存1天
        public static final int HALF_HOUT = 30 * 60;//保存半小时
        public static final int ONE_HOUT = 60 * 60;//保存半小时
    }

    public static class CacheKey{
        public static final String USER = "User_";//商户扩展信息
    }
}
