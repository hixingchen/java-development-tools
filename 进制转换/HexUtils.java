package com.hixingchen.工具类.进制转换;

public class HexUtils {
    public static Float hexToFloat(String hex){
        String replace = hex.replace(" ", "");
        String binary = Long.toBinaryString(Long.valueOf(replace,16));
        String s1 = binary.substring(0,1);//符号位
        String s2 = binary.substring(1,9);//指数位
        int i = Integer.parseInt(s2,2)-127;
        String s3 = binary.substring(9);
        Long i1 = Long.parseLong(s3,2);
        float v = i1;
        for (int j = 0; j < 23; j++) {
            v/=2;
        }
        v+=1;
        for (int j = 0; j < i; j++) {
            v *= 2;
        }
        return Integer.valueOf(s1) == 0?v:-v;
    }
}

