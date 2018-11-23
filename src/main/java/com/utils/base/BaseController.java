package com.utils.base;

import com.alibaba.fastjson.JSON;
import com.utils.RsaUtils;
import com.utils.StringUtil;
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
    private static final String APrivateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCngokgq6tz9V4juHQyt8bHOxx5KJW0He5kmLxlHaaoWLb8vEjUzfbqnZI6WUqVHkPmYyeM1YTm1j0+yOmSkjcnQ5Kt3c3ij1Y4keZmxOoSSVL1ErDGvGsI3oC4RCB9S4I1Ew297EWBSkolm4N+lLiNCStF4Lmt80IBGX8Y1L5S2Zd9PY9MSczgzPjZbgdmeMiXY7U1uRBpxp+wbnFCQebbba4yZPiRTaDFvrh4A0BgPUeK5wHZ7NCxnzYXGDJnUN60H5qjLe1WRSDPmAJ6uVx0S+omWSfatUYQmqJayKIQTgBvnsjY5cU5eoHAh7HyM9fRmuyeWUVlJmxJSIIe/fInAgMBAAECggEBAIiz8w0CjI25tPYZ1lp9uG9nQjlucZ3ov2DBdR5gMF466DshvZ5N6DBYH7oWnNNNO8SNLv20Ux2/wYO3m0bOpMwaTV4vIZK/v3GzQACmuw/pXxY6BqJpqkA2tX2ru4gFU2HVBuX0KFT3N8OxQoLPBD21OedxGpdnanpTQHIXTKt3LjYFXLmAL9SI3rBJLfIjxeLmLEvHtOYlTsh+k148HYwTpxMpWQlg148lr/eeqRw8Pey9hnHI1yZbs+ZmKOwX0OpQbzBMBDmlceldveGRK0rKFrrStKLiSKwIWJ0zTB4miYPYhduff6ydVthvEuRrsIN6aHMY1NL4pWDp3GEYl2ECgYEAz+yEb6Lyqmr4vyOX/W7D1Qq5+oQ0ahjhT1knOpxCz1RDAfSFgt4RpNzddwnF6G7kKuU2rdUQn+hobTnZdxgI2w5ZPUQ1y4a1UnocI3HXTjTJOSyZ8+D5IM/580/1XYimpYsNsOTPL6Jj5cWQXjZCbkEqBAxLbaaEUMYr2wzvdSsCgYEAzj3SyQM0+NCALbgsKmxV5EvpR0XD0/N0AP3cOg1xvRnWeSC2LiOJBc7kl1npyYpurdNzPMxdLA+Pw5bMIKNiwZplTpFTqor+AxFExdxzLXTEkKwLwEABb8upEQr1r4xyV3RN9bhJ/pX5FsEssKHuNeS1FmEmN0O6hE82A8ewcPUCgYEArog20w7Q68RHcQqLtqyXrekHHAaFiNnBpz2K9GPeoPJ2HNoIiEWoQd8z94w4mFcdJ/Sn5VsITXJk1erNZG7EYDy3I/pxvZoh8k2V+RrCRF+C2X9a66BY/vYVNexUZmMin6sNnwMx9yY0/NbNzId3U91XS3aWikBQqQzwGzvMdWkCgYEAgbLDYgIQSADm+tl9fNhQU5ehhl7LPfk2FIGRAzj9NAyRoe7+YpmaS/rGKqAys7nIzKvTJoLmfRT0Ne4ZRNU6/8GWwAgIc5GhGFC9zShLjmSGUOzBHw+hV6reEYew5csPBQxxEnvonJ+326Vz80iCQ4Ymk4JYxBxlqVse/tWPkhkCgYB2q2N55G+5Xh0DBw0+vdUejgC8Gdp+2XmFPMHnZXPkeSRNAhQi/Cv5nKUl35yHUJ5TgDqZ+32awnT4mjZp5aFJXauxcNhGmRc64uSrIBLUpyXVGS9dGDQcvcXLywlY5rkkDYFiMD+xCJeOwsP+ZxnpV0lP/N8KVnq0nIhCsV1pZA==";
    private static final String BPublicKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCfQKVBlRHk09e3quFquox7eaCLq/qW+P/skZa8W28cL9EZDNgjE1cyuouULmN46fY+NyW80XIkqCuV+xCVKH1qkxJj4pqSn+2fTXEJlhrjgc0tT9q5O4gZk39ef4VSKvpTtCLPCdPfTY3JUVvssiMMJ+HfDNWCZMJxHt1IHp1bRLNLxKOBflovy5B+iyJBOL3sdKhgr76YPV/eeghsTveVpoEzytF1jFJPD/Uzc76G2m2EF6hCBFfmHb8nYp1BMuhOEt0C2OuMb2SwWNnn21drHNFOO1vtjvxuykTwVxqWxowROQIpzsclDCEsJOnYgRM/5//26rW3kCDJu7EpkQkHAgMBAAECggEADEOXNmi7ewrRZwSS3XxGS0//jCIPZ0b/toEzwYetE9ee27YAgxr/MRnqm9vCr1IdM4ddsZ/TkX0d9iviiAoZNnbjCKwvyTDlxMna9akIoxKg8Wdf6bnq7kVMnsNlSgGjS4To1VblaSH41FMeqCjDyDDeFSZQ3vzReJKu6dnptliB2lQdJxaoQic7qX/s2G/OcSut5M6CS0hax7mbt4ixvn1WZKDuTGy7UX87eS7zzHYgHIxfrjVdv2vpAki1c6dA+fs65v+ycJ8b1FD+QR6RqyW2loS6Kom4BZa+6b3HhbaQhFYIv/J8/tBnBUw0jGUOOMZ9R32upJuI9VOQsvoVyQKBgQDcaW4JSwIG9MqpRsIid8THIckaurniMxb+E4nfSIhw7VKry+W6Wq9LRlhqI+Hucj+qepYoaxnPtq1An2lRj3K6oCNQrulX0tpAJ8yt7AK4Ih+8VCAmc7tK4IiIZTQlsAtAfk8BsmRgNFxnaMviRnhCWlQeegTxrucjoTgFZ1EWcwKBgQC49zzpe6GJ0XluTq6oBHYzth7+/W//TaHWzp8IKZZ6HpwewNjRpZb95GwEkpDmlKI9ZeYfYpsADhS20vT/Y2X17DaeBMb56oCtqthuOPoCQq33mLOl6nAybYBarHov6wnnjYJrsj4SrHGMIJ3EbudBtrlczAcViARdCDpS6jsKHQKBgQCtPJhl6XSAbypSffDcEnKxaNadkaHELg+mR0kYG/RRfdZ6fNnk7J3WdkXLtsQ/G8D6hzs6LWOuJJBruF+Y4Tx1fqQDKFby1iEAR8fR8yv3+aoj5aMXVeMhaPUm7xRbgtB1MlF6rfHCw6TVCkMlZ+nhjQmMoq78HlJBaNeVN60+DQKBgQCwK+RbakLI9riXBOA77cv7oupQ6mlDHNkZtMEqUBogBImkjP+2Zuayul7b2BGlXUpN+oE9wXhqi/7Ux4dMvIq/uhWnWlt+bAnV39S23xPZFVOHcW0iiXIFxeAr/P2AKHjNkC35j9KpjI+17Nb34r3nDDfvF/FjY6LsTUrn8Gr9TQKBgHkiAquoF/3qyVsnGcC/5NvwK9/DnwBZ2KWfd5K2h1Pwo81XPrRzAATrWc63mqImKsGK6uPnca1JLkzKy6wE9nES1un1j+kFQTIE3bRotfvvltk9nIGZJN9T7E16yobwOGV9OcheiwFQqment0nESLwGthjc3n+VsCtBqSgMRQ0f";
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
