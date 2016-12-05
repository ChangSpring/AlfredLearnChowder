package com.alfred.study.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alfred.study.R;
import com.alfred.study.ui.base.BaseActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <a>http://www.cnblogs.com/lich/archive/2011/12/11/2283700.html</a>
 * <p>
 * 字节流与字符流的区别
 * <p>
 * 字节流和字符流使用是非常相似的，那么除了操作代码的不同之外，还有哪些不同呢？
 * <p>
 * <p>
 * 字节流在操作的时候本身是不会用到缓冲区（内存）的，是与文件本身直接操作的，而字符流在操作的时候是使用到缓冲区的
 * <p>
 * <p>
 * 字节流在操作文件时，即使不关闭资源（close方法），文件也能输出，但是如果字符流不使用close方法的话，则不会输出任何内容，说明字符流用的是缓冲区，并且可以使用flush方法强制进行刷新缓冲区，这时才能在不close的情况下输出内容
 * <p>
 * <p>
 * <p>
 * 那开发中究竟用字节流好还是用字符流好呢？
 * <p>
 * 在所有的硬盘上保存文件或进行传输的时候都是以字节的方法进行的，包括图片也是按字节完成，而字符是只有在内存中才会形成的，所以使用字节的操作是最多的。
 * <p>
 * 如果要java程序实现一个拷贝功能，应该选用字节流进行操作（可能拷贝的是图片），并且采用边读边写的方式（节省内存）
 * <p>
 * Created by Alfred on 2016/12/5.
 */

public class IOActivity extends BaseActivity {
    @Bind(R.id.tv_content_io)
    TextView contentIOTv;

    @Bind(R.id.btn_click_io)
    Button clickBtn;

    private String filePath = Environment.getExternalStorageDirectory() + File.separator + "test.txt";

    private static final String TAG = IOActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io);

        ButterKnife.bind(this);

        clickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeByFileOutputStream();
            }
        });
    }

    /**
     * 写数据通过FileOutputStream类
     */
    @OnClick(R.id.btn_click_io)
    public void writeByFileOutputStream() {
        try {
            File file = new File(filePath);
            //--------覆盖写入------------
            OutputStream outputStream = new FileOutputStream(file);
            String content = "Hello World !";
            byte[] bytes = content.getBytes();
            //一次性写入
            outputStream.write(bytes);
            //一个字节一个字节写入
//            for (int i = 0;i < bytes.length;i++){
//                outputStream.write(bytes[i]);
//            }
            //-------------以上两种写入方式只会进行覆盖写入------------

            //追加写入请看下面
//            OutputStream outputStream1 = new FileOutputStream(file, true);
//            //\r\n 是文件中的换行
//            String str = "\r\nHello World !";
//            byte[] byte2 = str.getBytes();
//            for (int j = 0; j < byte2.length; j++) {
//                outputStream1.write(byte2[j]);
//            }
//            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }

    /**
     * 读文件通过FileInputStream类
     */
    public void readByFileInputStream() {
        try {
            File file = new File(filePath);
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            //一次性读文件
            inputStream.read(bytes);
            //一个字节一个字节读文件
//            for (int i = 0; i < bytes.length; i++) {
//                bytes[i] = (byte) inputStream.read();
//            }
            inputStream.close();
            Log.i(TAG, "content : " + new String(bytes));

        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * 写文件通过字符流
     */
    public void writerFile() {
        try {
            File file = new File(filePath);

            //覆盖写入
            Writer writer = new FileWriter(file);
            String str = "Hello World !";
            writer.write(str);

            //追加写入
            Writer writer1 = new FileWriter(file, true);
            String str1 = "\r\nHello World !";
            writer.write(str1);

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读文件通过字符流
     */
    public void readFile() {
        try {
            File file = new File(filePath);
            Reader reader = new FileReader(file);
            char[] chars = new char[1024];
            //一次性读完
            int len = reader.read(chars);

            //循环读文件
//            int temp = 0;
//            int len1 = 0;
//            while ((temp = reader.read()) != -1) {
//                chars[len1] = (char) temp;
//                len1++;
//            }

            Log.i(TAG, "content : " + new String(chars, 0, len));
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //FileWriter和FileReader的说明
    //从JDK文档中可以知道FileOutputStream是OutputStream的直接子类。FileInputStream也是InputStream的直接子类，但是在字符流文件的两个操作类却有一些特殊，FileWriter并不直接是Writer的子类，而是      OutputStreamWriter
    // 的子类，而FileReader也不直接是Reader的子类，而是InputStreamReader的子类，那么从这两个类的继承关系就可以清楚的发现，不管是使用字节流还是字符流实际上最终都是以字节的形式操作输入输出流的。也       就是说，传输或者从文件中读取数据的时候，文件里真正保存的数据永远是字节。

    /**
     * 将字节的文件输出流，以字符的形式输出
     */
    public void outputStreamWriter(){
        try {
            File file = new File(filePath);
            Writer writer = new OutputStreamWriter(new FileOutputStream(file));
            writer.write("Hello World !");
            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 用字符流形式读取字节流的对象
     */
    public void inputStreamReader(){
        try {
            File file = new File(filePath);
            Reader reader = new InputStreamReader(new FileInputStream(file));
            char[] chars = new char[1024];
            int len = reader.read(chars);
            reader.close();
            Log.i(TAG,"content : " + new String(chars,0,len));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
