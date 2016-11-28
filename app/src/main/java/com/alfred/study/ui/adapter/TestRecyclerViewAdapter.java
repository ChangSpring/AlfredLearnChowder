package com.alfred.study.ui.adapter;

import android.content.Context;

import com.alfred.study.R;
import com.alfred.study.bean.Student;
import com.alfred.study.ui.base.BaseRecyclerViewAdapter;
import com.alfred.study.ui.base.BaseViewHolder;

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
