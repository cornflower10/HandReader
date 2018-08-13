package com.github.obsessive.simplifyreader.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.obsessive.simplifyreader.R;
import com.github.obsessive.simplifyreader.widgets.ProgressWebView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Webctivity extends AppCompatActivity {

    @InjectView(R.id.wb)
    ProgressWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webctivity);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);

        String szImei = TelephonyMgr.getDeviceId();
        Log.i("szImei",szImei);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);

                return  true;
            }



        });

        webView.setVerticalScrollBarEnabled(true);
        webView. setHorizontalScrollBarEnabled(false);
        webView. getSettings().setJavaScriptEnabled(true);
        webView. getSettings().setSupportZoom(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSavePassword(true);
        webView. requestFocus();
        webView. getSettings().setUseWideViewPort(true);
        webView. getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("http://h5.qumi.com/android/index?appcode=7ba015f1415bca8b&appsign=7fef3266508262a2&imei="+szImei);

    }


    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else
            finish();
        super.onBackPressed();
    }
}
