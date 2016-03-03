package example.com.mymobilesafe.security;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import example.com.mymobilesafe.R;

public class SecuritySetting4Activity extends SecurityBaseActivity {
    private SharedPreferences sp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_setting4);
        initView();
        initData();
        setListener();
    }

    @Override
    public void showNext() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("complete_security_set",true);
        editor.commit();
        Intent intent = new Intent(this,SecurityMainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);
        finish();
    }

    @Override
    public void showPre() {
        Intent intent = new Intent(this,SecuritySetting3Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
        finish();
    }

    private void initView() {

    }
    private void initData() {
        sp = getSharedPreferences("config",MODE_PRIVATE);
    }
    private void setListener() {

    }


}
