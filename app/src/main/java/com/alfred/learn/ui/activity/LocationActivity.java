package com.alfred.learn.ui.activity;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.alfred.learn.R;
import com.alfred.learn.ui.base.BaseActivity;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alfred on 2016/11/4.
 */

public class LocationActivity extends BaseActivity {

    @Bind(R.id.info_location_tv)
    TextView locationInfoTv;
    @Bind(R.id.start_location_tv)
    TextView startDirectionTv;
    @Bind(R.id.direction_locatioin_tv)
    TextView directionTv;

    private Location mLastLocation;
    private LocationManager mLocationManager;


    private final String TAG = LocationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);

        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            // 查找到服务信息
            //<a>http://blog.csdn.net/feiduclear_up/article/details/50704127</a>
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
            criteria.setAltitudeRequired(true);//是否要求海拔
            criteria.setBearingRequired(true);//是否要求方向
            criteria.setCostAllowed(true);//是否要求收费
            criteria.setSpeedRequired(true);//是否要求速度
            criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
            criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);//设置方向精确度
            criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);//设置速度精确度
            criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);//设置水平方向精确度
            criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);//设置垂直方向精确度
            //返回满足条件的，当前设备可用的location provider，当第二个参数为false时，返回当前设备所有provider中最符合条件的那个provider（但是不一定可用）。
            String provider = mLocationManager.getBestProvider(criteria, true); // 获取GPS信息
            Location location = mLocationManager.getLastKnownLocation("network"); // 通过GPS获取位置

            List<String> providerList = mLocationManager.getAllProviders();
            for (String provider1 : providerList){
                Log.i(TAG,"provider = " + provider1 + "\n");
            }

            // 绑定监听，有4个参数
            // 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
            // 参数2，位置信息更新周期，单位毫秒
            // 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
            // 参数4，监听
            // 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新

            // 1秒更新一次，或最小位移变化超过1米更新一次；
            // 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
//            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER
//                    , 1000
//                    , 1
//                    , mLocationListener);
            Log.i(TAG,"location = " + location);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    /**
     * Geocoder获取地理位置信息是一个后台的耗时操作，为了不阻塞主线程，强力建议在使用Geocoder获取地理位置信息时采用异步线程的方式来请求服务，这样不会造成主线程的阻塞
     *
     * @return
     */
    public void getAddress() {
        Geocoder geocoder = new Geocoder(this);
        //判断当前设备是否内置了地理位置服务。返回true表示Geocoder地理编码可以使用，否则不可使用。国内一般用不了Google services，所以一般使用百度地图或者高德地图来代替。
        boolean falg = geocoder.isPresent();
        Log.i(TAG, "geocoder isPresent is = " + falg);
        Location location = mLastLocation;
        if (location == null || !falg) {
            return ;
        }

        StringBuilder stringBuilder = new StringBuilder();
        try {

//根据经纬度获取地理位置信息
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

//根据地址获取地理位置信息
//            List<Address> addresses = geocoder.getFromLocationName( "北京市海淀区北四环西路66号中国技术交易大厦", 1);

            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    stringBuilder.append(address.getAddressLine(i)).append("\n");
                }
                stringBuilder.append(address.getCountryName()).append("_");//国家
                stringBuilder.append(address.getFeatureName()).append("_");//周边地址
                stringBuilder.append(address.getLocality()).append("_");//市
                stringBuilder.append(address.getPostalCode()).append("_");
                stringBuilder.append(address.getCountryCode()).append("_");//国家编码
                stringBuilder.append(address.getAdminArea()).append("_");//省份
                stringBuilder.append(address.getSubAdminArea()).append("_");
                stringBuilder.append(address.getThoroughfare()).append("_");//道路
                stringBuilder.append(address.getSubLocality()).append("_");//香洲区
                stringBuilder.append(address.getLatitude()).append("_");//经度
                stringBuilder.append(address.getLongitude());//维度
                stringBuilder.append(address.getPremises()).append("_");
                stringBuilder.append(address.getCountryName()).append("_");
                stringBuilder.append(address.getFeatureName()).append("_");
                stringBuilder.append(address.getLocale()).append("_");
//                stringBuilder.append(address.get()).append("_");
                System.out.println(stringBuilder.toString());
            }
        } catch (IOException e) {
            Toast.makeText(this, "报错", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        locationInfoTv.setText(stringBuilder.toString());
//        Address address = null;
//        try {
//            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//            if (addressList.size() > 0) {
//                address = addressList.get(0);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    //位置监听
    private LocationListener mLocationListener = new LocationListener() {
//        Location mLastLocation;
        boolean mValid = false;

        @Override
        public void onLocationChanged(Location location) {
            if (location.getLongitude() == 0.0 && location.getLatitude() == 0.0) {
                return;
            }
            if (!mValid) {
                Log.d("LocatioinTrackManager", "got first location");
            }
            mLastLocation.set(location);

            Log.i(TAG, "GPS定位精度为: " + location.getAccuracy());
            Log.i(TAG, "时间：" + location.getTime());
            Log.i(TAG, "经度：" + location.getLongitude());
            Log.i(TAG, "纬度：" + location.getLatitude());
            Log.i(TAG, "海拔：" + location.getAltitude());
            Log.i(TAG, "速度: " + location.getSpeed());

            mValid = true;

            getAddress();
        }

        /**
         * GPS状态变化时触发
         * @param provider
         * @param status
         * @param extras
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                // GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    Log.i(TAG, "当前GPS状态为可见状态");
                    break;
                // GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Log.i(TAG, "当前GPS状态为服务区外状态");
                    mValid = false;
                    break;
                // GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i(TAG, "当前GPS状态为暂停服务状态");
                    mValid = false;
                    break;
            }
        }

        /**
         * GPS开启时触发
         * @param provider
         */
        @Override
        public void onProviderEnabled(String provider) {

        }

        /**
         * GPS禁用时触发
         * @param provider
         */
        @Override
        public void onProviderDisabled(String provider) {
            mValid = false;
        }

        public Location current() {
            return mValid ? mLastLocation : null;
        }
    };

}
