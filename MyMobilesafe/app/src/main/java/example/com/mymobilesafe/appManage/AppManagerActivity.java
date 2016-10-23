package example.com.mymobilesafe.appManage;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

    private TextView tvAppType;

    private List<AppInfo> appInfos = new ArrayList<AppInfo>();

    private List<AppInfo> userAppList = new ArrayList<>();

    private List<AppInfo> systemAppList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomTitleBar.getTitleBar(this,"应用管理",true);
        setContentView(R.layout.activity_app_manager);
        initView();
        setListener();
    }

    @Override
    public void initView() {
        llProgressLayout = (LinearLayout) findViewById(R.id.ll_app_manager_progress);
        lvAppList = (ListView) findViewById(R.id.lv_app_manager_applist);
        tvAppType = (TextView) findViewById(R.id.tv_appmanager_app_type);
        final MyAppAdapter myAppAdapter = new MyAppAdapter();
        lvAppList.setAdapter(myAppAdapter);

        initData();
        llProgressLayout.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();

    }

    private void setListener(){
        lvAppList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem > userAppList.size()){
                    tvAppType.setText("系统程序："+systemAppList.size() + "个");
                }else {
                    tvAppType.setText("用户程序："+userAppList.size() + "个");
                }
            }
        });
    }

    @Override
    public void initData() {
        appInfos = GloabalTools.getAppInfo(this);
        for(AppInfo appInfo : appInfos){
            if(appInfo.userApp){
                userAppList.add(appInfo);
            }else{
                systemAppList.add(appInfo);
            }
        }
    }

    @Override
    public void back() {
        this.finish();
    }

    private class MyAppAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return userAppList.size() + 1 + systemAppList.size() + 1;
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
            AppInfo appInfo ;
            if(position == 0){
                TextView textView = new TextView(AppManagerActivity.this);
                textView.setText("用户软件："+userAppList.size()+"个");
                textView.setBackgroundColor(Color.GRAY);
                textView.setTextColor(Color.BLACK);
                return textView;
            }else if(position <= userAppList.size()){
                appInfo = userAppList.get(position - 1);
            }else if(position == userAppList.size() + 1){
                TextView textView = new TextView(AppManagerActivity.this);
                textView.setText("系统软件："+systemAppList.size()+"个");
                textView.setBackgroundColor(Color.GRAY);
                textView.setTextColor(Color.BLACK);
                return textView;
            }else {
                appInfo = systemAppList.get(position - userAppList.size() - 2);
            }

            ViewHolder viewHolder;
            if(convertView != null && convertView instanceof RelativeLayout){
                viewHolder = (ViewHolder) convertView.getTag();
            }else{
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(AppManagerActivity.this).inflate(R.layout.activity_app_manager_listitem,null);
                viewHolder.tvAppName = (TextView) convertView.findViewById(R.id.tv_appmanager_app_name);
                viewHolder.tvLocation = (TextView) convertView.findViewById(R.id.tv_appmanager_location);
                viewHolder.ivAppIcon = (ImageView) convertView.findViewById(R.id.iv_appmanager_appicon);
                convertView.setTag(viewHolder);
            }
            viewHolder.tvAppName.setText(appInfo.name);
            if(appInfo.inRom){
                viewHolder.tvLocation.setText("位置：手机内存");
            }else{
                viewHolder.tvLocation.setText("位置：外存储设备");
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
