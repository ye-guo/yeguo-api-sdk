package icu.yeguo.yeguoapisdk.utils;

import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;

public class SignUtil {
    private static final String SIGNATURE_KEY = "野果API";
    public static String generateSignature(String message) {
        // 此处密钥如果有非ASCII字符
        byte[] key = SIGNATURE_KEY.getBytes();
        HMac mac = new HMac(HmacAlgorithm.HmacMD5, key);
        // 生成签名
        return mac.digestHex(message);
    }
}
