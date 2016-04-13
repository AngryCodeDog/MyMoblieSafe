package example.com.mymobilesafe.hightools;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.home.BaseActivity;
import example.com.mymobilesafe.util.dao.NumberAddressQueryUtils;

/**
 * Created by zyp on 3/28/16.
 */
public class SearchPhoneAreaActivity extends BaseActivity {

    private EditText mEtPhoneNumber ;
    private TextView mTvPhoneNumberArea;
    private Button mBtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_phone_number);
        findView();
        setListener();
    }

    private void findView() {
        mEtPhoneNumber = (EditText) findViewById(R.id.search_phone_number_et);
        mTvPhoneNumberArea = (TextView) findViewById(R.id.search_phone_number_tv);
        mBtSearch = (Button) findViewById(R.id.search_phone_number_bt);
    }

    private void setListener(){
        mBtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(mEtPhoneNumber.getText().toString())){
                    Animation shake = AnimationUtils.loadAnimation(SearchPhoneAreaActivity.this, R.anim.shake);
                    findViewById(R.id.search_phone_number_et).startAnimation(shake);
                    searchNumberArea(mEtPhoneNumber.getText().toString());
                }
            }
        });
        mEtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchNumberArea(s.toString());
            }
        });

    }


    public void searchNumberArea(String number){
        if("".equals(number)){

            Toast.makeText(SearchPhoneAreaActivity.this,"请输入号码",Toast.LENGTH_SHORT).show();
        }else{
            //去查找数据
            String address = "";
            try {
                address = NumberAddressQueryUtils.queryNumber(mEtPhoneNumber.getText().toString());
            }catch (Exception e){
                address = "暂无此号码";
            }
            mTvPhoneNumberArea.setText(address);

        }
    }

}
