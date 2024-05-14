import java.math.BigInteger;

public class CRCUtils {
    public static String CRC_16_MODBUS(String source) {
        source += "0000";
        long POLY = 0x18005;
        long INIT = 0xFFFF;
        String binarySource = hexToBinary(source);
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < binarySource.length(); i++) {
            temp.append(binarySource.charAt(i/8*8+(7-i%8)));
        }
        binarySource = temp.toString();
        Long res = Long.valueOf(binarySource.substring(0, 16), 2);
        res ^= INIT;
        for (int i = 16; i < binarySource.length();) {
            if (res<0x10000L){
                res = res * 2 + binarySource.charAt(i) - '0';
                i++;
                continue;
            }
            res ^= POLY;
        }
        if (res>=0x10000L){
            res ^= POLY;
        }
        String ans = Long.toHexString(res);
        int ansLen = ans.length();
        for (int i = 0; i < 4 - ansLen; i++) {
            ans = "0"+ans;
        }
        ans = hexToBinary(ans);
        temp = new StringBuilder();
        for (int i = 0; i < ans.length(); i++) {
            temp.append(ans.charAt(i/8*8+(7-i%8)));
        }
        String s = new BigInteger(temp.toString(), 2).toString(16);
        return s.substring(2)+s.substring(0,2);
    }
    private static String hexToBinary(String hexString) {
        StringBuilder binaryString = new StringBuilder();
        int len = hexString.length();
        for (int i = 0; i < len; i++) {
            char c = hexString.charAt(i);
            int decimal = Integer.parseInt(String.valueOf(c), 16);
            String binary = Integer.toBinaryString(decimal);
            binaryString.append(String.format("%4s", binary).replaceAll(" ", "0"));
        }
        return binaryString.toString();
    }
}
