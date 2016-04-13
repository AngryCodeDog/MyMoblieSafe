package example.com.mymobilesafe.setting;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.util.LogUtil;
import example.com.mymobilesafe.util.dao.NumberAddressQueryUtils;

public class MyCallListenerService extends Service {

    private TelephonyManager telephony;
    private OnePhoneStateListener myCallListener;
    private WindowManager windowManager;
    private View llMyToast;
    private MyCallOutReceiver myCallOutReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //开启来电监听
        openCallListener();
        return super.onStartCommand(intent, flags, startId);

    }

    private void openCallListener() {
        telephony = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        myCallListener = new OnePhoneStateListener() ;
        telephony.listen(myCallListener,PhoneStateListener.LISTEN_CALL_STATE);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        //注册广播接收者
        myCallOutReceiver = new MyCallOutReceiver();
        IntentFilter filter = new IntentFilter("android.intent.action.NEW_OUTGOING_CALL");
        registerReceiver(myCallOutReceiver,filter);
    }


    private class OnePhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                    //等待接电话
                    String callerAddress = NumberAddressQueryUtils.queryNumber(incomingNumber);
                    showToast(callerAddress);
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    //电话挂断
                    if(llMyToast != null){
                        windowManager.removeView(llMyToast);
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    //通话中
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    private void showToast(String msg){
        llMyToast = LayoutInflater.from(getApplicationContext()).inflate(R.layout.my_toast_layout,null);
        TextView tv_msg = (TextView) llMyToast.findViewById(R.id.tv_my_toast_msg);
        tv_msg.setText(msg);

        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        //添加到窗体
        windowManager.addView(llMyToast,mParams);

    }

    //广播接收者的生命周期和服务是一样的。
    class MyCallOutReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String phone = getResultData();
            String address = NumberAddressQueryUtils.queryNumber(phone);
            showToast(address);
        }
    }


    @Override
    public void onDestroy() {
        //在销毁时候要取消监听！！
        telephony.listen(myCallListener,PhoneStateListener.LISTEN_NONE);
        unregisterReceiver(myCallOutReceiver);
        super.onDestroy();
    }
}
