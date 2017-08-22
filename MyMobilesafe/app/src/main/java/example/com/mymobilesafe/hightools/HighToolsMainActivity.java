package example.com.mymobilesafe.hightools;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.View.CustomTitleBar;
import example.com.mymobilesafe.hightools.baidulLocation.BaiduLocation;
import example.com.mymobilesafe.home.BaseActivity;

public class HighToolsMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomTitleBar.getTitleBar(this,"高级工具",true);
        setContentView(R.layout.activity_high_tools_main);
        initView();
        initData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void back() {
        finish();
    }

    public void onClick(View view){
        if(view.getId() == R.id.high_tools_phone_number_address_tv){
            Intent intent7 = new Intent(HighToolsMainActivity.this, SearchPhoneAreaActivity.class);
            startActivity(intent7);
        }

        if(view.getId() == R.id.high_tools_baidu_location){
            Intent intent7 = new Intent(HighToolsMainActivity.this, BaiduLocation.class);
            startActivity(intent7);
        }
        if(view.getId() == R.id.high_tools_smart_roboot){
            Intent intent7 = new Intent(HighToolsMainActivity.this, SmartRobootActivity.class);
            startActivity(intent7);
        }
        if(view.getId() == R.id.high_tools_everyday_joke){
            Intent intent7 = new Intent(HighToolsMainActivity.this, SmartRobootActivity.class);
            startActivity(intent7);
        }
    }

}
