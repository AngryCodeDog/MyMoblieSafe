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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

        final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;
//        mParams.gravity = Gravity.TOP + Gravity.LEFT;
//        mParams.x = 100;
//        mParams.y = 100;
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        |WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParams.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
        //添加到窗体
        windowManager.addView(llMyToast,mParams);
        //设置触摸事件，让来电显示可以拖动
        llMyToast.setOnTouchListener(new View.OnTouchListener() {
            int startX ;
            int startY ;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //获取初始位置
                        startX = (int)event.getRawX();
                        startY = (int)event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mParams.x += (int)event.getRawX() - startX;
                        mParams.y += (int)event.getRawY() - startY;
                        //更新界面
                        windowManager.updateViewLayout(llMyToast,mParams);
                        //更新初始位置
                        startX = (int)event.getRawX();
                        startY = (int)event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
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
