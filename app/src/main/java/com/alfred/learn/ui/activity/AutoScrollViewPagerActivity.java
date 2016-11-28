package com.alfred.learn.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alfred.learn.R;
import com.alfred.learn.ui.base.BaseActivity;
import com.alfred.learn.widget.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alfred on 16/8/23.
 */
public class AutoScrollViewPagerActivity extends BaseActivity{

    @Bind(R.id.view_pager)
    AutoScrollViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_scroll_view_pager);
        ButterKnife.bind(this);

        List<ImageView> imageViewList = new ArrayList<>();
        for (int i = 0; i < 3; i ++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.example_appwidget_preview);
            imageViewList.add(imageView);
        }
        AutoScrollViewPagerAdapter adapter = new AutoScrollViewPagerAdapter(this,imageViewList);

        mViewPager.setCycle(true);
        mViewPager.setInterval(3000);
        mViewPager.setSwipeScrollDurationFactor(3);
        mViewPager.setDirection(AutoScrollViewPager.RIGHT);
        mViewPager.startAutoScroll();
        mViewPager.setAdapter(adapter);

    }

    private class AutoScrollViewPagerAdapter extends PagerAdapter{

        private Context mContext;
        private List<ImageView> mImageViewList;

        public AutoScrollViewPagerAdapter(Context context, List<ImageView> list){
            this.mContext = context;
            this.mImageViewList = list;
        }

        @Override
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (View) object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }
    }
}
