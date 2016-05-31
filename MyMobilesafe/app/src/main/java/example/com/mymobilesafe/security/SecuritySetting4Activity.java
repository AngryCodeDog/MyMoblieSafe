package example.com.mymobilesafe.security;

import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.security.receiver.MyAdmin;
import example.com.mymobilesafe.util.GloabalTools;

/**
 * 完成设置界面，打开远程锁屏
 */
public class SecuritySetting4Activity extends SecurityBaseActivity {

    private CheckBox cb_complete_and_lockscreen ;

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
        if(cb_complete_and_lockscreen.isChecked()){
            //保存远程设置
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(GloabalTools.REMOTE_AND_LOCKSCREEN,true);
            toBackMainActivity();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("警告").setMessage("没有开启远程锁屏！将不能远程锁定该手机，是否继续设置远程防护？");
            builder.setPositiveButton("确定",null);
            builder.setNegativeButton("离开", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    toBackMainActivity();
                }
            });
            builder.create().show();
        }
    }

    private void toBackMainActivity() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(GloabalTools.COMPLETE_SECURITY_SET,true);
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

    public void initView() {
        cb_complete_and_lockscreen = (CheckBox) findViewById(R.id.setting_item_cb);
    }
    public void initData() {
        if(sp.getBoolean(GloabalTools.REMOTE_AND_LOCKSCREEN,false)){
            cb_complete_and_lockscreen.setChecked(true);
        }else{
            cb_complete_and_lockscreen.setChecked(false);
        }
    }
    private void setListener() {

    }

    public void openSecurity(View view){
        //如果点击了这个，默认安全设置是有效的，并且默认打开远程锁屏
        //开启远程锁屏
       openAdmin();
    }

    public void openAdmin(){
        if(cb_complete_and_lockscreen.isChecked()){
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            ComponentName mDeviceAdminSample = new ComponentName(this,MyAdmin.class);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,mDeviceAdminSample);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"开启我后，远程锁屏就不会失灵啦！");
            startActivity(intent);
        }

    }

}
