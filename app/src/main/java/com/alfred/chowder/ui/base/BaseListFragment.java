package com.alfred.chowder.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alfred.chowder.R;
import com.alfred.chowder.bean.Entity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by alfred on 16/8/4.
 */
public abstract class BaseListFragment<T extends Entity> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.status_base_list_ll)
    LinearLayout statusLinearLayout;
    @Bind(R.id.status_base_list_iv)
    ImageView statusImageView;
    @Bind(R.id.status_base_list_tv)
    TextView statusTextView;

    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;
    private BaseRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_orange_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = getAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    //请求网络
                    if (adapter != null){
                        adapter.addOtherPageDatas(onLoadMore());
                    }
                }
            }
        });
    }

    @Override
    public void onRefresh() {
//        swipeRefreshLayout.setRefreshing(true);
        if (adapter != null){
            adapter.addFirstPageDatas(onStartRefresh());
        }
    }

    protected abstract List<T> onStartRefresh();

    protected abstract List<T> onLoadMore();

    protected abstract BaseRecyclerViewAdapter getAdapter();

}
