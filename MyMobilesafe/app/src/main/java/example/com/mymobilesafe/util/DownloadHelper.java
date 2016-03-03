package example.com.mymobilesafe.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zyp on 16-1-3.
 */
public class DownloadHelper {

    /**
     *get file name
     *<p>author:zyp
     *<p>create at 16-1-4 下午10:09
     **/
    private static String getFileName(String url) {
        return url.substring(url.lastIndexOf("/")+1);
    }

    /**
     *get sdcard path
     *<p>author:zyp
     *<p>create at 16-1-6 下午9:20
     **/
    private static String getSdcardPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
    }



    /**
     *
     *
     * @param in
     * @param fileSize
     * @return
     */
    public static boolean downloadFile(InputStream in, int fileSize,String url)  {
        String SdcardPath = getSdcardPath();
        String fileName = getFileName(url);
        byte[] data = new byte[1024];
        byte[] file_data = new byte[fileSize];
        File file = null;
        Log.d("TAG",fileSize+"");
        try {
            file = FileTools.createSDFile(SdcardPath+fileName);
            FileOutputStream fos = new FileOutputStream(file,true);
            int len = 0;
            while((len = in.read(data)) != -1){
                fos.write(data,0,len);
            }
            Log.d("TAG",fileSize+"");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                in.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }


}
