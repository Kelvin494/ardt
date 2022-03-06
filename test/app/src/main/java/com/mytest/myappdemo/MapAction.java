package com.mytest.myappdemo;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import com.amap.api.maps.*;
import com.amap.api.maps.model.*;
import com.amap.api.services.core.ServiceSettings;
import android.widget.Toast;
import java.io.*;
public class MapAction extends Activity {
    private MapView mMapView = null;
    private AMap aMap = null;
    private UiSettings mUiSettings =null;
    private LatLng latlng=null;
    private Marker marker=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R .layout .map);
        
        getActionBar().hide();//隐藏actionbar

        //由此开始

        //隐私合规
        ServiceSettings.updatePrivacyShow(this, true, true);
        ServiceSettings.updatePrivacyAgree(this,true);

        //初始化地图容器
        mMapView = findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        //显示地图
        if (aMap == null) {

            aMap = mMapView.getMap();   //显示地图
            if (aMap != null) 
                Toast.makeText(this, "please wait", Toast.LENGTH_SHORT).show();
        }
        aMap.setCustomMapStyle(
            new com.amap.api.maps.model.CustomMapStyleOptions()
            .setEnable(true)
            .setStyleData(getAssetsData("style.data"))
            .setStyleExtraData(getAssetsData("style_extra.data"))
        );
	    

        //显示本地定位蓝点
        MyLocationStyle myLocationStyle= new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位1secand/1times，蓝点不锁定在屏幕中心，定位点方向跟随，定位点移动跟随
        myLocationStyle.showMyLocation(true);//显示蓝点
        aMap.setMyLocationStyle(myLocationStyle);//设定蓝点样式
        aMap.setMyLocationEnabled(true);//打开定位图层

        //显示交互控件
        mUiSettings=aMap.getUiSettings();
        mUiSettings.setMyLocationButtonEnabled(true);//定位按钮
        mUiSettings.setCompassEnabled(true);//指南针
        mUiSettings.setScaleControlsEnabled(true);//比例尺

        //显示车的位置
        latlng = new LatLng(22.37494458,113.56855998);//车的位置
        marker = aMap.addMarker(
            new MarkerOptions()
            .position(latlng)
            .title("MyCar")
            .icon(BitmapDescriptorFactory.fromAsset("ic_map_marker_radius.png")));//显示标记点



        //oncreate end
        
    }
    
    //以二进制读取assets文件夹下的文件
    private byte[] getAssetsData(String targetFileName){
        byte[] result=null;
        try{
            InputStream mAssets= getAssets().open(targetFileName);
            int lenght = mAssets.available();
            byte[] buffer=new byte[lenght];
            mAssets.read(buffer);
            mAssets.close();
            result=buffer;

            return result;
        }catch(IOException e){

            return result;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    } 
    
    //activity end
    
}
