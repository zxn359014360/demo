package com.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RsaUtils
{
/**
 * RSA软加密
 */
private static final String KEY_ALGORITHM = "RSA";

    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static KeyFactory keyFactory = null;

    /** *//**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

        /** *//**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    static
    {
        try
        {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        }
        catch (NoSuchAlgorithmException e)
        {
            System.err.println("keyFactory初始化失败");
        }
    }

    /**
     * RSA签名
     *
     * @param content 待签名数据
     * @param privateKey 私钥
     * @param input_charset 编码格式
     * @return 签名值
     * @throws Exception
     */
    public static String sign(String content, String privateKey, String input_charset)
        throws Exception
    {
        PrivateKey priKey = toPrivateKey(privateKey);

        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(priKey);

        signature.update(content.getBytes(Charset.forName(input_charset)));

        byte[] signed = signature.sign();

        String signStr = Base64.encodeBase64String(signed);

        return signStr;

    }

    /**
     * RSA验签名检查
     *
     * @param content 待验签数据原文
     * @param sign 签名值
     * @param public_key 公钥
     * @param input_charset 编码格式
     * @return 布尔值
     * @throws Exception
     */
    public static boolean verifySign(String content, String sign, String public_key, String input_charset)
        throws Exception
    {
        PublicKey pubKey = toPublicKey(public_key);

        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

        signature.initVerify(pubKey);

        signature.update(content.getBytes(Charset.forName(input_charset)));

        boolean bverify = signature.verify(Base64.decodeBase64(sign));
        return bverify;

    }

    public static String encrypt(String plaintext, String secretKey, String input_charset)
        throws Exception
    {
        // 取得公钥
        PublicKey pubKey = toPublicKey(secretKey);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        byte[] encDataByte = cipher.doFinal(plaintext.getBytes(Charset.forName(input_charset)));

        return Base64.encodeBase64String(encDataByte);
    }

    public static String decrypt(final String decryptText, String secretKey, String input_charset)
        throws Exception
    {
        byte[] dataByte = Base64.decodeBase64(decryptText);

        // 取得私钥
        PrivateKey privateKey = toPrivateKey(secretKey);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decDataByte = cipher.doFinal(dataByte);

        return new String(decDataByte, Charset.forName(input_charset));
    }

    public static Map<String, String> genKeyPairs(int keySize)
    {
        Map<String, String> keyPairs = new HashMap<String, String>();

        try
        {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(keySize);
            KeyPair keyPair = keyPairGen.generateKeyPair();

            String publicKey = Base64.encodeBase64String(keyPair.getPublic().getEncoded());
            String privateKey = Base64.encodeBase64String(keyPair.getPrivate().getEncoded());

            keyPairs.put("PublicKey", publicKey);
            keyPairs.put("PrivateKey", privateKey);
        }
        catch (NoSuchAlgorithmException e)
        {
            System.err.println(e.getMessage());
        }

        return keyPairs;
    }

    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static PrivateKey toPrivateKey(String key)
        throws Exception
    {
        // 取得私钥数组
        byte[] keyBytes = Base64.decodeBase64(key);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        // 生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        return privateKey;
    }

    /**
     *
     * 得到公钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static PublicKey toPublicKey(String key)
        throws Exception
    {
        // 取得公钥数组
        byte[] keyByte = Base64.decodeBase64(key);

        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 生成公钥
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);

        return publicKey;
    }


    /** *//**
     * <p>
     * 公钥加密  分段加密
     * </p>
     *
     * @param content 源数据
     * @param publicKey 公钥(BASE64编码)
     * @param input_charset 编码
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String content, String publicKey, String input_charset)
            throws Exception {
        byte[] data = content.getBytes(Charset.forName(input_charset));
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return  Base64.encodeBase64String(encryptedData);
    }

    /** *//**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param content 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String content, String privateKey)
            throws Exception {
        byte[] encryptedData = Base64.decodeBase64(content);
        PrivateKey secretKey = toPrivateKey(privateKey);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData,"utf-8");
    }



    public static void main(String[] args) throws Exception
    {
        /*int keySize = 2048;
        Map<String, String> keyPairs = genKeyPairs(keySize);
        System.err.println("RSA keySize:" + keySize);
        System.err.println("RSA KeyPairs:" + keyPairs);*/

        String content = "{\"appVersion\":\"JXF-A-1.0.1\",\"bizContent\":\"{\"repaidNum\":\"3000\",\"cardId\":\"0b6418ae6748417dbc712711f0d012ab\",\"principal\":\"1000\",\"pepaymentDate\":\"25\",\"merchantId\":\"ba5d80dacae64a8495a397755232750e\"}\",\"businessCode\":\"300006\",\"interfaceVersionNo\":\"1.0\",\"phoneMac\":\"abc12121\",\"phoneVersion\":\"IOS8\"}";
        String text = "12421123123123123adgagadg";
//        String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCftDNUhCN/444xUiIkV+dxH6lb7of4nkmcvpygKImyepNwusRcvTdtZp/z+K/IHOcEYGS8iSSioM1ZWX1uStXPx4Zo9nYBBcLd3f4TuAF2dzwh1CiVVUV5Ve0xYJD6CHp3Z0Dw0xoKYooPpYUpwvtsDmkus8TfA1bABWizCsCc6Tsqswd15sxqc6+XuCxc97HAwPME8zZLtK7RUGn84P5Kf6zvLqjqUhG0gpjZfFAsNHNvUSvza8Y9obNrKaVGvRWYxaicoaPJH0ZxXmegNnV9VZNZXB982EtUEwoPk0ufz58RQiDnWA5QawIknRXDLUyMoy0F8QoIsvKjzI6GpmTXAgMBAAECggEAWDYpkAo4rYAcX0O1lgtzy/koC55SPlH36Psj+hbKD+pCnCadJXhiMCxaN2Dqfwbv12wC2FyL/sQBCNQ0QwJU3SKhLELN5TywaOogV/Xv4OZ1MV5FWE60RBPhIr/q9CBQvLkslpiTrp7FEWVkiy+mvgWrtV8YY/ItLX0PWq2avE/3MXlhBPorM63rMOwO9DwfhCHnYHabe1eWCw8APfJqeETM4IAah5G7ySAaX3uqziSYDO1uSIEBVxtlEcIXlDrA9Sj4TR0fXzkc3ozoutgpk+DTUz23FcEyK+YrkVRi4BXkQDsAH7YNCUw2FSZulkUyKHryakPNX8+V9MmrbL7AsQKBgQD3WGfcnrtC1xSPLDCOkd2C1Juys13zIv/4DxYH1dLq+0bdIKIbH/7i41IAAMcetGICRSFlAOT0oisqOMZbpK6QGqH8V0esEoDp4loBjKpeNzFWHm+sDULuC/SO3uw897VMKGN6yxr8IemRgEZFbn1KDx74Qcgnt6RpIG3wRG4wWQKBgQClSr9M8HOCEPZ0YVK0VG7qN0oxHkoxeOUiHxyUbYtRY86BDsqYspxP5qsYVGPZTXNki2xpQCsU52A+YYAf+8/7djDfhW9g1iKZv4aV5iq2mzVAxdT6AYimQAiIj0iYSCiM5jrmncEX30arOVfI1ing+KHoykEWSNixRPgAb8MYrwKBgQDJh/ioI5UEcuZHeYPexi6r6LsrsUW9UykoXnJe0/PUjgRBK9OpMjqldv5bDkcvV13754O8HixuvqtY7YWBKf8pXunZBuxY4YK0Dj+zv38Y4POL7aSjlPKRrqAGwM/PJS1M7iOP62kDQkZizRd0fwAKlaNwN3j0E4ccONYazEbTkQKBgASmmcNccKOUPpr/sggI6CYG8Dt5krTZpfjTz0YN3wGnQUQ4WlL5k5Rb9Sx2E2kl2L1XfvFnMM8hw3991tEPkMaOiMmBQ6UB4W9aCDtngoQo0dLEvj1albG304WkInLGdP2h7L5Yafp1+dMPhfzMqdj+pe+a4UHzQsWaHzBD9MVZAoGAEzN1Qsx0U/jdSGQUrb2XBHQq/c0PAcZ8VdXYRO2LoG9bk2DSXx+OcITXs8bUS+DV3HJGZ37ZT/vn21K+egxZzh9NHYvrUn9B19OjSjcEdSoGm3bLT8xvOMePyA/EYKjCx7umM0ozDmrmEo76xu4gLcWfdQVyT4YJnwLsCYCiL7A=";
//        System.err.println(RsaUtils.sign(content, privateKey, "UTF-8"));

//        String sign = RsaUtils.sign(content, privateKey, "UTF-8");
        //String sign = "QCAuH3Oeek+c0Z8iMeb5FVLsss1j6lx5v7yx9EwZFhAEo5T5gE4tnntfEbnbXp6wT47aJ9ga2PwuLGWvDTwC+TWHsCuwQCtMjyPmxf6htzYXKlvYr/+lw/GhzYQz7zBTiRjx1WtGkaNvS2KUAzEm0NWSjmpq49mVUK0V1XEOfswOdMnHqn+0nNdgNS38k49j+AXAzjdIa6QjP0qvkKG4Ad1qU9rZ9ne00NsKdN51RrDxWjrb5SeplPMW2s83Sz72dznH1Np98M51M61FWHxvjuDr6hzbxkan5Cmkh2PNjNLLHlDhYA+hlpdRQK04AZdiUVGEIIApCeZ57v4pqzbKfg==";
        //String publickKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5ZH2IhAu4FSOevgOabhZKwesmDZZ8VIhgovMwavS7zKLhRdeslANhL/RdWPxYq1rPPyya//BG917Va4oCdmKwWpW+rmVNcY+kn5BoCpQ/8WB4Gz7HWfr8k3SDWwmacK1XmHhuwQKWrOe6uhbu4ifMB1E0fcM9FpZUy+PzLrydy0kNEPJZRAwV9HV9aDhjOnMI0sHMsnRXeWfo2Vp5XTw+p2kFEoVG2wwu85tJ6mZiRfxhiM4G0yH4y1wN1gYsHsWZVEsrh7Vdh8VAEE3d0flB/xWpa4lAjvXTKGQ8qyEsFr5xMrRZwg1xNs9AoXBmlgC/MOadXbCVklabQsFwqPv8QIDAQAB";
        String publickKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCE0wojG1fnUtJs/MYtTG6xqq9yE1vucbNV9JhTAosHP15z8DokLnPXTPs/fGXWDs3k0S9uZmQ/tQBHUKrn+reKL/I6r+86v1aUtaeL7FmirNpLFhkVRKCch81Kk2w9ePd/MRAjV4Y8osuBXEZVAls7G4I+5Nv4vV3InmT6PpAd7QIDAQAB";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAITTCiMbV+dS0mz8xi1MbrGqr3ITW+5xs1X0mFMCiwc/XnPwOiQuc9dM+z98ZdYOzeTRL25mZD+1AEdQquf6t4ov8jqv7zq/VpS1p4vsWaKs2ksWGRVEoJyHzUqTbD14938xECNXhjyiy4FcRlUCWzsbgj7k2/i9XcieZPo+kB3tAgMBAAECgYEAhENSWNslkWfbukOLZBg0Mh7xTfsRFEOMfQ9vj1l8796eQ3z3qIfJOp+hoPpSvfFH/Y6AWMMFGJs4h8tAJHh4wmbQReVnJ9BpkjGBNSl5IooTweM+avI6Doa11ojbMsCxvFIQ7AJCE/p5FgAW/AoG5m4eMvyyrLGj0amW27v/84ECQQC9p3MDZAoswqtTX8lZfr7+zYNNGfqG9ubwuwZHxpTnbJ+PsY8ThV0J+HDTKpC0kHLeX86+7XURAoLbjBtZtfPlAkEAs0ouUYgD7uILA3Yh5vNB8HYVWZ64o0A7HkOoTMUi5Yjwh8nBvdonnet+ZmFXLyLKqrv4kh187MQzldfUcrtxaQJAPlYcmp+2LHIr5/KS9uSPGKcqIhyI2PWsgLGoZc0iwlJUu+FLusnaA93yD6Zs+7yC1Q6+oMjh8p5Vrp4Fm14P9QJAe8Yui+LWtEghSlZ3uTUexJ8AxkopNarWJWKYAcfm+4e6ZkQHGMZOq1s0fAGfas8eExiJ1nW9FMeuxTenVz3IYQJBAI70IUZWzLy9X0RmZItLqunHZW/wVsOHGR7AFob447NR5pHLp6d7yoFU4G3wYPIFb39l6rdvylf+GbeirhSc5Nw=";
//        System.err.print(RsaUtils.verifySign(content, sign, publickKey, "UTF-8"));
//        String b = RsaUtils.encryptByPublicKey(content, publickKey,"utf-8");
//        String b = RsaUtils.encrypt(text, publickKey,"utf-8");
//        System.out.println(b);
        //AES 软解密
        /*String abc = "YD+ZHxUSwfujPTo3KLFxAP9QAOSnEHLKvGklp71883BVRsEH68bFm5ce9PiavAEkcBC/slmWuNQRfAGXfAPh+CjFkRST+ApciXvtgPsA8mds4SruR+SUC6GB1uht458gNVQlBZzNXlciUynzTT9XjdvoterKHXcOmk8IbKZ/9zoh3yh3F9rDzWcgLYpcoY2TLNerk5wVIrRRH+D/kNaWzvYwvMtmMyNx93vmRlKsduqolEmXIIo4zXuMQFBUcnpaTLKj8NmvmURWUumWTveUEjQ01UxeK0fM+YjbzKWrGLEjw+NcM7WiERuY4+MP9jk8cwAHPI2tyHfVvgC0Z98RGc6rgbdOi/8VrPmNX68+qrqHilSvQjj9FffGC4zqacI4NXW3eY77vtOB/CBx4ouv39KH230qQskaXcE2Z6KvDSRS0i25GNeAmiaPd+/if0dQsV/W+495bePcLPcoTeGTzkHmqZpD5XfQ2cWTrdIhUG+fzAm2CDGrQlUmxpwCyCvJx8I3EbY0iaonEtWSfhNhXDkmlEOp8huZ2VGbk49X0B1sKYz6cr0F4jGg26RGxSUhI+iwVYa9sEfboj21sZSMHY2mfNsRzgcyM9l/VkKuPluol1Oq/lRuzJ2/3KejOLAPCcvXWcNocHMcD3fal7u+6x37iHGXg9keqFdiRkvy3C75eN6jZyfgG3M311sWTw3vOm66npacgbVO2OhDbyLT2TTKduK3q4XBdZ7BqDWjVNetbcXSDtcvPwvGT6P2TUsf";
        String secretKey = "CRu3Yc58KCPRo4PQpJa1yw==";
        String abc1 = AesUtils.decryptMsg(abc, secretKey);
        System.out.println("abc1"+abc1);*/
//        Map map = genKeyPairs(2048);
//
//        System.out.println(map.get("PublicKey"));
//        System.out.println(map.get("PrivateKey"));
        String d = "LxHmFM4wBEsovhvW/GrdEON0kKGAD7KcUFw5VHdVzQ2oVBfCigJOBK27G3CAIMpqOtNY1Anda5CwtzU/JvvZFUAHVprsc4Nt7qjL7bx0yABsqyJiAxWD8prqeiEWdhMCwa6BDt5Jy3V1yCI6YP+0HqVuJ+5ezlLuPj/7y5zOUjxMhtuZIVMJGZltuKN1X6PPVkfLtmWxLU9yDPj+43fgecL5QS17xmm8QOe2vfSAd3/NUSxzSqPKq+iXpdUncNrrIPTP6d4bgY6iPbDC6xYxj8ybu5HEBzyUWX0Xn6W6XxhE0whVLvmhke903Xzu9txM7nkHWqknn4+1ZruZsuQ9VEvHHp3r3x9YafJsNzYuQmpGKTnsBVyvKyiaOQK7OdzQzQgvunVhac0PKpc8t2McxZ1zTf6C9ljN58FpmllIBjhYabwuR/hurr56yy7Rua3qUikuj6mlDPwMsrA5xPsvZx3zYx4fQQ142t+YCzSr3xH9Sj8LqDDKfvVjuNId6lu/";
        String c = RsaUtils.decryptByPrivateKey(content,privateKey);
//        String c = RsaUtils.decrypt(b,privateKey,"utf-8");
        System.out.println(c);



    }

}
