package example.com.mymobilesafe.security;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import example.com.mymobilesafe.R;

public class SecurityMainActivity extends Activity {

    SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_main);
        initView();
        initData();
        setListener();
        isCompleteSet();
    }
    private void initView() {

    }
    private void initData() {
        sp = getSharedPreferences("config",MODE_PRIVATE);
    }
    private void setListener() {

    }

    private void isCompleteSet(){
        boolean isCompleteSet = sp.getBoolean("complete_security_set",false);
        if(isCompleteSet){

        }else{
            Intent intent = new Intent(this,SecuritySetting1Activity.class);
            startActivity(intent);
            finish();
        }
    }

    public void enterInputSetting(View view){
        Intent intent = new Intent(this,SecuritySetting1Activity.class);
        startActivity(intent);
        finish();
    }

}
