package example.com.mymobilesafe.security;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.util.GloabalTools;

/**
 * 设置界面3，设置安全号码
 */
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
        //判断有没有设置安全号码
        if(mEtNumber.getText().toString().equals("")){
            //弹窗提示没有设置安全号码，是否继续设置或者不设置马上离开
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("警告").setMessage("没有设置安全号码！丢失后将不能远程控制该手机，是否继续设置安全号码？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });
            builder.setNegativeButton("离开", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveSecurityNumber(null);
                    toSetting4Activity();
                }
            });
            builder.create().show();
        }else{
            saveSecurityNumber(mEtNumber.getText().toString());
            toSetting4Activity();
        }
    }

    private void toSetting4Activity(){
        Intent intent = new Intent(this,SecuritySetting4Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);
        finish();
    }

    private void saveSecurityNumber(String number){
        //保存安全号码
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(GloabalTools.SECURITY_PHONE_NUMBER,number);
        editor.commit();
    }

    @Override
    public void showPre() {
        Intent intent = new Intent(this,SecuritySetting2Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
        this.finish();
    }

    public void initView() {
        mEtNumber = (EditText) findViewById(R.id.security_setting3_telephone_et);
    }
    public void initData() {
        String security_phone_number = sp.getString(GloabalTools.SECURITY_PHONE_NUMBER,null);
        if(security_phone_number != null){
            mEtNumber.setText(security_phone_number);
        }

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
