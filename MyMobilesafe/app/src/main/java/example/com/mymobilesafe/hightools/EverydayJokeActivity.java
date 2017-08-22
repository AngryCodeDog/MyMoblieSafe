package example.com.mymobilesafe.hightools;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.View.CustomTitleBar;
import example.com.mymobilesafe.bean.JokeResponse;
import example.com.mymobilesafe.bean.SRResponse;
import example.com.mymobilesafe.home.BaseActivity;
import example.com.mymobilesafe.netutil.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EverydayJokeActivity extends BaseActivity {
    private TextView tvJoke;
    private int pagenum = 1;
    private int jokeindex = 1;
    private JokeResponse jokeResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomTitleBar.getTitleBar(this,"每日一笑",true);
        setContentView(R.layout.activity_everyday_joke);
        initView();
        toReqJoke();
    }

    @Override
    public void back() {
        this.finish();
    }

    @Override
    public void initView() {
        tvJoke = (TextView) findViewById(R.id.tv_joke_show);

    }

    @Override
    public void initData() {

    }

    public void onClick(View view){
        if(view.getId() == R.id.bt_again_joke){
            if(jokeindex < 20){
                tvJoke.setText(jokeResponse.getResult().getList().get(jokeindex).getContent());
                jokeindex ++;
            }else{
                jokeindex = 1;
                //去请求笑话
                toReqJoke();
            }

        }
    }

    private void toReqJoke() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jisuxhdq.market.alicloudapi.com/")
                //可以接收自定义的Gson，当然也可以不传
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService testService = retrofit.create(APIService.class);
        //拿到代理对象，然后调用该方法
        Call<JokeResponse> call = testService.reqJoke(pagenum+"","20","addtime");
        //用法和OkHttp的call如出一辙,不同的是它的回调在主线程；
        call.enqueue(new Callback<JokeResponse>() {
            @Override
            public void onResponse(Call<JokeResponse> call, Response<JokeResponse> response) {
                jokeResponse = response.body();
                //请求之后设置第一个
                tvJoke.setText(jokeResponse.getResult().getList().get(0).getContent());
            }

            @Override
            public void onFailure(Call<JokeResponse> call, Throwable t) {
                tvJoke.setText("笑话没有了！");

            }
        });
        pagenum++;
    }
}
