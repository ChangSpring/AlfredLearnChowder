package com.alfred.chowder.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alfred.chowder.R;
import com.alfred.chowder.ui.base.BaseActivity;

import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alfred on 16/10/10.
 */

public class GPSDataTestActivity extends BaseActivity implements SensorEventListener {

    private int mGPSCount = 0;

    @Bind(R.id.tv_gps_data_count)
    TextView mGpsStrengthTv;
    @Bind(R.id.start_gps_data_btn)
    Button startBtn;
    @Bind(R.id.end_gps_data_btn)
    Button endBtn;

    private Sensor mSensor;
    private SensorManager mSensorManager;
    private LocationManager mLocationManager;
    private TelephonyManager mTelephonyManager;

    private static final String TAG = GPSDataTestActivity.class.getSimpleName();

    GpsStatus.Listener gpsS = new GpsStatus.Listener() {
        @Override
        public void onGpsStatusChanged(int event) {
            mGPSCount = 0;
            if (event == GpsStatus.GPS_EVENT_FIRST_FIX) {
                //第一次定位
            } else if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
                //卫星状态改变
                try {
                    GpsStatus gpsStatus = mLocationManager.getGpsStatus(null); // 取当前状态

                    int maxSatellites = gpsStatus.getMaxSatellites(); //获取卫星颗数的默认最大值

                    Iterator<GpsSatellite> it = gpsStatus.getSatellites().iterator();//创建一个迭代器保存所有卫星
                    while (it.hasNext() && mGPSCount <= maxSatellites) {
                        GpsSatellite s = it.next();
                        //可见卫星数量
                        if (s.usedInFix()) {
                            //已定位卫星数量
                            mGPSCount++;
                        }
                    }
//                gpsCount.Gpscount(mGPSCount);
                    Log.i(TAG, "GPS信号强度(根据返回的卫星数量来判断当前Gps信号强度的) = " + mGPSCount);
                    mGpsStrengthTv.setText("GPS信号强度(根据返回的卫星数量来判断当前Gps信号强度的) = " + mGPSCount);
//                    Toast.makeText(GPSDataTestActivity.this, "GPS信号强度(根据返回的卫星数量来判断当前Gps信号强度的) = " + mGPSCount, Toast.LENGTH_SHORT).show();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }

            } else if (event == GpsStatus.GPS_EVENT_STARTED) {
                //定位启动
                Log.i(TAG, "GPS定位启动");
            } else if (event == GpsStatus.GPS_EVENT_STOPPED) {
                //定位结束
                Log.i(TAG, "GPS定位结束");
            }
//            Log.i(TAG, "GPS信号强度(根据返回的卫星数量来判断当前Gps信号强度的) = " + mGPSCount);
        }
    };

    //位置监听
    LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "GPS定位精度为: " + location.getAccuracy());
            Log.i(TAG, "时间：" + location.getTime());
            Log.i(TAG, "经度：" + location.getLongitude());
            Log.i(TAG, "纬度：" + location.getLatitude());
            Log.i(TAG, "海拔：" + location.getAltitude());
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
                    break;
                // GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i(TAG, "当前GPS状态为暂停服务状态");
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

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_data);

        ButterKnife.bind(this);


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //TYPE_GYROSCOPE 为陀螺仪  TYPE_ACCELEROMETER 为加速器
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        openGPSSettings();
        getBaseStationInfo();
        getLocationInfo();
        try {
            mLocationManager.addGpsStatusListener(gpsS);

            // 绑定监听，有4个参数
            // 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
            // 参数2，位置信息更新周期，单位毫秒
            // 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
            // 参数4，监听
            // 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新

            // 1秒更新一次，或最小位移变化超过1米更新一次；
            // 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        PackageManager amgr = getPackageManager();
        List<ApplicationInfo> infos = amgr.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (ApplicationInfo info : infos) {
            if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                sb.append(amgr.getApplicationLabel(info));
                sb.append("\\u007c");
            }

        }
        Log.e(TAG,sb.toString().substring(0,sb.toString().length()));

    }

    @OnClick(R.id.start_gps_data_btn)
    public void start() {
        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER
                    , 1000
                    , 1
                    , mLocationListener);
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.end_gps_data_btn)
    public void end() {
        mLocationManager.removeGpsStatusListener(gpsS);
        mSensorManager.unregisterListener(this);
    }

    /**
     * 通过手机信号获取基站信息
     */
    private void getBaseStationInfo() {

        // 返回值MCC + MNC
        String operator = mTelephonyManager.getNetworkOperator();
        int mcc = Integer.parseInt(operator.substring(0, 3));
        int mnc = Integer.parseInt(operator.substring(3));

        // 中国移动和中国联通获取LAC、CID的方式
        GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
        int lac = location.getLac();
        int cellId = location.getCid();

        Log.i(TAG, " MCC = " + mcc + "\t MNC = " + mnc + "\t LAC = " + lac + "\t CID = " + cellId);

        // 中国电信获取LAC、CID的方式
                /*CdmaCellLocation location1 = (CdmaCellLocation) mTelephonyManager.getCellLocation();
                lac = location1.getNetworkId();
                cellId = location1.getBaseStationId();
                cellId /= 16;*/

        // 获取邻区基站信息
//        List<NeighboringCellInfo> infos = mTelephonyManager.getNeighboringCellInfo();
//        StringBuffer sb = new StringBuffer("总数 : " + infos.size() + "\n");
//        for (NeighboringCellInfo info1 : infos) { // 根据邻区总数进行循环
//            sb.append(" LAC : " + info1.getLac()); // 取出当前邻区的LAC
//            sb.append(" CID : " + info1.getCid()); // 取出当前邻区的CID
//            sb.append(" BSSS : " + (-113 + 2 * info1.getRssi()) + "\n"); // 获取邻区基站信号强度
//        }
//
//        Log.i(TAG, " 获取邻区基站信息:" + sb.toString());
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d("linc", "value size: " + sensorEvent.values.length);
        float xValue = sensorEvent.values[0];// Acceleration minus Gx on the x-axis
        float yValue = sensorEvent.values[1];//Acceleration minus Gy on the y-axis
        float zValue = sensorEvent.values[2];//Acceleration minus Gz on the z-axis
        Log.i(TAG, "x轴： " + xValue + "  y轴： " + yValue + "  z轴： " + zValue);
//        if (xValue > mGravity) {
//            mTvInfo.append("\n重力指向设备左边");
//        } else if (xValue < -mGravity) {
//            mTvInfo.append("\n重力指向设备右边");
//        } else if (yValue > mGravity) {
//            mTvInfo.append("\n重力指向设备下边");
//        } else if (yValue < -mGravity) {
//            mTvInfo.append("\n重力指向设备上边");
//        } else if (zValue > mGravity) {
//            mTvInfo.append("\n屏幕朝上");
//        } else if (zValue < -mGravity) {
//            mTvInfo.append("\n屏幕朝下");
//        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void openGPSSettings() {
        // 获取位置管理服务
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (mLocationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT).show();
            return;
        }
//        status = false;
        Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        startActivityForResult(intent, 0); //此为设置完成后返回到获取界面
    }

    private void getLocationInfo() {
        try {
            if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                // 为获取地理位置信息时设置查询条件
                String bestProvider = mLocationManager.getBestProvider(getCriteria(), true);
                // 获取位置信息
                // 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
                Location location = mLocationManager.getLastKnownLocation(bestProvider);
                Log.i(TAG, "gps定位精度 = " + location.getAccuracy());
            } else {
                Toast.makeText(this, "请开启GPS导航", Toast.LENGTH_SHORT).show();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        // 设置是否需要方位信息
        criteria.setBearingRequired(false);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }
}

