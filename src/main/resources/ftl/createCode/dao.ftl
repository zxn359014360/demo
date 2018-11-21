/*
* @(#)${daoJava}.java
*
* Copyright 2017-2018, Inc. All rights reserved.
*
*/
package com.${packageName}.dao;

import com.${packageName}.model.${modelJava};
import com.api.${packageName}.vo.${modelJava}Vo;
import com.api.${packageName}.vo.${modelJava}VoReq;
import com.common.web.BaseIbatisDao;
import org.springframework.stereotype.Component;
import java.util.List;

/**
* Class <code>${daoJava}</code> is the root of the class hierarchy.
* Every class has <code>${daoJava}</code> as a superclass. All objects,
* including arrays, implement the methods of this class.
*
* @author   ${author}
* @version  ${createTime}
* @see      java.lang.Class
*/
@Component
public class ${daoJava} extends BaseIbatisDao<${modelJava},${keyType}>{

    @Override
	public Class<${modelJava}> getEntityClass() {
		return ${modelJava}.class;
	}


   /**
    * 批量插入
    *
    * @return
    */
    public void batchSave(List list) {
         this.getSqlMapClientTemplate().insert("${modelJava}.batchSave", list);
    }


}
