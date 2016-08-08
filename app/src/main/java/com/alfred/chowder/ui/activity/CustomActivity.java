package com.alfred.chowder.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alfred.chowder.util.ToastUtils;

public class CustomActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_custom);
		ToastUtils.show(this,AsyncTaskActivity.test,Toast.LENGTH_LONG);
	}
}
