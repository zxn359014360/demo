package com.utils;

import org.apache.commons.lang.math.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author zhangxn
 * @date 2018/6/5
 * Copyright 2018~2022
 * 功能说明：系统主键处理类
 */
public class IdUtil {

    public static String getId(){
        return  UUID.randomUUID().toString().replaceAll("-","");
    }


}
