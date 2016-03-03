package example.com.mymobilesafe.util;

import java.io.File;
import java.io.IOException;

/**
 * 文件工具类
 * Created by zyp on 16-1-3.
 */
public class FileTools {

    /**
     * create file in sdcard
     * @param filePath
     * @return
     * @throws IOException
     */
    public static File createSDFile(String filePath) throws IOException {
        File file = new File(filePath);
        try {
            file.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        return file;
    }

    /**
     * create directory in sdcard
     * @param dirPath
     * @return
     */
    public File createSDDir(String dirPath){
        File dir = new File(dirPath);
        dir.mkdir();
        return dir;
    }

    /**
     *
     * @param filePath
     * @return If this file exists returns true, else return false
     */
    public static boolean existFile(String filePath){
        File file = new File(filePath);
        return file.exists();
    }
}
