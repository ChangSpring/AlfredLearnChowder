package com.alfred.chowder.ui.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.alfred.chowder.R;
import com.alfred.chowder.ui.base.BaseActivity;
import com.alfred.chowder.ui.fragment.TestRecyclerViewFragment;

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
