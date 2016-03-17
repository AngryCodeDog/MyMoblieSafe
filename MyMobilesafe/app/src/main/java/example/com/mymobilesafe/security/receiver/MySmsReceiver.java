package example.com.mymobilesafe.security.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import example.com.mymobilesafe.R;

public class MySmsReceiver extends BroadcastReceiver {
    private Context mComtext ;
    private String smsSenter;
    public MySmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        mComtext = context;
        Object[] objs = (Object[]) intent.getExtras().get("pdus");
        for(Object b : objs){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])b);
            //发送者
            smsSenter = smsMessage.getOriginatingAddress();
            if("5556".equals(smsSenter)){
                //如果是安全号码发送过来的短信,则要提取内容,并做判断

            }
            //内容
            String body = smsMessage.getMessageBody();
            if("#*location*#".equals(body)){
                //得到手机的gps

                Log.e("MySmsReceiver","得到手机gps");
                Intent intentToService = new Intent(context,MySmsReceiver.class);
                context.startService(intentToService);

                LocationManager lm = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
                lm.requestLocationUpdates("gps",0,0,new MyLocationListener());

                // **************************************************************
                // 构建位置查询条件
//                Criteria criteria = new Criteria();
//                // 查询精度：高
//                criteria.setAccuracy(Criteria.ACCURACY_FINE);
//                // 是否查询海拨：否
//                criteria.setAltitudeRequired(false);
//                // 是否查询方位角 : 否
//                criteria.setBearingRequired(false);
//                // 是否允许付费：是
//                criteria.setCostAllowed(false);
//                // 电量要求：低
//                criteria.setPowerRequirement(Criteria.POWER_LOW);
//                // 返回最合适的符合条件的 provider ，第 2 个参数为 true 说明 , 如果只有一个 provider 是有效的 , 则返回当前
//                // provider
//                String provider = lm.getBestProvider(criteria, true);
//                //******************************************************************
//                Location location = lm.getLastKnownLocation(provider);
//                String longgitude = "经度："  + location.getLongitude();
//                String latitude = "纬度：" + location.getLatitude();
//
//                SmsManager smsManager = android.telephony.SmsManager.getDefault();
//                List<String> list = smsManager.divideMessage(longgitude + latitude);
//                for(String msg : list){
//                    smsManager.sendTextMessage(smsSenter,null,msg,null,null);
//                }
                abortBroadcast();
            }else if("#*alarm*#".equals(body)){
                //播放音乐
                Log.e("MySmsReceiver","播放报警音乐");
                MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
                player.setLooping(true);
                player.setVolume(1.0f,1.0f);
                player.start();
                abortBroadcast();
            }else if("#*wipedata*#".equals(body)){
                //远程清楚数据
                Log.e("MySmsReceiver","远程清楚数据");
                abortBroadcast();
            }else if("#*lockscreen*#".equals(body)){
                //远程锁屏
                Log.e("MySmsReceiver","远程锁屏");
                abortBroadcast();
            }

        }
    }


    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
//            String longgitude = "w:"  + location.getLongitude()+"\n";
//            String latitude = "s:" + location.getLatitude()+"\n";
//            SmsManager smsManager = android.telephony.SmsManager.getDefault();
//            List<String> list = smsManager.divideMessage(longgitude + latitude);
//            for(String msg : list){
//                smsManager.sendTextMessage(smsSenter,null,msg,null,null);
//            }

//            Toast.makeText(mComtext,"发送gps位置给"+smsSenter+":"+longgitude+","+latitude,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

}
