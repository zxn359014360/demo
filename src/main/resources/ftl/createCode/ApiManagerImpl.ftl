/*
* @(#)${modelJava}ManagerImpl.java
*
* Copyright 2017-2018, Inc. All rights reserved.
*
*/

package com.${packageName}.service.impl;

import com.api.${packageName}.vo.${modelJava}Vo;
import com.api.${packageName}.service.${modelJava}Manager;
import com.${packageName}.dao.${modelJava}Dao;
import com.common.page.Page;
import com.common.page.PageRequest;
import com.common.web.BaseManagerByVoImpl;
import com.common.web.EntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.${packageName}.model.${modelJava};

@Component("${modelValue}Manager")
public class ${modelJava}ManagerImpl extends BaseManagerByVoImpl<${modelJava}Vo,${modelJava},String> implements ${modelJava}Manager {

    @Autowired
    private ${modelJava}Dao ${modelValue}Dao;

    @Override
    protected EntityDao<${modelJava},String> getEntityDao() {
        return ${modelValue}Dao;
    }

    @Override
    public Page findByPageRequest(PageRequest pageRequest) {
        return ${modelValue}Dao.findByPageRequest(pageRequest);
    }

}

