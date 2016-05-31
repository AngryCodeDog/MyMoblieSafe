package example.com.mymobilesafe.util;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import example.com.mymobilesafe.AppManage.AppInfo;

/**
 * Created by zyp on 3/26/16.
 * 全局的静态累，用来存放一些全局的静态变量
 */
public class GloabalTools {

    /**默认的配置文件名*/
    public static String CONFIG = "config";

    /**是否开启自动更新*/
    public static String AUTO_UPDATE = "auto_update";

    /**是否开启自动更新*/
    public static String SECURITY_PWD = "security_pwd";

    /**是否完成了安全设置*/
    public static String COMPLETE_SECURITY_SET = "complete_security_set";

    /**是否完成了安全设置*/
    public static String OPEN_GPS_TRACK = "open_gps_track";

    /**绑定的sim卡的卡号*/
    public static String BIND_SIM_CARD_NUMBER = "sim_card_number";

    /**安全手机号码*/
    public static String SECURITY_PHONE_NUMBER = "security_phone_number";

    /**设置远程锁屏*/
    public static String REMOTE_AND_LOCKSCREEN = "complete_and_lockscreen";

    /**设置远程锁屏*/
    public static String CALL_LISTENE = "call_listene";


    /**
     * 校验服务是否还活着
     * @param context
     * @param serviceName
     * @return
     */
    public static boolean isServiceRunning(Context context,String serviceName){
        //校验服务是否还活着
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(100);
        for(ActivityManager.RunningServiceInfo serviceInfo : runningServices){
            if(serviceInfo.service.getClassName().equals(serviceName)){
                return true;
            }
        }
        return false;
    }


    public static List<AppInfo> getAppInfo(Context context){
        List<AppInfo> myAppInfos = new ArrayList<AppInfo>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> appInfos = pm.getInstalledPackages(0);
        for(PackageInfo info : appInfos){
            AppInfo appInfo = new AppInfo();
            appInfo.pkgName = info.packageName;
            appInfo.name = info.applicationInfo.loadLabel(pm).toString();
            appInfo.icon = info.applicationInfo.loadIcon(pm);
            int flags = info.applicationInfo.flags;
            if((flags & ApplicationInfo.FLAG_SYSTEM) == 0){
                //用户程序
                appInfo.userApp = true;
            }else{
                //系统程序
                appInfo.userApp = false;
            }
            if((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == 0){
                //手机内存中
                appInfo.inRom = true;
            }else{
                //外存储设备中
                appInfo.inRom = false;
            }
            myAppInfos.add(appInfo);
        }
        return myAppInfos;
    }

}
