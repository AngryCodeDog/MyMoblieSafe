package example.com.mymobilesafe.hightools;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.View.CustomTitleBar;
import example.com.mymobilesafe.bean.SRResponse;
import example.com.mymobilesafe.home.BaseActivity;
import example.com.mymobilesafe.netutil.APIService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SmartRobootActivity extends BaseActivity {
    EditText etSRReqInfo ;
    TextView tvSRResInfo;
    Button btSRReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomTitleBar.getTitleBar(this,"智能聊天机器人",true);
        setContentView(R.layout.activity_smart_roboot);
        initView();
    }

    @Override
    public void initView() {
        etSRReqInfo = (EditText) findViewById(R.id.et_smart_roboot_req_info);
        tvSRResInfo = (TextView) findViewById(R.id.tv_smart_roboot_response);
        btSRReq = (Button) findViewById(R.id.bt_smart_roboot_req);

        btSRReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = etSRReqInfo.getText().toString();
                reqSmartRoboot(info,"","","");
            }
        });


    }

    @Override
    public void initData() {

    }

    @Override
    public void back() {
        finish();
    }

    public void reqSmartRoboot(String info, String type, String loc, String userid){

        Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("http://op.juhe.cn/")
          //可以接收自定义的Gson，当然也可以不传
          .addConverterFactory(GsonConverterFactory.create())
          .build();
        APIService testService = retrofit.create(APIService.class);
        //拿到代理对象，然后调用该方法
        Call<SRResponse> call = testService.requestSmartRoboot(info,type,loc,userid,APIService.MYKEY);
        //用法和OkHttp的call如出一辙,不同的是它的回调在主线程；
        call.enqueue(new Callback<SRResponse>() {
            @Override
            public void onResponse(Call<SRResponse> call, Response<SRResponse> response) {
                tvSRResInfo.setText(response.body().getResult().getText());

            }

            @Override
            public void onFailure(Call<SRResponse> call, Throwable t) {
                tvSRResInfo.setText("失败了");

            }
        });

    }
}
