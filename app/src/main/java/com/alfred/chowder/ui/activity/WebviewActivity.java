package com.alfred.chowder.ui.activity;

import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alfred.chowder.R;
import com.alfred.chowder.ui.base.BaseActivity;
import com.alfred.chowder.util.ToastUtils;

/**
 * Created by Alfred on 16/9/13.
 */
public class WebviewActivity extends BaseActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        mWebView.loadUrl("http://www.littleplanet.cc/littleplanetAPP/exam/1/index.html");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                ToastUtils.show(WebviewActivity.this, "error", Toast.LENGTH_SHORT);
            }
        });
    }
}
