package com.alfred.study.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alfred.study.R;
import com.alfred.study.bean.Entity;
import com.alfred.study.interf.OnItemClickListener;
import com.alfred.study.interf.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alfred on 16/8/4.
 */
public abstract class BaseRecyclerViewAdapter<T extends Entity> extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<T> mList = new ArrayList<>();

    private List<View> mHeaderViewList = new ArrayList<>();
    private List<View> mFooterViewList = new ArrayList<>();
    private List<Integer> mHeaderLayoutIdList = new ArrayList<>();
    private List<Integer> mFooterLayoutIdList = new ArrayList<>();

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_FOOTER = 2;

    public BaseRecyclerViewAdapter(Context context) {
        this.context = context;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;

        if (viewType == TYPE_ITEM) {
            viewHolder = BaseViewHolder.get(context, null, parent, getNormalLayoutId(), -1);
        } else if (viewType == TYPE_HEADER) {
            viewHolder = BaseViewHolder.get(context, null, parent, mHeaderLayoutIdList.get(0), -1);
        } else if (viewType == TYPE_FOOTER) {
            viewHolder = BaseViewHolder.get(context, null, parent, R.layout.item_recyclerview_loading, -1);
        }

        setListener(parent, viewHolder, viewType);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int position) {
        baseViewHolder.updatePosition(position);
        if (isHeader(position)){
            bindHeaderData(baseViewHolder,mHeaderViewList.get(position),position);
        }else if (isFooter(position)){
            bindFooterData(baseViewHolder,mFooterViewList.get(position - mHeaderViewList.size() - mList.size()),position);
        }else{
            bindData(baseViewHolder, mList.get(position - mHeaderViewList.size()), position);
        }
    }

    @Override
    public int getItemCount() {
        int headerAndFooterCount = mHeaderViewList.size() + mFooterViewList.size();
        return mList == null ? headerAndFooterCount : mList.size() + headerAndFooterCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)){
            return TYPE_HEADER;
        }else if (isFooter(position)){
            return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }
    }

    public int getHeaderViewsCount(){
        return mHeaderViewList.size();
    }

    public int getFooterViewsCount(){
        return mFooterViewList.size();
    }

    public boolean isHeader(int position){
        return position >= 0 && position < mHeaderViewList.size();
    }

    public boolean isFooter(int position){
        return position >= mList.size() + mHeaderViewList.size();
    }

    /**
     * 重写此方法设置normal item , footer item ,header item 是否可以点击
     *
     * @param viewType
     * @return 默认都可以点击
     */
    protected boolean isEnableClicked(int viewType) {
        return true;
    }


    public void addItemData(T t) {
        if (t == null) {
            return;
        }
        mList.add(t);
        notifyDataSetChanged();
    }

    public void addHeaderView(View view, int layoutId) {
        if (view == null) {
            throw new RuntimeException("headerView is null");
        }
        if (layoutId == -1) {
            throw new RuntimeException("layoutId is not allowed !");
        }

        mHeaderViewList.add(view);
        mHeaderLayoutIdList.add(layoutId);
        notifyDataSetChanged();
    }

    public void addHeaderView(View view, int layoutId, int position) {
        if (view == null) {
            throw new RuntimeException("headerView is null");
        }

        if (layoutId == -1) {
            throw new RuntimeException("layoutId is not allowed !");
        }

        if (position < 0 || position > mHeaderViewList.size()) {
            throw new RuntimeException("headerView position is not allowed ! ");
        }

        mHeaderViewList.add(position, view);
        mHeaderLayoutIdList.add(position, layoutId);
        notifyDataSetChanged();
    }

    public void addFooterView(View view, int layoutId) {
        if (view == null) {
            throw new RuntimeException("footerView is null");
        }

        if (layoutId == -1) {
            throw new RuntimeException("layoutId is not allowed !");
        }

        mFooterViewList.add(view);
        mHeaderLayoutIdList.add(layoutId);
        notifyDataSetChanged();
    }

    public void addFooterView(View view, int layoutId, int position) {
        if (view == null) {
            throw new RuntimeException("footerView is null");
        }

        if (layoutId == -1) {
            throw new RuntimeException("layoutId is not allowed !");
        }

        if (position < 0 || position > mFooterViewList.size()) {
            throw new RuntimeException("footerView position is not allowed ! ");
        }

        mFooterViewList.add(position, view);
        mHeaderLayoutIdList.add(position, layoutId);
    }

    public void addItemDataForPosition(T t, int position) {
        if (t == null) {
            return;
        }
        mList.add(position, t);
        notifyItemChanged(position);
//        notifyDataSetChanged();
    }

    public void removeItemDataForPosition(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
//        notifyDataSetChanged();
    }

    public void removeItemDataForObject(T t) {
        mList.remove(t);
        notifyDataSetChanged();
    }

    public void addFirstPageDatas(List<T> list) {
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addOtherPageDatas(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mList;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getPosition();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    protected void setListener(final ViewGroup parent, final BaseViewHolder baseViewHolder, int viewType) {
        if (!isEnableClicked(viewType))
            return;

        baseViewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    int position = getPosition(baseViewHolder);
                    onItemClickListener.onItemClck(parent, view, mList.get(position), position);
                }
            }
        });

        baseViewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemLongClickListener != null) {
                    int position = getPosition(baseViewHolder);
                    return onItemLongClickListener.onItemLongClick(parent, view, mList.get(position), position);
                }
                return false;
            }
        });
    }

    protected void bindHeaderData(BaseViewHolder viewHolder,View view ,int position){

    }

    protected void bindFooterData(BaseViewHolder viewHolder,View view ,int position){

    }

    protected abstract int getNormalLayoutId();

    protected abstract void bindData(BaseViewHolder viewHolder, T t, int position);


}
