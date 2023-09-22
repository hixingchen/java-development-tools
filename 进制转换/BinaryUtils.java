package fk.frameking.modules.common.utils;

public class BinaryUtils {
    //需要结构2字节1 4 11
    //浮点型转2字节二进制
    public static String FloatToBinary(Float convert){
        Integer s = Float.floatToIntBits(convert);
        String temp = Integer.toBinaryString(s);
        //如果输入浮点数大于0，在前面补0
        if (convert > 0){
            temp = "0"+temp;
        }
        String result = "";
        //取1
        result += temp.substring(0,1);
        //取4
        result += temp.substring(5,9);
        //取11
        result += temp.substring(9,20);
        return result;
    }
    //2字节二进制转浮点型
    public static Float BinaryToFloat(String binary){
        //第0位为符号位，(1)1到5位为指数位，(2)指数位转整数加一代表第5位之后的多少位依然是整数部分，根据（1）（2）可
        //算出整数部分的值2^(1)+(2)
        String s1 = binary.substring(0,1);//符号位
        String s2 = binary.substring(1,5);//指数位
        int i = Integer.parseInt(s2,2);
        String s3 = binary.substring(5,6+i);
        Integer i2=(int)Math.pow(2,i+1)+Integer.parseInt(s3,2);//整数部分的值
        String s4 = binary.substring(6+i);//小数部分
        int length = s4.split("1")[0].length();
        float i3 = (float) Integer.parseInt(s4,2);
        while(i3 > 1){
            i3 /= 2;
        }
        i3/=Math.pow(2,length);
        Float result = i3+i2;
        return (float)Math.round(result*100)/100;//保留一位小数
    }

    /**
     * 数字前面自动补零
     * @param number 数字
     * @param number 位数
     * @return
     */
    public static String fillZero(String number,int num){
        if(number.length()>=num){
            return number;
        }
        int addNum = num-number.length();
        for (int i = 0; i < addNum; i++) {
            number = "0"+number;
        }
        return number;
    }

    public static void main(String[] args) {
//        String s = FloatToBinary(25.2f);
//        System.out.println(s);
        String s = "0000000010011001";
        Float aFloat = BinaryToFloat(s);
        System.out.println(aFloat);
    }
}

