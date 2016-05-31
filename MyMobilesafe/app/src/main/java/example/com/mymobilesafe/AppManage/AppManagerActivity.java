package example.com.mymobilesafe.AppManage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.View.CustomTitleBar;
import example.com.mymobilesafe.home.BaseActivity;
import example.com.mymobilesafe.util.GloabalTools;

public class AppManagerActivity extends BaseActivity {


    private LinearLayout llProgressLayout ;

    private ListView lvAppList;

    List<AppInfo> appInfos = new ArrayList<AppInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomTitleBar.getTitleBar(this,"应用管理",true);
        setContentView(R.layout.activity_app_manager);
        initView();

    }

    @Override
    public void initView() {
        llProgressLayout = (LinearLayout) findViewById(R.id.ll_app_manager_progress);
        lvAppList = (ListView) findViewById(R.id.lv_app_manager_applist);
        final MyAppAdapter myAppAdapter = new MyAppAdapter();
        lvAppList.setAdapter(myAppAdapter);
        new Thread(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        llProgressLayout.setVisibility(View.GONE);
                        myAppAdapter.notifyDataSetChanged();
                    }
                });

            }
        }.start();
    }

    @Override
    public void initData() {
        appInfos = GloabalTools.getAppInfo(this);
    }

    @Override
    public void back() {
        this.finish();
    }

    private class MyAppAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return appInfos.size();
        }

        @Override
        public AppInfo getItem(int position) {
            return appInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            AppInfo appInfo = getItem(position);
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(AppManagerActivity.this).inflate(R.layout.activity_app_manager_listitem,null);
                viewHolder.tvAppName = (TextView) convertView.findViewById(R.id.tv_appmanager_app_name);
                viewHolder.tvLocation = (TextView) convertView.findViewById(R.id.tv_appmanager_location);
                viewHolder.ivAppIcon = (ImageView) convertView.findViewById(R.id.iv_appmanager_appicon);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvAppName.setText(appInfo.name);

            if(appInfo.inRom){
                viewHolder.tvLocation.setText("手机内存");
            }else {
                viewHolder.tvLocation.setText("外部存储");
            }
            viewHolder.ivAppIcon.setImageDrawable(appInfo.icon);
            return convertView;
        }

        class ViewHolder {
            TextView tvLocation;
            TextView tvAppName;
            ImageView ivAppIcon;
        }

    }



}
