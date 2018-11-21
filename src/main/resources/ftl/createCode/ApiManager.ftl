/*
* @(#)${modelJava}VoManager.java
*
* Copyright 2017-2018, Inc. All rights reserved.
*
*/

package com.api.${packageName}.service;

import com.api.${packageName}.vo.${modelJava}Vo;
import com.common.page.Page;
import com.common.page.PageRequest;
import com.common.web.BaseManagerByVo;

public interface ${modelJava}Manager extends BaseManagerByVo<${modelJava}Vo, String> {

    @Override
    Page findByPageRequest(PageRequest pageRequest);
}

