package com.alfred.chowder.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.alfred.chowder.R;
import com.alfred.chowder.ui.base.BaseActivity;
import com.alfred.chowder.util.ToastUtils;

/**
 * Created by JiaM on 16/7/19.
 */
public class AsyncTaskActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        new MyAsyncTask().execute();
    }

    private class MyAsyncTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            return "hello world !";
        }

        @Override
        protected void onPostExecute(String s) {
            ToastUtils.show(AsyncTaskActivity.this,s , Toast.LENGTH_SHORT);
        }
    }
}
