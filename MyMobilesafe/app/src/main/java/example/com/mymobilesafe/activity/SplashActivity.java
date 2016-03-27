package example.com.mymobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.util.Download;
import example.com.mymobilesafe.util.DownloadHelper;
import example.com.mymobilesafe.util.GloabalTools;
import example.com.mymobilesafe.util.StreamTools;

/**
 * 启动界面，进行加载数据，更新提示
 */
public class SplashActivity extends BaseActivity {
    /**
     * 进入主页面
     */
    private static final int ENTER_HOME = 0;
    /**
     * 显示更新弹窗
     */
    private static final int SHOW_UPDATE_DIALOG = 1;
    private static final int URL_ERROR = 2;
    private static final int NETWORK_ERROR = 3;
    private static final int JSON_ERROR = 4;
    private static final int SUCCEED_DOWNLOAD = 5;
    private static final int IO_ERROR = 6;

    private TextView tv_version;
    private AlphaAnimation aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(1000);
        findViews();
        //这里应该是默认设置为自动更新软件的

        //获取是否需要自动去更新软件
        Boolean isUpdate = sp.getBoolean(GloabalTools.AUTO_UPDATE,false);
        if(isUpdate){//这里暂时没有更新，（不过更新这个功能是可以的，已经测试过）
            checkNewVersion();
        }else {
            //进入主页面
            enterHome();
        }

    }

    /**
     * 初始化组件
     * <p>author:zyp
     * <p>create at 16-1-3 下午2:26
     **/
    private void findViews() {
        tv_version = (TextView) findViewById(R.id.tv_splash_version);
        tv_version.setText("版本" + getVersionName());
        View view = findViewById(R.id.rl_root_splash);
        view.setAnimation(aa);
    }

    /**
     * 检查版本信息
     * author:zyp
     * create at 16-1-3 下午12:45
     **/
    private void checkNewVersion() {
        new Thread() {
            public void run() {
                Message msg = new Message();
                long startTime = System.currentTimeMillis();
                try {
                    URL url = new URL("http://192.168.1.101:8080/update.html");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        //连接成功
                        InputStream is = conn.getInputStream();
                        String result = StreamTools.getStringFromInput(is);
                        JSONObject jso = new JSONObject(result);
                        String version = jso.getString("version");
                        String description = jso.getString("description");
                        String apkurl = jso.getString("apkurl");
                        Log.d("TAG",apkurl);
                        if (getVersionName().equals(version)) {
                            msg.what = ENTER_HOME;
                        } else {
                            msg.what = SHOW_UPDATE_DIALOG;
                            Bundle bundle = new Bundle();
                            bundle.putString("description", description);
                            bundle.putString("apkurl", apkurl);
                            msg.setData(bundle);
                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    msg.what = URL_ERROR;

                } catch (IOException e) {
                    e.printStackTrace();
                    msg.what = NETWORK_ERROR;

                } catch (JSONException e) {
                    e.printStackTrace();
                    msg.what = JSON_ERROR;

                } finally {
                    long endTime = System.currentTimeMillis();
                    long dTime = endTime - startTime;
                    if (dTime < 2000) {
                        try {
                            Thread.sleep(2000 - dTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHanler.sendMessage(msg);
                }
            }
        }.start();
    }

    private Handler mHanler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ENTER_HOME:
                    enterHome();
                    break;
                case SHOW_UPDATE_DIALOG:
                    Bundle bundle = msg.getData();
                    String description = bundle.getString("description");
                    String apkurl = bundle.getString("apkurl");
                    showUpdateDialog(description, apkurl);
                    break;
                case URL_ERROR:
                    Toast.makeText(SplashActivity.this, "网址异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case NETWORK_ERROR:
                    Toast.makeText(SplashActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case JSON_ERROR:
                    Toast.makeText(SplashActivity.this, "JSON解析出错", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
            }
        }
    };

    /**
     * 进入主页面
     * <p>author:zyp
     * <p>create at 16-1-3 下午2:07
     **/
    private void enterHome() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        this.finish();//把启动页面关掉，防止可以返回到此页面
    }

    /**
     * 显示更新弹窗
     * <p>author:zyp
     * <p>create at 16-1-3 下午12:49
     **/
    private void showUpdateDialog(String description, final String apkurl) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("有新版本");
        builder.setMessage(description);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(apkurl));
//                startActivity(intent);
                try {
                    Log.d("TAG","去下载");
                    downloadFile(apkurl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setPositiveButton("暂不更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enterHome();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }


    private void downloadFile(final String apkurl) throws IOException {
        new AsyncTask<Void,Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                int msg = -1;
                URL url = null;
                try {
                    Log.d("TAG","另起一个线程去下载apk");
                    url = new URL(apkurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    if (conn.getResponseCode() == 200) {
                        boolean isSucceed = DownloadHelper.downloadFile(conn.getInputStream(),conn.getContentLength(),apkurl);
                        Log.d("TAG","isSucceed："+isSucceed);
                        if(isSucceed){
                            msg = SUCCEED_DOWNLOAD;
                            Log.d("TAG","下载成功");
                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    msg = URL_ERROR;
                } catch (IOException e) {
                    e.printStackTrace();
                    msg = IO_ERROR;
                }
                return msg;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                switch (integer){
                    case SUCCEED_DOWNLOAD:
                        String filepath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
                        filepath += apkurl.substring(apkurl.lastIndexOf("/")+1);
                        Toast.makeText(SplashActivity.this,"下载成功文件保存在:"+filepath,Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Log.d("TAG","下载失败");
                        break;
                    case URL_ERROR:
                        break;
                    case IO_ERROR:
                        break;
                }

            }
        }.execute();

    }

    private String getVersionName() {
        //用来获取apk管理器
        PackageManager pm = getPackageManager();
        try {
            //获取apk的清单文件
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

}
