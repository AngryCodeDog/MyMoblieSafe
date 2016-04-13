package example.com.mymobilesafe.util.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zyp on 3/28/16.
 */
public class NumberAddressQueryUtils {

    private static  String path = "data/data/example.com.mymobilesafe/files/address.db";

    /**
     * 传入一个号码，查询归属地
     * @param number
     * @return
     */
    public static String queryNumber(String number){
        String address = number;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
        if(number.matches("^1[34568]\\d{9}$")){
            Cursor cursor = sqLiteDatabase.rawQuery("select location from data2 where id = (select outkey from data1 where id = ?)",new String[]{number.substring(0,7)});
            while (cursor.moveToNext()){
                String location = cursor.getString(0);
                address = location;
            }
            cursor.close();
        }else{
            //其他号码
            switch (number.length()){
                case 3://110   119  120
                    if("110".equals(number)){
                        address = "报警电话";
                    }else if("119".equals(number)){
                        address = "火警电话";
                    }else if("120".equals(number)){
                        address = "急救电话";
                    }else{
                        address = "客服电话";
                    }
                    break;
                case 4://模拟器号码
                    break;
                case 5://10086
                    address = "客服电话";
                    break;
                case 7:
                    address = "本地号码";
                    break;
                case 8:
                    address = "本地号码";
                    break;
                default:
                    if(number.length() > 10 && number.startsWith("0")){
                        Cursor cursor = sqLiteDatabase.rawQuery("select location from data2 where area = ?",new String[]{number.substring(1,3)});
                        while (cursor.moveToNext()){
                            String location = cursor.getString(0);
                            address = location.substring(0,location.length()-2);
                        }
                        cursor.close();

                        cursor = sqLiteDatabase.rawQuery("select location from data2 where area = ?",new String[]{number.substring(1,4)});
                        while (cursor.moveToNext()){
                            String location = cursor.getString(0);
                            address = location.substring(0,location.length()-2);
                        }
                        cursor.close();
                    }
                    break;

            }
        }

        return address;
    }


}
