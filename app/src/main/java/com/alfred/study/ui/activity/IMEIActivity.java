package com.alfred.study.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.alfred.study.R;
import com.alfred.study.ui.base.BaseActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**k */

public class IMEIActivity extends BaseActivity {
    @Bind(R.id.tv_valid_imei)
    TextView validTv;

    @Bind(R.id.btn_test_imei)
    Button testBtn;

    private final String TAG = IMEIActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imei);

        ButterKnife.bind(this);

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        validTv.setText("IMEI = " + telephonyManager.getDeviceId());

        Log.i(TAG, "file path : " + Environment.getExternalStorageDirectory());
//        test();

    }

//    @OnClick(R.id.btn_test_imei)
//    public void isM() {
//        validTv.setText(isOctNumber("A100004CF217DC") + "");
//    }

    @OnClick(R.id.btn_test_imei)
    public void readFile() {
        File file = new File(Environment.getExternalStorageDirectory(), "a.txt");

        try {
            int validCount = 0, invalidCount = 0;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String strLine;
            while ((strLine = bufferedReader.readLine()) != null) {
                if (strLine.length() != 15 || !isOctNumber(strLine)) {
                    invalidCount++;
                    Log.i(TAG, "The number is not valid according to the Luhn formula :" + strLine);
                } else if (isLuhnValid(strLine)) {
                    validCount++;
                } else {
                    invalidCount++;
                    Log.i(TAG, "The number is not valid according to the Luhn formula :" + strLine);
                }
            }
            bufferedReader.close();
            Log.i(TAG, "invalid count = " + invalidCount);
            Log.i(TAG, "valid count = " + validCount);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
        }
    }

    //十进制 A000004FFD0DA8
    private static boolean isOctNumber(String str) {
        boolean flag = true;
        for (int i = 0, n = str.length(); i < n; i++) {
            char cc = str.charAt(i);
            if (cc == 'A' || cc == 'B' || cc == 'C' || cc == 'D' || cc == 'E' || cc == 'F' || cc == 'a' || cc == 'b'
                    || cc == 'c' || cc == 'd' || cc == 'e' || cc == 'f') {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private void test() {
        //A0000040E1432F  490154203237518
        String code = "355324060793739";
        Log.i(TAG, "Input number: " + code);
        if (isLuhnValid(code))
            Log.i(TAG, "The number is valid according to the Luhn formula. Luhn sum:" + luhnTest(code));
        else
            Log.i(TAG, "The number is not valid according to the Luhn formula. Luhn sum:" + luhnTest(code));
        Log.i(TAG, "The check digit to append is: " + getCheckDigit(code));
        String validcode = getLuhnNumber(code);
        Log.i(TAG, "The Luhn valid number is: " + validcode);
        Log.i(TAG, "The calculated Luhn number is valid: " + isLuhnValid(validcode));
    }


    /**
     * Calculate the Luhn sum of the input number
     *
     * @param str number to check
     * @return sum calcuted with Luhn algorithm
     **/
    public int luhnTest(String str) {
        int sum = 0;
        boolean isEven = false;
        for (int i = str.length(); i > 0; i--) {
            int k = Integer.parseInt(str.substring(i - 1, i));
            if (isEven) {
                k = k * 2;
                if (k / 10 != 0)
                    k = k / 10 + k % 10;
            }

            isEven = !isEven;
            sum += k;
        }
        return sum;
    }

    /**
     * Return true if the input number is valid according to the Luhn formula
     *
     * @param str number to check
     **/
    public boolean isLuhnValid(String str) {
        if (luhnTest(str) % 10 == 0)
            return true;
        return false;
    }

    /**
     * Return the check digit that makes the input number valid according to the Luhn formula
     *
     * @param str number to check
     * @return calculated check digit
     **/
    public int getCheckDigit(String str) {
        int k = luhnTest(str + "0");
        int i = 0;
        if (k % 10 != 0)
            i = 10 - k % 10;
        return i;
    }

    /**
     * Return the input number with check digit appended
     *
     * @param str number to make Luhn valid
     * @return Luhn valid number
     **/
    public String getLuhnNumber(String str) {
        return str + getCheckDigit(str);
    }
}
