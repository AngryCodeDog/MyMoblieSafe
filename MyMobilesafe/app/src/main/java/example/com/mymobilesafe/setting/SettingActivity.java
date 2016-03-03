package example.com.mymobilesafe.setting;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.setting.view.SettingItemView;

public class SettingActivity extends Activity {
    private SettingItemView settingItemView ;
    private boolean isOpenUpdate = true;
    private SharedPreferences sp ;


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

        sp = getSharedPreferences("config",MODE_PRIVATE);
    }

    /**
     *设置监听器
     *<p>author:zyp
     *<p>create at 16-1-10 下午5:29
     **/
    private void setListener() {
        Boolean isUpdate = sp.getBoolean("update",false);
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
            editor.putBoolean("update",true);
        }else{
            editor.putBoolean("update",false);
        }
        editor.commit();
    }

    public boolean getUpdateStatus(){
        return settingItemView.isChecked();
    }

}
