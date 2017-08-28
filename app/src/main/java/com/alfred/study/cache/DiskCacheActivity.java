package com.alfred.study.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alfred.study.R;
import com.alfred.study.util.AppUtils;
import com.alfred.study.util.SDCardUtils;
import com.alfred.study.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import libcore.io.DiskLruCache;

/**
 * http://blog.csdn.net/guolin_blog/article/details/28863651
 */
public class DiskCacheActivity extends AppCompatActivity {
    private DiskLruCache mDiskLruCache = null;
    private String mImageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disk_cache);

        open();

    }

    /**
     * 写入缓存
     */
    private void writeCache(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //将字符串MD5编码
                    String key = StringUtils.hashKeyForDisk(mImageUrl);
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        //创建输出流
                        OutputStream outputStream = editor.newOutputStream(0);
                        //实现下载并写入缓存
                        if (downloadUrlToStream(mImageUrl, outputStream)) {
                            editor.commit();
                        } else {
                            //放弃写入
                            editor.abort();
                        }
                    }
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 读取缓存
     */
    private void readCache(){
        String key = StringUtils.hashKeyForDisk(mImageUrl);
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null){
                InputStream inputStream = snapshot.getInputStream(0);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过调用open()创建DiskLruCache示例
     */
    private void open() {
        DiskLruCache diskLruCache = null;
        try {
            File cacheDir = SDCardUtils.getDiskCacheDir(this, "bitmap");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            //第三个参数:指定同一个key可以对应多少个缓存文件,基本都是传1
            diskLruCache = DiskLruCache.open(cacheDir, AppUtils.getVersionCode(), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载图片
     *
     * @param urlString
     * @param outputStream
     * @return
     */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            bufferedOutputStream = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = bufferedInputStream.read()) != -1) {
                outputStream.write(b);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
