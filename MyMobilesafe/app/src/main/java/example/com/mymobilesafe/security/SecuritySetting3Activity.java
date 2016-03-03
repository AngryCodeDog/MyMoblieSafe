package example.com.mymobilesafe.security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import example.com.mymobilesafe.R;

public class SecuritySetting3Activity extends SecurityBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_setting3);
        initView();
        initData();
        setListener();
    }

    @Override
    public void showNext() {
        Intent intent = new Intent(this,SecuritySetting4Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);
        finish();
    }

    @Override
    public void showPre() {
        Intent intent = new Intent(this,SecuritySetting2Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
        this.finish();
    }

    private void initView() {

    }
    private void initData() {

    }
    private void setListener() {

    }


    public void selectContact(View view){
        Intent intent = new Intent(this,SelectContactActivity.class);
        startActivityForResult(intent,0);
    }
}
