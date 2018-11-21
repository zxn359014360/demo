/*
* @(#)${serverName}.java
*
* Copyright 2017-2018, Inc. All rights reserved.
*
*/
package com.web.service.impl.${packageName};

/**
* Class <code>${serverName}Impl</code> is the root of the class hierarchy.
* Every class has <code>${serverName}Impl</code> as a superclass. All objects,
* including arrays, implement the methods of this class.
*
* @author   ${author}
* @version  ${createTime}
* @see      java.lang.Class
*/
@Service
public class ${serverName}ClientImpl extends CsCommonServiceImpl<${modelJava}, String> implements I${serverName}Client{

    @Autowired
    private I${serverName} ${updateStrserverJavastr};

    @Override
    public IWsCommonService<${modelJava}, String> getWsCommonService() {
        return ${updateStrserverJavastr};
    }

    /**
     * 分页
     *
     * @return
     */
    public PageResult<List<${modelJava}Vo>> getPageListByVo(${modelJava}VoReq ${modelValue}Vo, Integer page, Integer rows){
        return ${updateStrserverJavastr}.getPageListByVo(${modelValue}Vo,page,rows);
    }


        /**
        * 查询个数
        *
        * @return
        */
     public ${modelJava}VoCount getCountValue(${modelJava}VoReq ${modelValue}Vo){
        return ${updateStrserverJavastr}.getCountValue(${modelValue}Vo);
     }

        /**
        * 查询总计
        *
        * @return
        */
   public ${modelJava}VoSum getSumValue(${modelJava}VoReq ${modelValue}Vo){
        return ${updateStrserverJavastr}.getSumValue(${modelValue}Vo);
   }

        /**
        * 查询统计
        *
        * @return
        */
    public ${modelJava}VoStatistics getStatisticsValue(${modelJava}VoReq ${modelValue}Vo){
        return ${updateStrserverJavastr}.getStatisticsValue(${modelValue}Vo);
    }

}
