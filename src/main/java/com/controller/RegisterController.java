package com.controller;

import com.model.User;
import com.service.UserService;
import com.utils.*;
import com.utils.base.BaseController;
import com.utils.redis.CacheUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

@Controller
public class RegisterController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView("register/register","phone","13123397020");
        return mav;
    }


    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    @ResponseBody
    public String getSms(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        String rsaSign = request.getParameter("rsaSign");

        try {
            if (rsaSign == null) {
                return returnJson(false, "验签错误");
            }
            StringBuffer requestBuffer = new StringBuffer(phone);
            String decrypt = RsaUtil.decryptByPrivateKey(rsaSign, getAPrivateKey());
            if (!decrypt.equals(requestBuffer.toString())) {
                return returnJson(false, "验签错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return returnJson(false, "验签异常");
        }

        if ("13100001111".equals(phone)) {
            return returnJson(true, "该手机号已注册过，请去APP登录", 1, phone);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return returnJson(true, "您的验证码为123456");
    }



    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ResponseBody
    public String submitRegist(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        String rsaSign = request.getParameter("rsaSign");
        String password = request.getParameter("password");
        if (StringUtil.isEmptyOrBlank(phone)) {
            return returnJson(false, "手机号不能为空！");
        }
        if (StringUtil.isEmptyOrBlank(password)) {
            return returnJson(false, "密码不能为空！");
        }
        if (StringUtil.isEmptyOrBlank(code)) {
            return returnJson(false, "验证码不能为空！");
        }
        //sha256加密密码
        String sha256HexPwd = DigestUtils.sha256Hex(password).toUpperCase();


        try {
            if (rsaSign == null) {
                return returnJson(false, "验签错误");
            }
            StringBuffer requestBuffer = new StringBuffer().append(phone).append(code).append(password);
            String decrypt = RsaUtil.decryptByPrivateKey(rsaSign, getAPrivateKey());
            if (!decrypt.equals(requestBuffer.toString())) {
                return returnJson(false, "验签错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return returnJson(false, "验签异常");
        }

        if (!"123456".equals(code)) {
            return returnJson(false, "验证码错误");
        }

        User user = new User();
        user.setId(IdUtil.getId());
        user.setPhone(phone);
        user.setPassword(password);
        user.setCreateTime(DateUtil.getNowDateTimeStr());
        userService.register(user);

        return returnJson(true, "注册成功！请去app登陆", 0, phone);
    }


    @ResponseBody
    @RequestMapping(value = "/getPictureCode", method = RequestMethod.GET)
    public void getPictureCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String phone = request.getParameter("phone");

        //设置不缓存图片
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        //指定生成的响应图片,一定不能缺少这句话,否则错误.
        response.setContentType("image/jpeg");

        ArrayList list = PictureCodeUtil.getPictureCode();
        BufferedImage image = null;
        String sRand = null;
        for (int i = 0; i < list.size(); i += 2) {
            image = (BufferedImage) list.get(i);
            sRand = (String) list.get(i + 1);
        }

//        HttpSession session = request.getSession(true);
        //验证码存进cache里
        StringBuffer keyBuffer = new StringBuffer(phone);
        CacheUtils.getCacheForKeyExpire().addKeyExpire(keyBuffer.toString(), sRand, 300);


        ImageIO.write(image, "JPEG", response.getOutputStream()); //输出图片
    }
}
