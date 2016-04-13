package example.com.mymobilesafe.hightools;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.home.BaseActivity;

public class HighToolsMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_tools_main);
    }

    public void onClick(View view){
        if(view.getId() == R.id.high_tools_phone_number_address_tv){
            Intent intent7 = new Intent(HighToolsMainActivity.this, SearchPhoneAreaActivity.class);
            startActivity(intent7);
        }
    }
}
