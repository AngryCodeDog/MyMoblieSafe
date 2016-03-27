package example.com.mymobilesafe.security;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.util.GloabalTools;

/**
 * 设置1主要显示各项设置，默认打开gps追踪
 */
public class SecuritySetting1Activity extends SecurityBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_setting1);
        initView();
        initData();
        setListener();
    }

    @Override
    public void showNext() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(GloabalTools.OPEN_GPS_TRACK,true);
        editor.commit();
        Intent intent = new Intent(this,SecuritySetting2Activity.class);
        startActivity(intent);
        //这个要求在finish或者startActivity后面
        overridePendingTransition(R.anim.right_in,R.anim.left_out);
        finish();
    }

    @Override
    public void showPre() {
        this.finish();
    }

    private void initView() {

    }
    private void initData() {

    }
    private void setListener() {

    }

}
