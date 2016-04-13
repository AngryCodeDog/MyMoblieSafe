package example.com.mymobilesafe.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.hightools.HighToolsMainActivity;
import example.com.mymobilesafe.security.SecurityMainActivity;
import example.com.mymobilesafe.setting.SettingActivity;
import example.com.mymobilesafe.util.GloabalTools;
import example.com.mymobilesafe.util.MD5Util;

/**
 * Created by zyp on 16-1-3.
 */
public class HomeActivity extends Activity{
    private GridView gv_function ;
    private SharedPreferences sp ;

    private String[] function_name = {
            "手机防盗","通讯卫士","软件管理",
            "进程管理","流浪统计","手机杀毒",
            "缓存清理","高级工具","设置中心"
    };
    private int[] funciton_image = {
            R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app,
            R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
            R.drawable.sysoptimize,R.drawable.atools,R.drawable.settings
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        initView();
        initData();
        setListener();
    }

    private void initData() {
        sp = getSharedPreferences(GloabalTools.CONFIG,MODE_PRIVATE);
    }


    public void initView(){
        gv_function = (GridView)findViewById(R.id.home_gv_functionlist);
        gv_function.setAdapter(new MyGridViewAdapter());
    }

    private void setListener() {
        gv_function.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        showSetDialog();
                        break;
                    case 7:
                        Intent intent7 = new Intent(HomeActivity.this, HighToolsMainActivity.class);
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8 = new Intent(HomeActivity.this,SettingActivity.class);
                        startActivity(intent8);
                        break;

                }
            }
        });
    }

    private void showSetDialog() {
        if(isSetupPwd()){
            //如果设置过密码，则显示密码输入对话框
            showIputDialog();
        }else{
            //如果没有设置过密码，则显示设置密码对话框
            showSetPwdDialog();
        }
    }

    private void showSetPwdDialog() {
        //----------配置Dialog初始化---------------------------------------
        final Dialog dialog = new Dialog(this);
        //这句话（设置没有标题栏）必须在setContentView()之前声明，不然会报错
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.setting_pwd_dialog_layout);
        dialog.show();

        //----------对Dialog进行监听------------------------
        final EditText et_firstPwd = (EditText) dialog.findViewById(R.id.setting_pwd_dialog_et_firstpwd);
        final EditText et_secondPwd = (EditText) dialog.findViewById(R.id.setting_pwd_dialog_et_secondpwd);
        Button bt_confirm = (Button) dialog.findViewById(R.id.setting_pwd_dialog_bt_confirm);
        Button bt_cancel = (Button) dialog.findViewById(R.id.setting_pwd_dialog_bt_cancel);

        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.setting_pwd_dialog_bt_confirm:
                        String firstPwd = et_firstPwd.getText().toString();
                        String secondPwd = et_secondPwd.getText().toString();
                        if(firstPwd.equals("") || secondPwd.equals("")){
                            showToastInCenter("请输入完整信息");
                            return ;
                        }
                        if(firstPwd.equals(secondPwd)){
                            String password_md5 = MD5Util.encryptWithMD5(firstPwd);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(GloabalTools.SECURITY_PWD,password_md5);
                            editor.commit();
//                            Toast.makeText(HomeActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            //设置成功后，直接进入手机防盗页面
                            toPhoneSecurityActivity();
                        }
                        break;
                    case R.id.setting_pwd_dialog_bt_cancel:
                        dialog.dismiss();
                        break;
                }
            }
        };

        //设置监听
        bt_confirm.setOnClickListener(myOnClickListener);
        bt_cancel.setOnClickListener(myOnClickListener);
    }

    private void toPhoneSecurityActivity(){
        Intent intent = new Intent(this,SecurityMainActivity.class);
        startActivity(intent);
    }

    private void showIputDialog() {
        //---------------配置Dialog初始化-------------------------
        final Dialog dialog = new Dialog(this);
        //这句话（设置没有标题栏）必须在setContentView()之前声明，不然会报错
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.input_pwd_dialog_layout);
        dialog.show();

        //-----------------对Dialog进行监听--------------------------------
        final EditText et_firstPwd = (EditText) dialog.findViewById(R.id.input_pwd_dialog_et_pwd);
        Button bt_confirm = (Button) dialog.findViewById(R.id.input_pwd_dialog_bt_confirm);
        Button bt_cancel = (Button) dialog.findViewById(R.id.input_pwd_dialog_bt_cancel);

        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = sp.getString(GloabalTools.SECURITY_PWD,null);
                switch (v.getId()){
                    case R.id.input_pwd_dialog_bt_confirm:
                        String firstPwd = et_firstPwd.getText().toString();
                        //要使用md5加密的密码去比较
                        String passwordTemp = MD5Util.encryptWithMD5(firstPwd);
                        if(passwordTemp.equals(pwd)){
                           //如果密码正确，则进入
//                            Toast.makeText(HomeActivity.this,"密码正确",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            toPhoneSecurityActivity();
                        }else{
                            //如如果密码错误，则提示错误
                            Toast.makeText(HomeActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.input_pwd_dialog_bt_cancel:
                        dialog.dismiss();
                        break;
                }
            }
        };
        //设置监听
        bt_confirm.setOnClickListener(myOnClickListener);
        bt_cancel.setOnClickListener(myOnClickListener);
    }


    private boolean isSetupPwd(){
        String pwd = sp.getString(GloabalTools.SECURITY_PWD,null);
        return !TextUtils.isEmpty(pwd);
    }

    private void showToastInCenter(String msg){
        Toast toast = Toast.makeText(HomeActivity.this,msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }



    class MyGridViewAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return function_name.length;
        }
        @Override
        public Object getItem(int position) {
            return function_name[position];
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.home_layout_gridview_item,null);
            ImageView img = (ImageView) view.findViewById(R.id.home_gridview_item_image);
            TextView tv_desc = (TextView) view.findViewById(R.id.home_gtidview_item_text);
            img.setBackgroundResource(funciton_image[position]);
            tv_desc.setText(function_name[position]);

            return view;
        }
    }

}
