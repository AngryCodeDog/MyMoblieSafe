package example.com.mymobilesafe.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.View.CustomTitleBar;
import example.com.mymobilesafe.home.BaseActivity;
import example.com.mymobilesafe.setting.view.SettingItemView;
import example.com.mymobilesafe.util.GloabalTools;

/**
 * 设置界面
 */
public class SettingActivity extends BaseActivity {
    private SettingItemView siv_isUpdate;
    private SettingItemView siv_callListener;
    private boolean isOpenUpdate = true;
    private boolean isListeningCall = true;
    private  Intent intentToCallListen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomTitleBar.getTitleBar(this,"设置中心",true);
        setContentView(R.layout.setting_layout);
        initView();
        initData();
        setListener();
    }


    /**
     *初始化组件
     *<p>author:zyp
     *<p>create at 16-1-10 下午5:29
     **/
    public void initView() {
        siv_isUpdate = (SettingItemView) findViewById(R.id.setting_layout_auto_update);
        siv_isUpdate.setOncheck();
        siv_callListener = (SettingItemView) findViewById(R.id.setting_layout_itemview_call_listener);

        intentToCallListen = new Intent(SettingActivity.this,MyCallListenerService.class);

        isOpenUpdate = sp.getBoolean(GloabalTools.AUTO_UPDATE,false);


        if(isOpenUpdate){
            siv_isUpdate.setOncheck();
            isOpenUpdate = false;
        }else{
            siv_isUpdate.setOffCheck();
            isOpenUpdate = true;
        }
        isListeningCall = GloabalTools.isServiceRunning(this,MyCallListenerService.class.getName());
        if(isListeningCall){
            siv_callListener.setOncheck();
        }else{
            siv_callListener.setOffCheck();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void back() {
        finish();
    }

    /**
     *设置监听器
     *<p>author:zyp
     *<p>create at 16-1-10 下午5:29
     **/
    private void setListener() {
        siv_isUpdate.setOnClickListener(MyOnClickListener);
        siv_callListener.setOnClickListener(MyOnClickListener);
    }

    View.OnClickListener MyOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences.Editor editor = sp.edit();
            if(v.getId() == R.id.setting_layout_auto_update){
               if(isOpenUpdate){
                   siv_isUpdate.setOncheck();
                   isOpenUpdate = false;
               }else{
                   siv_isUpdate.setOffCheck();
                   isOpenUpdate = true;
               }
                editor.putBoolean(GloabalTools.AUTO_UPDATE,siv_isUpdate.isChecked());
            }else if(v.getId() == R.id.setting_layout_itemview_call_listener){
                isListeningCall = GloabalTools.isServiceRunning(SettingActivity.this,MyCallListenerService.class.getName());
                if(!isListeningCall){
                    siv_callListener.setOncheck();
                    isListeningCall = false;
                    //开启来电监听服务
                    startService(intentToCallListen);
                }else{
                    siv_callListener.setOffCheck();
                    isListeningCall = true;
                    //停止来电监听服务
                    stopService(intentToCallListen);
                }
            }
            editor.commit();
        }
    };

}
