package example.com.mymobilesafe.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.activity.BaseActivity;
import example.com.mymobilesafe.setting.view.SettingItemView;
import example.com.mymobilesafe.util.GloabalTools;

public class SettingActivity extends BaseActivity {
    private SettingItemView settingItemView ;
    private boolean isOpenUpdate = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        initView();
        setListener();
    }


    /**
     *初始化组件
     *<p>author:zyp
     *<p>create at 16-1-10 下午5:29
     **/
    private void initView() {
        settingItemView = (SettingItemView) findViewById(R.id.setting_layout_itemview);
        settingItemView.setOncheck();
    }

    /**
     *设置监听器
     *<p>author:zyp
     *<p>create at 16-1-10 下午5:29
     **/
    private void setListener() {
        Boolean isUpdate = sp.getBoolean(GloabalTools.AUTO_UPDATE,false);
        if(isUpdate){
            settingItemView.setOncheck();
        }else{
            settingItemView.setOffCheck();
        }

        final SharedPreferences.Editor editor = sp.edit();
        settingItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpenUpdate){
                    settingItemView.setOncheck();
                    isOpenUpdate = false;
                }else{
                    settingItemView.setOffCheck();
                    isOpenUpdate = true;
                }
                saveUpdateSetting(editor);
            }
        });



    }

    public void saveUpdateSetting(SharedPreferences.Editor editor){
        if(getUpdateStatus()){
            editor.putBoolean(GloabalTools.AUTO_UPDATE,true);
        }else{
            editor.putBoolean(GloabalTools.AUTO_UPDATE,false);
        }
        editor.commit();
    }

    public boolean getUpdateStatus(){
        return settingItemView.isChecked();
    }

}
