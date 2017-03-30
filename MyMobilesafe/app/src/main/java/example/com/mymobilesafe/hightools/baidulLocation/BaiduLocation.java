package example.com.mymobilesafe.hightools.baidulLocation;

import android.os.Bundle;
import android.view.Window;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.home.BaseActivity;

public class BaiduLocation extends BaseActivity {

    MapView mMapView = null;


    public LocationClient mLocationClient = null;

    public MyLocationListener myLocationListener = new MyLocationListener();

    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_baidu_location);
        initView();
        initData();
    }

    @Override
    public void initView() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
    }

    @Override
    public void initData() {
        //获取地图控件引用
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //实例化mLocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myLocationListener);
        myLocationListener.setReceiver(new MyLocationListener.Receiver() {
            @Override
            public void receive(BDLocation location) {
                showLocationMap(location);
            }
        });
        //设置定位参数
        initMapSet();
        //开始定位
        mLocationClient.start();
    }



    private void showLocationMap(BDLocation location){
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
            .accuracy(location.getRadius())
            // 此处设置开发者获取到的方向信息，顺时针0-360
            .direction(100).latitude(location.getLatitude())
            .longitude(location.getLongitude()).build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
//        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
//            .fromResource(R.mipmap.dingwei);
//        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
        LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
        //设置默认放大系数，数值越小放大倍数就越小，反之越大。
        MapStatusUpdate mup = MapStatusUpdateFactory.newLatLngZoom(ll,18);
        mBaiduMap.animateMapStatus(mup);
//        mBaiduMap.setMyLocationConfigeration(config);
    }

   private void initMapSet() {
       LocationClientOption option = new LocationClientOption();
       //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
       option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

       //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");

       //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
       int span=0;
       option.setScanSpan(span);

       //可选，设置是否需要地址信息，默认不需要(定位返回的结果包含地址信息)
       option.setIsNeedAddress(true);

        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);

        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
       option.setLocationNotify(true);

        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationDescribe(true);

        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        option.setIsNeedLocationPoiList(true);

        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
       option.setIgnoreKillProcess(false);

        //可选，默认false，设置是否收集CRASH信息，默认收集
//       option.SetIgnoreCacheException(false);

        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
//       option.setEnableSimulateGps(false);

        mLocationClient.setLocOption(option);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView = null;
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
