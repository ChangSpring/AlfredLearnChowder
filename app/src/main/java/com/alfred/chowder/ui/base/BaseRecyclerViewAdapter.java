package com.alfred.chowder.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alfred.chowder.R;
import com.alfred.chowder.bean.Entity;
import com.alfred.chowder.interf.OnItemClickListener;
import com.alfred.chowder.interf.OnItemLongClickListener;

import java.util.List;

/**
 * Created by alfred on 16/8/4.
 */
public abstract class BaseRecyclerViewAdapter<T extends Entity> extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<T> list;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_FOOTER = 2;

    public BaseRecyclerViewAdapter(Context context){
        this.context = context;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;
        if (viewType == TYPE_ITEM){
            viewHolder = BaseViewHolder.get(context,null,parent,getNormalLayoutId(),-1);
            setListener(parent,viewHolder,viewType);
        }else if (viewType == TYPE_HEADER){
        }else if(viewType == TYPE_FOOTER){
            viewHolder = BaseViewHolder.get(context,null,parent, R.layout.item_recyclerview_loading,-1);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int position) {
        baseViewHolder.updatePosition(position);
        bindData(baseViewHolder,list.get(position),position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position + 1 == getItemCount() ? TYPE_FOOTER : TYPE_ITEM;
    }

    protected boolean isEnableClicked(int viewType){
        return viewType == TYPE_ITEM;
    }


    public void addItemData(T t) {
        if (t == null) {
            return;
        }
        list.add(t);
        notifyDataSetChanged();
    }

    public void addItemDataForPosition(T t, int position) {
        if (t == null) {
            return;
        }
        list.add(position, t);
        notifyItemChanged(position);
//        notifyDataSetChanged();
    }

    public void removeItemDataForPosition(int position) {
        list.remove(position);
        notifyItemRemoved(position);
//        notifyDataSetChanged();
    }

    public void removeItemDataForObject(T t) {
        list.remove(t);
        notifyDataSetChanged();
    }

    public void addFirstPageDatas(List<T> list) {
        if (list == null) {
            return;
        }
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }

    public void addOtherPageDatas(List<T> list) {
        if (list == null) {
            return;
        }
        list.addAll(list);
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return list;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder){
        return viewHolder.getPosition();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }

    protected void setListener(final ViewGroup parent, final BaseViewHolder baseViewHolder,int viewType){
        if (!isEnableClicked(viewType))
            return;

        baseViewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null){
                    int position = getPosition(baseViewHolder);
                    onItemClickListener.onItemClck(parent,view,list.get(position),position);
                }
            }
        });

        baseViewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemLongClickListener != null){
                    int position = getPosition(baseViewHolder);
                    return onItemLongClickListener.onItemLongClick(parent,view,list.get(position),position);
                }
                return false;
            }
        });
    }


    protected abstract int getNormalLayoutId();
    protected abstract void bindData(BaseViewHolder viewHolder,T t,int position);


}
