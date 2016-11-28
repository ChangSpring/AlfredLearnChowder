package com.alfred.study.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alfred.study.R;
import com.alfred.study.ui.adapter.BannerViewPagerAdapter;
import com.alfred.study.widget.AutoScrollViewPager;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alfred on 16/10/8.
 */
public class TestAutoScrollViewPagerFragment extends Fragment {
    private AutoScrollViewPager mViewPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_auto_scroll_view_pager,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mViewPager = (AutoScrollViewPager) view.findViewById(R.id.view_pager);

        List<View> imageViewList = new ArrayList<>();
        String[] bannerUrls = {"http://img.sc115.com/uploads/shows/130813/201308132017521074.jpg","http://img.sc115.com/uploads/shows/130813/201308132017521076.jpg","http://img.sc115.com/uploads/shows/130813/201308132017531077.jpg"};
        for (int i = 0 ; i < bannerUrls.length;i++){
            ImageView imageView = new ImageView(getActivity());
            Glide.with(this)
                    .load(bannerUrls[i])
                    .into(imageView);
//            imageView.setImageResource(R.drawable.example_appwidget_preview);
            imageViewList.add(imageView);
        }

        BannerViewPagerAdapter bannerViewPagerAdapter = new BannerViewPagerAdapter(imageViewList);
        mViewPager.setAdapter(bannerViewPagerAdapter);

        mViewPager.setCycle(true);
        mViewPager.setInterval(5000);
        mViewPager.startAutoScroll();
    }
}
