package example.com.mymobilesafe.taskmanage;

import android.os.Bundle;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.mymobilesafe.View.CustomTitleBar;
import example.com.mymobilesafe.appManage.AppInfo;
import example.com.mymobilesafe.R;
import example.com.mymobilesafe.home.BaseActivity;
import example.com.mymobilesafe.util.GloabalTools;

public class TaskManagerActivity extends BaseActivity {

    private ArrayList<AppInfo> runingAppList = new ArrayList<AppInfo>();

    /**圆形进度条*/
    private LinearLayout llProgressLayout ;
    /***app列表*/
    private ListView lvAppList;
    /**app类型*/
    private TextView tvAppType;

    private TextView tvRunningAppCount;

    private TextView tvMem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomTitleBar.getTitleBar(this,"进程管理",true);
        setContentView(R.layout.activity_task_manager);
        initView();
        initData();
    }

    @Override
    public void back() {
        this.finish();
    }

    @Override
    public void initView() {
        llProgressLayout = (LinearLayout) findViewById(R.id.ll_task_manager_progress);
        lvAppList = (ListView) findViewById(R.id.lv_task_manager_applist);
        tvAppType = (TextView) findViewById(R.id.tv_taskmanager_app_type);
        tvRunningAppCount = (TextView) findViewById(R.id.tv_taskmanager_app_count);
        tvMem = (TextView) findViewById(R.id.tv_taskmanager_mem);
        final MyAppAdapter myAppAdapter = new MyAppAdapter();
        lvAppList.setAdapter(myAppAdapter);
    }

    @Override
    public void initData() {
        int runCount = GloabalTools.getRunningAppCount(this);
        long availMem = GloabalTools.getAvailMemSize(this);
        long totalMem = GloabalTools.getTotalMemSize(this);
        tvRunningAppCount.setText("正在运行："+ runCount + "个");
        tvMem.setText("剩余/总内存:" + Formatter.formatFileSize(this,availMem) + "/" + Formatter.formatFileSize(this,totalMem));
    }


    private class MyAppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return runingAppList.size() ;
        }

        @Override
        public Object getItem(int position) {
            return runingAppList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AppInfo appInfo ;

            ViewHolder viewHolder;
            if(convertView != null && convertView instanceof RelativeLayout){
                viewHolder = (ViewHolder) convertView.getTag();
            }else{
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(TaskManagerActivity.this).inflate(R.layout.activity_app_manager_listitem,null);
                viewHolder.tvAppName = (TextView) convertView.findViewById(R.id.tv_appmanager_app_name);
                viewHolder.tvLocation = (TextView) convertView.findViewById(R.id.tv_appmanager_location);
                viewHolder.ivAppIcon = (ImageView) convertView.findViewById(R.id.iv_appmanager_appicon);
                convertView.setTag(viewHolder);
            }
            AppInfo runingAppInfo = (AppInfo) getItem(position);
            viewHolder.tvAppName.setText("");

            viewHolder.ivAppIcon.setImageDrawable(runingAppInfo.icon);


            return convertView;
        }

        class ViewHolder {
            TextView tvLocation;
            TextView tvAppName;
            ImageView ivAppIcon;
        }

    }


}
