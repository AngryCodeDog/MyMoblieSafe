package example.com.mymobilesafe.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zyp on 16-1-2.
 */
public class StreamTools {
    public static String getStringFromInput(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        while ((len = in.read(data)) != -1){
            baos.write(data,0,len);
        }
        in.close();
        String str_data = baos.toString("UTF-8");
        baos.close();
        return str_data;
    }
}
