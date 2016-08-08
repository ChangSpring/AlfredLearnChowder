package com.alfred.chowder.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.alfred.chowder.R;
import com.alfred.chowder.ui.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by JiaM on 16/7/19.
 */
public class AsyncTaskActivity extends BaseActivity {

    private TextView textView;
    public static String test = "Hello !";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        ButterKnife.bind(this);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText(R.string.helloworld);
    }
}
