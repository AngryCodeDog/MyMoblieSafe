package example.com.mymobilesafe.security;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.util.GloabalTools;

public class SecurityMainActivity extends SecurityBaseActivity {

    private LinearLayout ll_notice;

    private TextView tv_notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_main);
        initView();
        setListener();
        initData();
    }

    @Override
    public void showNext() {

    }

    @Override
    public void showPre() {

    }

    private void initView() {

    }
    private void initData() {
        ll_notice = (LinearLayout) findViewById(R.id.security_main_notice_ll);

        //防盗保护是否开启
        if(isCompleteSet()){
            //是否打开GPS追踪（GPS追踪默认是开启的）
            //是否绑定sim卡号码
            //是否设置安全号码
            //是否打开远程锁屏
            if(sp.getBoolean(GloabalTools.OPEN_GPS_TRACK,false)
                    || sp.getString(GloabalTools.BIND_SIM_CARD_NUMBER,null) == null
                    || sp.getString(GloabalTools.SECURITY_PHONE_NUMBER,null) == null
                    || sp.getBoolean(GloabalTools.REMOTE_AND_LOCKSCREEN,false)){
                //有防盗设置没打开
                tv_notice = (TextView) findViewById(R.id.security_main_notice_tv);
                tv_notice.setText(R.string.security_notice);
            }else{
                ll_notice.setVisibility(View.GONE);
            }
        }
    }
    private void setListener() {

    }

    private boolean isCompleteSet(){
        boolean isCompleteSet = sp.getBoolean(GloabalTools.COMPLETE_SECURITY_SET,false);
        if(isCompleteSet){
            return true;
        }else{
            Intent intent = new Intent(this,SecuritySetting1Activity.class);
            startActivity(intent);
            finish();
            return false;
        }
    }

    public void enterInputSetting(View view){
        Intent intent = new Intent(this,SecuritySetting1Activity.class);
        startActivity(intent);
        finish();
    }






}
