package com.alfred.study.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.alfred.study.R;
import com.alfred.study.ui.base.BaseActivity;
import com.alfred.study.ui.fragment.LeftMenuFragment;
import com.alfred.study.util.ToastUtils;
import com.alfred.study.widget.LeftDrawerLayout;

/**
 * Created by Alfred on 2017/2/7.
 */

public class LeftDrawerLayoutActivity extends BaseActivity {

    private LeftMenuFragment mMenuFragment;
    private LeftDrawerLayout mLeftDrawerLayout;
    private TextView mContentTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_drawer_layout);

        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.id_drawerlayout);
        mContentTv = (TextView) findViewById(R.id.id_content_tv);
        mContentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(LeftDrawerLayoutActivity.this,"heoo");
                mLeftDrawerLayout.openDrawer();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        mMenuFragment = (LeftMenuFragment) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null) {
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment = new LeftMenuFragment()).commit();
        }

        mMenuFragment.setOnMenuItemSelectedListener(new LeftMenuFragment.OnMenuItemSelectedListener() {
            @Override
            public void menuItemSelected(String title) {
                mLeftDrawerLayout.closeDrawer();
                mContentTv.setText(title);
            }
        });

    }
}
