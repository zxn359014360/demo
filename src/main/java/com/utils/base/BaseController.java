package com.utils.base;

import com.alibaba.fastjson.JSON;
import com.utils.RsaUtils;
import net.sf.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/19.
 */
public class BaseController {
    private static final String APublibKey = "";
    private static final String APrivateKey = "";
    private static final String BPublicKey = "";
    /**
     * 获取流中的JSON参数
     * 请求数据解密
     * @param request
     * @return
     */
    public Map<String, String> getJsonParameter(HttpServletRequest request) throws Exception {
        InputStream in = request.getInputStream();
        List<Byte> buff = new ArrayList<Byte>();
        if (null == in){
            return null;
        }
        int b;
        while ((b = in.read()) != -1) {
            buff.add((byte) b);
        }
        byte[] data = new byte[buff.size()];
        int s = buff.size();
        for (int i = 0; i < s; i++) {
            data[i] = buff.get(i);
        }
        String context = new String(data, "utf-8");
        if (in != null) {
            in.close();
        }

        //解密.
        String decryptContext = "";
        try{
            decryptContext = decrtptRequest(context);
        }catch (BadPaddingException e){
            decryptContext = context;

        }

        return (Map<String, String>) JSON.parse(decryptContext);
    }

    /**
     * 返回数据加密
     * @param result
     * @return
     * @throws Exception
     */
    public String encryptResult(String result) throws Exception {
        String encryptResult = RsaUtils.encryptByPublicKey(result,BPublicKey,"utf-8");
        return encryptResult;
    }


    public String decrtptRequest(String request) throws Exception {
        String decrtptRequest = RsaUtils.decryptByPrivateKey(request,APrivateKey);
        return decrtptRequest;
    }

    public static String getAPublibKey() {
        return APublibKey;
    }

    public static String getAPrivateKey() {
        return APrivateKey;
    }

    public static String getBPublicKey() {
        return BPublicKey;
    }

    /**
     * Ajax返回操作状态和文本
     *
     * @param isSuccess
     * @param message
     * @return
     */
    protected String returnJson(boolean isSuccess, String message) {

        Map<String, Object> param = new HashMap<String, Object>(2);

        param.put("success", isSuccess);
        param.put("msg", message);

        return JSONObject.fromObject(param).toString();
    }



    protected String returnJson(boolean isSuccess, String message, int code, Object object) {

        Map<String, Object> param = new HashMap<String, Object>(3);

        param.put("success", isSuccess);
        param.put("msg", message);
        param.put("code", code);
        param.put("object", object);

        return JSONObject.fromObject(param).toString();
    }


}
