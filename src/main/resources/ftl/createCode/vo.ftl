/*
* @(#)${modelJava}.java
*
* Copyright 2017-2018, Inc. All rights reserved.
*
*/
package com.api.${packageName}.vo;

import java.io.Serializable;
<#list inportClassNameList as var>
import ${var};
</#list>

/**
* Class <code>${modelJava}Vo</code> is the root of the class hierarchy.
* Every class has <code>${modelJava}Vo</code> as a superclass. All objects,
* including arrays, implement the methods of this class.
*
* @author   ${author}
* @version  ${createTime}
* @see      java.lang.Class
*/
public class ${modelJava}Vo implements Serializable {

    <#list fieldList as var>
         private ${var[4]} ${var[2]};
    </#list>

    <#list fieldList as var>

        public ${var[4]} get${var[3]}() {
            return ${var[2]};
        }

        public void set${var[3]}(${var[4]} ${var[2]}) {
            this.${var[2]} = ${var[2]};
        }

    </#list>

}

