package com.alfred.study.ui.fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alfred.study.R;
import com.alfred.study.bean.Student;
import com.alfred.study.ui.adapter.BannerViewPagerAdapter;
import com.alfred.study.ui.adapter.TestRecyclerViewAdapter;
import com.alfred.study.ui.base.BaseListFragment;
import com.alfred.study.ui.base.BaseRecyclerViewAdapter;
import com.alfred.study.widget.AutoScrollViewPager;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alfred on 16/8/5.
 */
public class TestRecyclerViewFragment extends BaseListFragment<Student> {

    private BaseRecyclerViewAdapter adapter;

    private static final String TAG = TestRecyclerViewFragment.class.getName();

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

        View view2 = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_header, null);
        adapter.addHeaderView(view2, R.layout.item_recyclerview_header);

        View view3 = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_banner, null);
        AutoScrollViewPager viewPager = (AutoScrollViewPager) view3.findViewById(R.id.vp_banner);

        List<View> imageViewList = new ArrayList<>();
        String[] bannerUrls = {"http://img.sc115.com/uploads/shows/130813/201308132017521074.jpg", "http://img.sc115" +
                ".com/uploads/shows/130813/201308132017521076.jpg", "http://img.sc115.com/uploads/shows/130813/201308132017531077.jpg"};
        for (String url : bannerUrls) {
            ImageView imageView = new ImageView(mContext);
            Glide.with(this)
                    .load(url)
                    .into(imageView);
//            imageView.setImageResource(R.drawable.example_appwidget_preview);
            imageViewList.add(imageView);

        }

        BannerViewPagerAdapter bannerViewPagerAdapter = new BannerViewPagerAdapter(imageViewList);
        viewPager.setAdapter(bannerViewPagerAdapter);

        viewPager.setCycle(true);
        viewPager.startAutoScroll();

        adapter.addHeaderView(view3, R.layout.item_recyclerview_banner);

        getContactsInfo();

    }

    public void getContactsInfo() {
        Uri uri = Uri.parse("content://com.android.contacts/contacts");
        //获得一个ContentResolver数据共享的对象
        ContentResolver resolver = mContext.getContentResolver();
        //取得联系人中开始的游标
        Cursor cursor = resolver.query(uri, null, null, null, null);

        while (cursor.moveToNext()) {
            //获得联系人ID
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //获得联系人姓名
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            //获得联系人手机号码
            Cursor phone = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" +
                    id, null, null);
            StringBuilder stringBuilder = new StringBuilder("contact_id = ").append(id).append(" name = ").append(name);
            while (phone.moveToNext()) {
                //取得电话号码(可能多个号码)
                int phoneFieldConlumnIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String phoneNumber = phone.getString(phoneFieldConlumnIndex);
                stringBuilder.append(phoneNumber + "\\");
            }
            Log.i(TAG, stringBuilder.toString());
        }
    }
}
