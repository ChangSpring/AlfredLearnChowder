package com.alfred.study.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.alfred.study.R;
import com.alfred.study.util.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.textView)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mTextView.setText(getHandleContent(this, "name", "content", R.color.colorQQHealthBlue, R.mipmap.ic_launcher));

    }

    public SpannableStringBuilder getHandleContent(Context context, String name, String caption, int nameColor, int imgRes) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("im ").append(name).append(":").append(caption);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#26B8F3"));
        builder.setSpan(foregroundColorSpan, 3, name.length() + 4, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ImageSpan imageSpan = new ImageSpan(context, imgRes);
        builder.setSpan(imageSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ToastUtils.showShort(MainActivity.this, "hello");
            }
        };
//        builder.setSpan(clickableSpan, 3, name.length() + 4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        return builder;
    }
}
