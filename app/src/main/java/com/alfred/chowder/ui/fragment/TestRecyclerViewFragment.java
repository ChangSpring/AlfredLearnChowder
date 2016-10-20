package com.alfred.chowder.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alfred.chowder.R;
import com.alfred.chowder.bean.Student;
import com.alfred.chowder.ui.adapter.BannerViewPagerAdapter;
import com.alfred.chowder.ui.adapter.TestRecyclerViewAdapter;
import com.alfred.chowder.ui.base.BaseListFragment;
import com.alfred.chowder.ui.base.BaseRecyclerViewAdapter;
import com.alfred.chowder.widget.AutoScrollViewPager;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alfred on 16/8/5.
 */
public class TestRecyclerViewFragment extends BaseListFragment<Student> {

    private BaseRecyclerViewAdapter adapter;

    @Override
    protected List<Student> onStartRefresh() {
        List<Student> list = new ArrayList<>();
        list.add(new Student("hello"));
        list.add(new Student("world"));
        list.add(new Student("people"));
        list.add(new Student("hahah"));
        return list;
    }

    @Override
    protected List<Student> onLoadMore() {
        List<Student> list = new ArrayList<>();
        list.add(new Student("hello"));
        list.add(new Student("world"));
        list.add(new Student("people"));
        list.add(new Student("hahah"));
        return list;

    }

    @Override
    protected BaseRecyclerViewAdapter getAdapter() {
        adapter = new TestRecyclerViewAdapter(mContext);
        return adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_header, null);
//        adapter.addHeaderView(view, R.layout.item_recyclerview_header);
        View view2 = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_header, null);
        adapter.addHeaderView(view2, R.layout.item_recyclerview_header);

        View view3 = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_banner, null);
        AutoScrollViewPager viewPager = (AutoScrollViewPager) view3.findViewById(R.id.vp_banner);

        List<View> imageViewList = new ArrayList<>();
        String[] bannerUrls = {"http://img.sc115.com/uploads/shows/130813/201308132017521074.jpg","http://img.sc115.com/uploads/shows/130813/201308132017521076.jpg","http://img.sc115.com/uploads/shows/130813/201308132017531077.jpg"};
        for (int i = 0 ; i < bannerUrls.length;i++){
            ImageView imageView = new ImageView(mContext);
            Glide.with(this)
                    .load(bannerUrls[i])
                    .into(imageView);
//            imageView.setImageResource(R.drawable.example_appwidget_preview);
            imageViewList.add(imageView);
        }

        BannerViewPagerAdapter bannerViewPagerAdapter = new BannerViewPagerAdapter(imageViewList);
        viewPager.setAdapter(bannerViewPagerAdapter);

        viewPager.setCycle(true);
        viewPager.startAutoScroll();

        adapter.addHeaderView(view3,R.layout.item_recyclerview_banner);



    }
}
