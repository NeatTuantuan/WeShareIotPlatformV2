package redis;

import java.util.Arrays;

/**
 * @author lr
 * @Package flink.utils
 * @date 2020/4/17 10:16
 * @description
 */
public class DataUtil {
    /**
     * int -> byte数组
     * @param i
     * @return
     */
    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte)((i >> 24) & 0xFF);
        result[1] = (byte)((i >> 16) & 0xFF);
        result[2] = (byte)((i >> 8) & 0xFF);
        result[3] = (byte)(i & 0xFF);
        return result;
    }

    /**
     * byte数组 -> int
     * @param bytes
     * @return
     */
    public static int bytes2int(byte[] bytes){
        int result = 0;
        if(bytes.length == 4){
            int a = (bytes[0] & 0xff) << 24;
            int b = (bytes[1] & 0xff) << 16;
            int c = (bytes[2] & 0xff) << 8;
            int d = (bytes[3] & 0xff);
            result = a | b | c | d;
        }
        return result;
    }

    public static void main(String[] args) {
        int a = -64;
        System.out.println(Arrays.toString(intToByteArray(a)));
        System.out.println(bytes2int(intToByteArray(a)));
    }
}
