package com.alfred.learn.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alfred.learn.R;
import com.alfred.learn.ui.base.BaseActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.alfred.learn.R.raw.b;

/**
 * 执行adb shell 执行模拟点击操作
 * Created by Alfred on 2016/10/27.
 */

public class ADBShellActivity extends BaseActivity {
    @Bind(R.id.exec_adb_shell_btn)
    Button execBtn;

    @Bind(R.id.shell_adb_shell_tv)
    TextView shellTv;

    private String filePath;

    private String batFileName = "b.bat";
    private String pythonFileName = "b.py";

    private String batFile;
    private String pythonFile;

    public static final String COMMAND_SU = "su";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_EXIT = "exit\n";
    public static final String COMMAND_LINE_END = "\n";

    /**
     * {专车,出发地输入栏,出发地,目的地输入栏,目的地,价格"我知道了"按钮,价格,计价规则详情(两个,为了加价,第二个坐标下移),详细说明}
     */
    private String[] commands = {"input tap 800 300",
            "input tap 440 1640", "input tap 640 340", "input tap 440 1800",
            "input tap 340 340", "input tap 340 1540", "input tap 540 1640",
            "input tap 540 1240", "input tap 540 1440", "input tap 540 1840",
            "input keyevent 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adb_shell);

        ButterKnife.bind(this);

        filePath = getApplicationContext().getFilesDir().getAbsolutePath();
        batFile = filePath + "/" + batFileName;
        pythonFile = filePath + "/" + pythonFileName;

        upgradeRootPermission(getPackageCodePath());

//        createFileWriteData();
//        createMultipleChannelCommond();

        execBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new MyThread(), "thread").start();
            }
        });

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> mAllApps = getPackageManager().queryIntentActivities(mainIntent, 0);

        StringBuilder stringBuilder = new StringBuilder();
        for (ResolveInfo resolveInfo : mAllApps) {
            if (resolveInfo.activityInfo.packageName.equals("com.sdu.didi.psnger")) {
                stringBuilder.append(resolveInfo.activityInfo.packageName + resolveInfo.activityInfo.name + "\n");
            }
        }
        shellTv.setText(stringBuilder.toString());


    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                CommandResult commandResult = (CommandResult) msg.obj;
                shellTv.setText(commandResult.errorMsg + commandResult.successMsg + commandResult.result);
            }
        }
    };


    class MyThread implements Runnable {
        @Override
        public void run() {
            Intent mIntent = new Intent();
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.sdu.didi.psnger", "com.didi.sdk.app.DidiLoadDexActivity");
            mIntent.setComponent(comp);
            mIntent.setAction("android.intent.action.VIEW");
            startActivity(mIntent);

            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            CommandResult commandResult = execCommand(commands, true, true);
            Message message = mHandler.obtainMessage();
            message.what = 1;
            message.obj = commandResult;
            mHandler.sendMessage(message);
        }
    }

    /**
     * execute shell commands
     *
     * @param commands        command array
     * @param isRoot          whether need to run with root
     * @param isNeedResultMsg whether need result msg
     * @return if isNeedResultMsg is false, {@link CommandResult#successMsg} is null and
     * {@link CommandResult#errorMsg} is null.
     * <p>
     * if {@link CommandResult#result} is -1, there maybe some excepiton.
     */
    public CommandResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new CommandResult(result, null, null);
        }


        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;


        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) {
                    continue;
                }
// donnot use os.writeBytes(commmand), avoid chinese charset error
                os.write(command.getBytes());
                os.writeBytes(COMMAND_LINE_END);
                os.flush();

                Thread.sleep(6000);
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();


            result = process.waitFor();
// get command result
            if (isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }
                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (process != null) {
                process.destroy();
            }
        }
        return new CommandResult(result, successMsg == null ? null : successMsg.toString(), errorMsg == null ? null : errorMsg.toString());
    }

    /**
     * result of command
     * <p>
     * <p>
     * <p>
     * {@link CommandResult#result} means result of command, 0 means normal, else means error, same to excute in
     * linux shell
     * <p>
     * {@link CommandResult#successMsg} means success message of command result
     * <p>
     * {@link CommandResult#errorMsg} means error message of command result
     *
     * @author Trinea 2013-5-16
     */
    public static class CommandResult {


        /**
         * result of command
         **/
        public int result;
        /**
         * success message of command result
         **/
        public String successMsg;
        /**
         * error message of command result
         **/
        public String errorMsg;


        public CommandResult(int result) {
            this.result = result;
        }


        public CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }


//    @OnClick(R.id.exec_adb_shell_btn)
//    public void execADBShell() {
//        try {
//            Log.i(tag,"python " + pythonFile);
//            Process process = Runtime.getRuntime().exec("python /Users/Alfred/b.py");
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(process.getInputStream()));
//            int read;
//            char[] buffer = new char[4096];
//            StringBuffer output = new StringBuffer();
//            while ((read = reader.read(buffer)) > 0) {
//                output.append(buffer, 0, read);
//            }
//            reader.close();
//            process.waitFor();
//            shellTv.setText(output.toString());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * 创建.bat批处理文件
     *
     * @param @param path
     * @return void
     * @Title: createCommondFile
     * @Description:
     */
    private void createMultipleChannelCommond() {
        File commondFile = new File(filePath, batFileName);
        BufferedWriter bw = null;
        StringBuffer sb = new StringBuffer();
        sb.append("@echo start to run the python").append("\n");
        // 进入目录命令
        sb.append("cd /d " + filePath).append("\n");
        sb.append("python ").append("b.py").append("\n");

        try {
            // 判断文件是否存在
            if (commondFile.exists()) {
                commondFile.delete();
            }
            commondFile.createNewFile();
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(commondFile), "utf-8");
            bw = new BufferedWriter(write);
            bw.write(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                bw = null;
            } catch (IOException e) {
                bw = null;
            }
        }

    }


    private String readRawFile() {
        String content = null;
        Resources resources = this.getResources();
        InputStream inputStream = null;
        try {
            inputStream = resources.openRawResource(b);
            byte buffer[] = new byte[inputStream.available()];
            inputStream.read(buffer);
            content = new String(buffer);
            Log.i(tag, "read:" + content);
        } catch (IOException e) {
            Log.e(tag, "write file", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(tag, "close file", e);
                }
            }
        }
        return content;
    }


    private void createFileWriteData() {
        String content = readRawFile();

        File file = new File(pythonFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            fileWriter = new FileWriter(pythonFile, true);
            // 创建FileWriter对象，用来写入字符流
            bufferedWriter = new BufferedWriter(fileWriter); // 将缓冲对文件的输出

            fileWriter.write(content + "\n"); // 写入文件
            bufferedWriter.newLine();
            bufferedWriter.flush(); // 刷新该流的缓冲
            bufferedWriter.close();
            fileWriter.close();

        } catch (NullPointerException e) {
            e.printStackTrace();
            try {
                fileWriter.close();
                bufferedWriter.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                fileWriter.close();
                bufferedWriter.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


    }


    /**
     * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
     *
     * @return 应用程序是/否获取Root权限
     */
    public static boolean upgradeRootPermission(String pkgCodePath) {
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd = "chmod 777 " + pkgCodePath;
            process = Runtime.getRuntime().exec("su"); //切换到root帐号
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
