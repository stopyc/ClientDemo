package cn.qgstudio.util;

import cn.qgstudio.constant.SystemConstant;

/**
 * @program: ClientDemo
 * @description:
 * @author: stop.yc
 * @create: 2022-07-29 09:31
 **/
public class Encryption {
    /**
     * @Description: 加密
     * @Param: [data]
     * @return: java.lang.String
     * @Author: stop.yc
     * @Date: 2022/7/28
     */
    public static String addRsaAndAesToData(String data) throws Exception {

        String code = "";

        //1.获取aes密钥
        String aesKey = AesUtil.getAESKey(128, null);

        System.out.println(aesKey.length());

        //2.对数据进行aes加密
        String encrypt = AesUtil.encrypt(data, aesKey);

        System.out.println(encrypt.length());

        //3.获取加密后密文长度
        String enDataLength = Integer.toHexString(encrypt.length());
        System.out.println(enDataLength);

        //4.进行rsa签名
        String sign = RSAUtil.sign(encrypt, RSAUtil.getPrivateKey(SystemConstant.PRIVATE_KEY));

        //5.拼接生成字符串

        code = aesKey + enDataLength + encrypt + sign;

        System.out.println("授权码为:" + code);

        return code;
    }
}
