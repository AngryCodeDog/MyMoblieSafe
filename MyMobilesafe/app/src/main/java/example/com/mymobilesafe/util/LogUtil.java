package example.com.mymobilesafe.util;

import android.util.Log;

/**
 * Created by zyp on 4/10/16.
 */
public class LogUtil {
    public static void i(String tag,String msg){
        Log.i(tag,msg);
    }

    public static void e(String tag,String msg){
        Log.e(tag,msg);
    }

}
