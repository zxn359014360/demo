/*
* @(#)I${serverName}.java
*
* Copyright 2017-2018, Inc. All rights reserved.
*
*/
package com.web.service.inteface.${packageName};
import com.common.framework.page.PageResult;
import com.model.${packageName}.${modelJava};
import com.vo.${packageName}.${modelJava}Vo;
import com.vo.${packageName}.${modelJava}VoReq;
import com.vo.${packageName}.${modelJava}VoCount;
import com.vo.${packageName}.${modelJava}VoSum;
import com.vo.${packageName}.${modelJava}VoStatistics;
import com.common.service.ICsCommonService;
import java.util.List;
/**
* Class <code>I${serverName}Client</code> is the root of the class hierarchy.
* Every class has <code>I${serverName}Client</code> as a superclass. All objects,
* including arrays, implement the methods of this class.
*
* @author   ${author}
* @version  ${createTime}
* @see      java.lang.Class
*/
public interface I${serverName}Client extends ICsCommonService<${modelJava},String>{
/**
* 分页
*
* @return
*/
public PageResult<List<${modelJava}Vo>> getPageListByVo(${modelJava}VoReq ${modelValue}Vo, Integer page, Integer rows);


    /**
    * 查询个数
    *
    * @return
    */
    public ${modelJava}VoCount getCountValue(${modelJava}VoReq ${modelValue}Vo);

    /**
    * 查询总计
    *
    * @return
    */
    public ${modelJava}VoSum getSumValue(${modelJava}VoReq ${modelValue}Vo);

    /**
    * 查询统计
    *
    * @return
    */
    public ${modelJava}VoStatistics getStatisticsValue(${modelJava}VoReq ${modelValue}Vo);


}
