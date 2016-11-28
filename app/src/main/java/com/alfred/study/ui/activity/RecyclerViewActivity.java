package com.alfred.study.ui.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.alfred.study.R;
import com.alfred.study.ui.base.BaseActivity;
import com.alfred.study.ui.fragment.TestRecyclerViewFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alfred on 16/8/5.
 */
public class RecyclerViewActivity extends BaseActivity {

    @Bind(R.id.content_recycler_view)
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_recycler_view,new TestRecyclerViewFragment()).commitAllowingStateLoss();
    }
}
