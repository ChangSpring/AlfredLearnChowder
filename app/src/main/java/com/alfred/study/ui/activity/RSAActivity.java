package com.alfred.study.ui.activity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alfred.study.R;
import com.alfred.study.ui.base.BaseActivity;
import com.alfred.study.util.Base64Util;
import com.alfred.study.util.RSAUtil;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alfred on 2016/12/8.
 */

public class RSAActivity extends BaseActivity {

    @Bind(R.id.et_content_rsa)
    EditText contentEt;
    @Bind(R.id.btn_encrypt_rsa)
    Button encryptBtn;
    @Bind(R.id.btn_decrypt_rsa)
    Button decryptBtn;
    @Bind(R.id.tv_content_decrypt_rsa)
    TextView decryptTv;
    @Bind(R.id.tv_content_encrypt_rsa)
    TextView encryptTv;

    private static final String TAG = RSAActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa);

        ButterKnife.bind(this);
    }

    /**
     * 加密
     */
    @OnClick(R.id.btn_encrypt_rsa)
    public void encrypt() {
        String content = contentEt.getText().toString().trim();
        try {
            //从字符串中得到公钥
//            PublicKey publicKey = RSAUtil.loadPublicKey("");
            //从文件中得到公钥
            InputStream inputStream = getResources().getAssets().open("rsa_public_key.pem");
            PublicKey publicKey = RSAUtil.loadPublicKey(inputStream);
            //加密
            byte[] encryptByte = RSAUtil.encryptData(content.getBytes(), publicKey);
            Log.i(TAG, "jiami = " + new String(encryptByte));

            String afterEncrypt = Base64.encodeToString(encryptByte, Base64.DEFAULT);

            // 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
//            String afterEncrypt = Base64Util.encode(encryptByte);

            encryptTv.setText(afterEncrypt);
            Log.i(TAG, "encrypt data = " + afterEncrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解密
     */
    @OnClick(R.id.btn_decrypt_rsa)
    public void decrypt() {
//        String encryptContent = encryptTv.getText().toString().trim();
        String encryptContent = "TZ/VbDdShUfdCIdNHvQBmG2vfaeHylY/ASEcYv8HZ0RuRmWf0BWcPWYbpfhr4GgZblph0gyGHiLxzZCcMWi24ZcfBLxcvP6da5XvVWLb/79mlGn9vGsOay/YsiB4zAOCU72Rny4LxHg3LeEenqXcWNUkQWdxCz1I+IOrFzgtQCg=";
        try {
            //从字符串中得到私钥
//        PrivateKey privateKey = RSAUtil.loadPrivateKey("");
            //从文件中得到私钥
            InputStream inputStream = getResources().getAssets().open("pkcs8_rsa_private_key.pem");
            PrivateKey privateKey = RSAUtil.loadPrivateKey(inputStream);

            // 因为RSA加密后的内容经Base64再加密转换了一下，所以先Base64解密回来再给RSA解密
            byte[] decryptByte = RSAUtil.decryptData(Base64Util.decode(encryptContent),privateKey);

//            byte[] decryptByte = RSAUtil.decryptData(Base64.decode(encryptContent, Base64.DEFAULT), privateKey);

            String decryptStr = new String(decryptByte);
            decryptTv.setText(decryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
