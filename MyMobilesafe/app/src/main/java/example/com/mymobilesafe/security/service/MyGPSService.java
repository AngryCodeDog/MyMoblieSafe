package example.com.mymobilesafe.security.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.List;

public class MyGPSService extends Service {


    private MyBinder binder = new MyBinder();
    class MyBinder extends Binder{
        //这里是链接对象,可以有返回service对象的方法
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        lm.requestLocationUpdates("gps",0,0,new MyLocationListener());

    }


    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            String longgitude = "经度："  + location.getLongitude();
            String latitude = "纬度：" + location.getLatitude();
            SmsManager smsManager = android.telephony.SmsManager.getDefault();
            List<String> list = smsManager.divideMessage(longgitude + latitude);
//            for(String msg : list){
//                smsManager.sendTextMessage(smsSenter,null,msg,null,null);
//            }
//            Toast.makeText(this,"发送gps位置给"+":"+longgitude+","+latitude,Toast.LENGTH_LONG).show();
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
