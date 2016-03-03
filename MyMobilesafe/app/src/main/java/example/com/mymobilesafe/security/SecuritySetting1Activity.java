package example.com.mymobilesafe.security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import example.com.mymobilesafe.R;

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
