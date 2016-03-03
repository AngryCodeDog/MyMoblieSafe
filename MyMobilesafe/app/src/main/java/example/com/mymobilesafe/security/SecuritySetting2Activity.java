package example.com.mymobilesafe.security;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.setting.view.SettingItemView;

public class SecuritySetting2Activity extends SecurityBaseActivity {
    SettingItemView bind_sim_card_view ;
    private String bind_sim_card_number ;
    private TelephonyManager tm ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_setting2);
        initView();
        initData();
        setListener();
    }

    @Override
    public void showNext() {
        Intent intent = new Intent(this,SecuritySetting3Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);
        finish();
    }

    @Override
    public void showPre() {
        Intent intent = new Intent(this,SecuritySetting1Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
        this.finish();
    }

    private void initView() {

    }
    private void initData() {
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);


        bind_sim_card_view = (SettingItemView) findViewById(R.id.security_setting2_bind_sim_card);
        //查看是否设置过绑定，并初始化组件。
        bind_sim_card_number = sp.getString("sim_card_number",null);
        if(bind_sim_card_number != null){
            bind_sim_card_view.setOncheck();
        }else{
            bind_sim_card_view.setOffCheck();
        }

    }
    private void setListener() {
        bind_sim_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sim_card_number = tm.getSimSerialNumber();
                if(bind_sim_card_view.isChecked()){
                    bind_sim_card_view.setOffCheck();
                    saveBindSimCardState(null);
                }else{
                    bind_sim_card_view.setOncheck();
                    saveBindSimCardState(sim_card_number);
                }

            }
        });
    }



}
