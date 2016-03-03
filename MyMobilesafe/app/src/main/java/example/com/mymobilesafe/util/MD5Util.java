package example.com.mymobilesafe.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zyp on 16-2-17.
 */
public class MD5Util {
    /**
     *使用MD5加密
     *<p>author:zyp
     *<p>create at 16-2-17 下午10:27
     *<p>
     * 加密算法：
     * <p>1. 用每个byte去和11111111做与运算并且得到的是int类型的值: byte & 11111111
     * <p>2. 把int类型转换成16进制并返回String类型
     * <p>3. 不满八个二进制位就补全；
     **/
    public static String encryptWithMD5(String pwd){
        //得到一个信息摘要熵
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(pwd.getBytes());
            StringBuffer buffer = new StringBuffer();

            for(byte b : result){
                //用每个byte去和11111111做与运算并且得到的是int类型的值: byte & 11111111
                int number = b & 0xff;
                //把int类型转换成16进制并返回String类型
                String str = Integer.toHexString(number);
                //不满八个二进制位就补全(这里的str是16进制的数，因此只要在前面补一个0即可)
                if(str.length() == 1){
                    buffer.append("0");
                }
                buffer.append(str);
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
