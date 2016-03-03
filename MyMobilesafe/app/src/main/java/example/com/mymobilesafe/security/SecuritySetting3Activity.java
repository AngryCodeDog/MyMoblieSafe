package example.com.mymobilesafe.security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import example.com.mymobilesafe.R;

public class SecuritySetting3Activity extends SecurityBaseActivity {
    private EditText mEtNumber ;
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
        mEtNumber = (EditText) findViewById(R.id.security_setting3_telephone_et);
    }
    private void initData() {

    }
    private void setListener() {

    }


    public void selectContact(View view){
        Intent intent = new Intent(this,SelectContactActivity.class);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            String number = data.getStringExtra("number").replace("-","");
            mEtNumber.setText(number);
        }
    }
}
