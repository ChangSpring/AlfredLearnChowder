package com.alfred.study.ui.activity;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import com.alfred.study.R;
import com.alfred.study.ui.base.BaseActivity;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alfred on 2016/11/24.
 */

public class FingerPrintActivity extends BaseActivity {
    @Bind(R.id.finger_print)
    TextView mTextView;

    private static final String TAG = FingerPrintActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print);

        ButterKnife.bind(this);

        mTextView.setText("IMEI : " + getImei() + "\n" + "android_id ：" + getAndroidId() + "\n" + "cpu serial ：" + getCPUSerial() + "\n" + "wifiinfo :" +
                getWifiInfo().toString() + "\n" + "ssid : " + getWifiInfo().getSSID());

        Log.i(TAG, "scan results\n");
        List<ScanResult> list = ((WifiManager) getSystemService(Context.WIFI_SERVICE)).getScanResults();
        for (ScanResult scanResult : list) {
            Log.i(TAG, scanResult.SSID);
        }

        Log.i(TAG, "already connected wifi list : \n");
        List<WifiConfiguration> configurationList = ((WifiManager) getSystemService(Context.WIFI_SERVICE)).getConfiguredNetworks();
        for (WifiConfiguration config :
                configurationList) {
            Log.i(TAG, config.SSID);
        }
    }

    @OnClick(R.id.test_bugly_finger_print_btn)
    public void buglyTest() {
        CrashReport.testJavaCrash();
    }




    /**
     * 获取cpu序列号
     *
     * @return
     */
    private String getCPUSerial() {
        String str = "", strCPU = "", cpuAddress = "0000000000000000";
        try {
            //读取CPU信息
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            //查找CPU序列号
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    //查找到序列号所在行
                    if (str.contains("Serial")) {
                        //提取序列号
                        strCPU = str.substring(str.indexOf(":") + 1,
                                str.length());
                        //去空格
                        cpuAddress = strCPU.trim();
                        break;
                    }
                } else {
                    //文件结尾
                    break;
                }
            }
        } catch (Exception ex) {
            //赋予默认值
            ex.printStackTrace();
        }
        return cpuAddress;
    }

    /**
     * 非手机设备：最开始搭载Android系统都手机设备，而现在也出现了非手机设备：如平板电脑、电子书、电视、音乐播放器等。这些设备没有通话的硬件功能，系统中也就没有TELEPHONY_SERVICE，自然也就无法通过上面的方法      获得DEVICE_ID。
     * 权限问题：获取DEVICE_ID需要READ_PHONE_STATE权限，如果只是为了获取DEVICE_ID而没有用到其他的通话功能，申请这个权限一来大才小用，二来部分用户会怀疑软件的安全性。
     * 厂商定制系统中的Bug：少数手机设备上，由于该实现有漏洞，会返回垃圾，如:zeros或者asterisks
     *
     * @return
     */
    private String getImei() {
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * 厂商定制系统的Bug：不同的设备可能会产生相同的ANDROID_ID：9774d56d682e549c。
     * 厂商定制系统的Bug：有些设备返回的值为null。
     * 设备差异：对于CDMA设备，ANDROID_ID和TelephonyManager.getDeviceId() 返回相同的值。
     *
     * @return
     */
    private String getAndroidId() {
        return Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private WifiInfo getWifiInfo() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        return wifiManager.getConnectionInfo();
    }


}
