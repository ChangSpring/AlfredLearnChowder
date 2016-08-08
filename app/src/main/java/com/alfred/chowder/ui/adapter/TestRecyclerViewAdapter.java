package com.alfred.chowder.ui.adapter;

import android.content.Context;

import com.alfred.chowder.R;
import com.alfred.chowder.bean.Student;
import com.alfred.chowder.ui.base.BaseRecyclerViewAdapter;
import com.alfred.chowder.ui.base.BaseViewHolder;

/**
 * Created by alfred on 16/8/5.
 */
public class TestRecyclerViewAdapter extends BaseRecyclerViewAdapter<Student> {

    public TestRecyclerViewAdapter(Context context){
        super(context);
    }

    @Override
    protected int getNormalLayoutId() {
        return R.layout.item_fragment_test_list;
    }

    @Override
    protected void bindData(BaseViewHolder viewHolder, Student student, int position) {
        viewHolder.setText(R.id.content_item_test_list_tv,student.name);
    }
}
