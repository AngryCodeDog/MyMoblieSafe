package example.com.mymobilesafe.security.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 *监听启动广播
 *<p>author:zyp
 *<p>create at 16-3-1 下午9:58
 **/
public class BootComplete extends BroadcastReceiver {
    private SharedPreferences sp;
    private TelephonyManager tm;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        //获取之前保存的sim卡序列号
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        String preSimNumber = sp.getString("sim_card_number","") + "23";
        //获取现在的sim卡序列号
        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String currentSimNumber = tm.getSimSerialNumber();
        //对比是否一致
        if(preSimNumber.equals(currentSimNumber)){
            //如果一样，则不用报警
        }else{
            //如果不一样，则需要发送报警短信给安全号码
            Log.e("BootComplete","sim卡变更了！！");
            Toast.makeText(context,"sim卡变更了",Toast.LENGTH_SHORT).show();
        }
    }
}
