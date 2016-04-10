package com.example.hongchun.myapplication.ui.activity.map;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.example.hongchun.myapplication.R;
import com.example.hongchun.myapplication.ui.activity.BaseNormActivity;
import com.example.hongchun.myapplication.util.MConstants;
import com.example.hongchun.myapplication.util.map.MLocationClientUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ContentView(R.layout.activity_map)
public class MapActivity extends BaseNormActivity {

    @ViewInject(R.id.toolbar)
  private   Toolbar toolbar;
    @ViewInject(R.id.textview_titlename)
    private  TextView textViewTitleName;

    @ViewInject(R.id.button_location)
    private  Button btn_location;

    @ViewInject(R.id.textview_locationaddress)
    private  TextView textViewLocationAddress;

    @ViewInject(R.id.button_navigation)
    private Button buttonNavigation;

    @ViewInject(R.id.button_locationnow)
    private Button btnLocationNow;

    @ViewInject(R.id.button_locationFlag)
    private Button btnLocationFlag;

    @ViewInject(R.id.bmapView)
    private  MapView mMapView;

    private BaiduMap mBaiduMap;

    MLocationClientUtil mLocationClientUtil;
    private BDLocation mLocation;
    private String mSDCardPath;
    private String authinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView(){
        initToolBarAndBackButton(toolbar);
        setToolBarTitle(textViewTitleName, "地图");
        mBaiduMap = mMapView.getMap();
        mMapView.showZoomControls(false);

        mBaiduMap.setOnMarkerClickListener(new MyMarkerListener());
        mLocationClientUtil=new MLocationClientUtil();

        if(initDirs()){
            initNavi();
        }
    }

    @Event(value = {R.id.button_location,R.id.button_navigation,R.id.button_locationnow
                    ,R.id.button_locationFlag},type = View.OnClickListener.class)
    private void onEvenOnclick(View view){
        switch (view.getId()){
            case R.id.button_location:
                mLocationClientUtil.start(this, new MLocationClientUtil.OnMBiaduLocationListener() {
                    @Override
                    public void mCallbackLocation(boolean isLocationOk, String address, BDLocation location) {
                        if (isLocationOk) {
                            textViewLocationAddress.setText(address + " latitude:" + location.getLatitude() + " longitude:" + location.getLongitude());
                        }else {
                            Toast.makeText(MapActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.button_locationnow:
                // 当前位置
                startLocation();
                break;
            case R.id.button_locationFlag:
                addOverLarFlag();
                break;
            case R.id.button_navigation:
                // 导航
                List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
                BDLocation location = LocationClient.getBDLocationInCoorType(mLocation, BDLocation.BDLOCATION_BD09LL_TO_GCJ02);

                BDLocation location1 = new BDLocation();
                location1.setLatitude(39.90882);
                location1.setLongitude(116.39750);
                BDLocation endLocation = LocationClient.getBDLocationInCoorType(location1, BDLocation.BDLOCATION_BD09LL_TO_GCJ02);
                //起始地址
                BNRoutePlanNode sNode = new BNRoutePlanNode(location.getLongitude(), location.getLatitude(),"start", null, BNRoutePlanNode.CoordinateType.GCJ02);
                BNRoutePlanNode eNode = new BNRoutePlanNode(endLocation.getLongitude(),endLocation.getLatitude(),"北京天安门", null, BNRoutePlanNode.CoordinateType.GCJ02);

                list.add(sNode);
                list.add(eNode);
                BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
                break;

        }
    }


    private void startLocation(){
        mLocationClientUtil.start(this, new MLocationClientUtil.OnMBiaduLocationListener() {
            @Override
            public void mCallbackLocation(boolean isLocationOk, String address, BDLocation location) {
                if (isLocationOk) {
                    // map view 销毁后不在处理新接收的位置
                    if (location == null || mMapView == null)
                        return;
                    mLocation = location;
                    float driection = location.getDirection();// 获取方向
                    // 构造定位数据
                    MyLocationData locData = new MyLocationData.Builder()
                            .accuracy(location.getRadius())
                                    // 此处设置开发者获取到的方向信息，顺时针0-360
                            .direction(0).latitude(location.getLatitude())
                            .longitude(location.getLongitude()).build();
                    // 设置定位数据
                    mBaiduMap.setMyLocationData(locData);

                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);

                    MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, bitmapDescriptor);
                    mBaiduMap.setMyLocationConfigeration(myLocationConfiguration);
                    // 地图移动到网点位置
                    LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                    MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                    mBaiduMap.setMapStatus(u);
                    mLocationClientUtil.stopMLocation();

                } else {
                    Toast.makeText(MapActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * 标注周边
     */
    private void addOverLarFlag(){
        for (int i=0;i<4;i++) {
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("number","12345678910"+i);
            hashMap.put("longitude",mLocation.getLongitude()+i);
            hashMap.put("latitude",mLocation.getLatitude()+i);
            hashMap.put("description", ""+mLocation.getAddress()+"："+i);
            addOverLarFlag(hashMap);
        }
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
                //拖拽中
            }
            public void onMarkerDragEnd(Marker marker) {
                //拖拽结束
            }
            public void onMarkerDragStart(Marker marker) {
                //开始拖拽
            }
        });
    }
    /**
     *
     * @param map
     */
    private void addOverLarFlag(HashMap map){
        BitmapDescriptor bitmap=null;
        bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_launcher);

        LatLng latLng=new LatLng(Double.valueOf("" + map.get("latitude")),Double.valueOf(""+map.get("longitude")));

        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions options = new MarkerOptions()
                .position(latLng)  //设置marker的位置
                .icon(bitmap)  //设置marker图标
                .zIndex(9)  //设置marker所在层级
                .draggable(true);  //设置手势拖拽
        //在地图上添加Marker，并显示
        if(mBaiduMap!=null){
            Marker marker=(Marker) mBaiduMap.addOverlay(options);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Map", map);
            marker.setExtraInfo(bundle);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 开启图层定位
        mBaiduMap.setMyLocationEnabled(true);
        startLocation();
    }
    @Override
    protected void onStop() {
        super.onStop();
        // 关闭图层定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClientUtil.stopMLocation();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
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



    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if ( mSDCardPath == null ) {
            return false;
        }
        File f = new File(mSDCardPath, MConstants.BaiDuMapFinal.APP_FOLDER_NAME);
        if ( !f.exists() ) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(
                Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }
    private void initNavi() {
        BaiduNaviManager.getInstance().init(this, mSDCardPath,  MConstants.BaiDuMapFinal.APP_FOLDER_NAME,
                new BaiduNaviManager.NaviInitListener() {
                    @Override
                    public void onAuthResult(int status, String msg) {
                        if (0 == status) {
                            authinfo = "key校验成功!";
                        } else {
                            authinfo = "key校验失败, " + msg;
                        }
                        MapActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MapActivity.this, authinfo, Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    public void initSuccess() {
                        Toast.makeText(MapActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                    }

                    public void initStart() {
                        Toast.makeText(MapActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
                    }

                    public void initFailed() {
                        Toast.makeText(MapActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
                    }
                }, null /*mTTSCallback*/);
    }


    /**
     * 标注点击事件监听
     */
    public class MyMarkerListener implements BaiduMap.OnMarkerClickListener,View.OnClickListener{
        @Override
        public void onClick(View v) {
            mBaiduMap.hideInfoWindow();
        }

        @Override
        public boolean onMarkerClick(Marker marker) {
            LatLng latLng=marker.getPosition();
           HashMap < String, Object > hashMap=(HashMap)marker.getExtraInfo().getSerializable("Map");
            String number=""+hashMap.get("number");
            String description=""+ hashMap.get("description");

            //创建InfoWindow展示的view
              View  showLabelInfoView= LayoutInflater.from(getApplicationContext()).inflate(R.layout.location_label_description_view,null);
            (showLabelInfoView.findViewById(R.id.map_label_llayout_num)).setVisibility(View.GONE);
            (showLabelInfoView.findViewById(R.id.map_label_llayout_type)).setVisibility(View.GONE);
            TextView tv_address=(TextView)showLabelInfoView.findViewById(R.id.map_label_shop_address);
            tv_address.setText(description);
            //定义用于显示该InfoWindow的坐标点
            LatLng pt = new LatLng(latLng.latitude,latLng.longitude);
            //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
            InfoWindow mInfoWindow = new InfoWindow(showLabelInfoView, pt, -47);
            //显示InfoWindow
            mBaiduMap.showInfoWindow(mInfoWindow);
            showLabelInfoView.setOnClickListener(this);

            return true;
        }
    }

    ////  //导航监听器 回调
    public  class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener{
        BNRoutePlanNode mBNRoutePlanNode;
        public DemoRoutePlanListener(BNRoutePlanNode sNode){
            this.mBNRoutePlanNode=sNode;
        }
        @Override
        public void onJumpToNavigator() {
            Intent intent = new Intent(MapActivity.this, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(MConstants.BaiDuMapFinal.ROUTE_PLAN_NODE, mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            Toast.makeText(MapActivity.this,"导航打开失败",Toast.LENGTH_SHORT).show();
        }
    }
}
