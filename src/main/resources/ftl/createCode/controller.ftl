package com.console.${packageName}.controller;

import com.api.${packageName}.vo.${modelJava}Vo;
import com.api.${packageName}.service.${modelJava}Manager;
import com.common.page.Page;
import com.common.page.PageRequest;
import com.common.utils.DateUtil;
import com.common.utils.IdUtil;
import com.common.web.BaseController;
import com.common.web.exception.ServiceException;
import com.common.web.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* Class <code>${modelJava}Controller</code> is the root of the class hierarchy.
* Every class has <code>${modelJava}Controller</code> as a superclass. All objects,
* including arrays, implement the methods of this class.
*
* @author   ${author}
* @version  ${createTime}
* @see      java.lang.Class
*/
@Controller
@RequestMapping("/${lastPackageName}/${modelValue}")
public class ${modelJava}Controller extends BaseController {
    @Autowired
    private ${modelJava}Manager  ${modelValue}Manager;


    private static final String LIST_HTML = "/page/${lastPackageName}/${modelValue}/${modelValue}";
    private static final String ADD_HTML = "/page/${lastPackageName}/${modelValue}/${modelValue}_add";
    private static final String EDIT_HTML = "/page/${lastPackageName}/${modelValue}/${modelValue}_edit";

    @Override
    protected String getListPage() {
        return LIST_HTML;
    }

    @Override
    protected Page findByPageRequest(PageRequest<Map<String, Object>> pageRequest) throws ServiceException {
        Page page = ${modelValue}Manager.findByPageRequest(pageRequest);
        return page;
    }

    /**
     * show 新增页面
     * @param req
     * @return
     */
    @RequestMapping("/add")
    public String addRole(HttpServletRequest req){
        return ADD_HTML;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save(${modelJava}Vo ${modelValue}Vo){
        if(StringUtils.isNotEmpty(${modelValue}Vo.getId())){
            if(StringUtils.isEmptyOrBlank(${modelValue}Vo.getUpdateTime())){
                ${modelValue}Vo.setUpdateTime(DateUtil.getNowDateTimeStr());
            }
            //编辑
            ${modelValue}Manager.update(${modelValue}Vo);
        }else{
            //新增
            if(StringUtils.isEmptyOrBlank(${modelValue}Vo.getCreateTime())){
                 ${modelValue}Vo.setCreateTime(DateUtil.getNowDateTimeStr());
                 ${modelValue}Vo.setUpdateTime(DateUtil.getNowDateTimeStr());
            }
            ${modelValue}Vo.setStatus(1);
            ${modelValue}Vo.setId(IdUtil.getId());
            ${modelValue}Manager.save(${modelValue}Vo);
        }
        return returnJson(SUCCESS,MSG);
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest req,String id){
        ${modelJava}Vo ${modelValue}Vo =  ${modelValue}Manager.getById(id);
        req.setAttribute("model", ${modelValue}Vo);
        return EDIT_HTML;
    }


    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletRequest req,String id){
        ${modelJava}Vo ${modelValue}Vo = new ${modelJava}Vo();
        ${modelValue}Vo.setId(id);
        ${modelValue}Vo.setStatus(-1);
         ${modelValue}Manager.update(${modelValue}Vo);
        return returnJson(SUCCESS,MSG);
    }

}
