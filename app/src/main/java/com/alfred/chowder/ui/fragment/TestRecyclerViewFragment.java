package com.alfred.chowder.ui.fragment;

import com.alfred.chowder.bean.Student;
import com.alfred.chowder.ui.adapter.TestRecyclerViewAdapter;
import com.alfred.chowder.ui.base.BaseListFragment;
import com.alfred.chowder.ui.base.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alfred on 16/8/5.
 */
public class TestRecyclerViewFragment extends BaseListFragment<Student> {
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
        return new TestRecyclerViewAdapter(mContext);
    }
}
