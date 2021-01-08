package com.fengjian.crowd.util;

import com.fengjian.crowd.constant.Constant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;

public class CrowdUtil {

    public static String md5(String source) {
        if (source == null || source.length() == 0) {
            throw new RuntimeException(Constant.MESSAGE_STRING_INVALIDATE);
        }
        String algorithm = "md5";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] input = source.getBytes();
            byte[] output = messageDigest.digest(input);
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();
            return encoded;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean judgeRequestType(HttpServletRequest request){
        String acceptHeader = request.getHeader("Accept");
        String xRequestedWithHeader = request.getHeader("X-Requested-With");
        return (acceptHeader != null && acceptHeader.contains("application/json"))
                ||
                (xRequestedWithHeader != null && xRequestedWithHeader.equals("XMLHttpRequest"));
    }
}
