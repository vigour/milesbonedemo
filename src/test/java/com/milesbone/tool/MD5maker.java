package com.milesbone.tool;

import java.security.MessageDigest;

/** 
 * md5的生成类 
 * Created by yy on 2017/10/11. 
 */  
public class MD5maker {  
    public static String make(String value){  
        String md5str = "";  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            byte[] input = value.getBytes();  
            byte[] buff = md.digest(input);  
            md5str = bytesToHex(buff);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return md5str;  
    }  
  
    /** 
     * 换成16进制 
     * @param bytes 
     * @return 
     */  
    private static String bytesToHex(byte[] bytes) {  
        StringBuffer md5str = new StringBuffer();  
        int digital;  
        for (int i = 0; i < bytes.length; i++) {  
            digital = bytes[i];  
            if (digital < 0) {  
                digital += 256;  
            }  
            if (digital < 16) {  
                md5str.append("0");  
            }  
            md5str.append(Integer.toHexString(digital));  
        }  
        return md5str.toString().toUpperCase();  
    }  
}  
