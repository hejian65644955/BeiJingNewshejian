package com.atguigu.androidandh5;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 作者：尚硅谷-杨光福 on 2016/7/28 11:19
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：java和js互调
 */
public class JavaAndJSActivity extends Activity implements View.OnClickListener {
    private EditText etNumber;
    private EditText etPassword;
    private Button btnLogin;
    /**
     * 加载网页或者说H5页面
     */
    private WebView webView;
    private WebSettings webSettings;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-07-28 11:43:37 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        setContentView(R.layout.activity_java_and_js);
        etNumber = (EditText) findViewById(R.id.et_number);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        initWebView();

    }

    private void initWebView() {

        webView = new WebView(this);

        //设置支持js调用java
        webView.addJavascriptInterface(new AndroidAndJSInterface(),"Android");

        //webview的使用
        webView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");
        //setContentView(webView);
        webSettings = webView.getSettings();
        //支持js语言
        webSettings.setJavaScriptEnabled(true);
        //添加缩放按钮-页面要支持

        webSettings.setBuiltInZoomControls(true);
        //支持双击变大变小-页面支持
        webSettings.setUseWideViewPort(true);

        //监听页面是否加载完成
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-07-28 11:43:37 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            // Handle clicks for btnLogin
            login();
        }
    }

    private void login() {
        String numebr = etNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(numebr) || TextUtils.isEmpty(password)) {
            Toast.makeText(JavaAndJSActivity.this, "账号或者密码为空", Toast.LENGTH_SHORT).show();
        } else {
            //登录
         login(numebr);
        }
    }

    private void login(String numebr) {
        webView.loadUrl("javascript:javaCallJs(" + "'" + numebr + "'" + ")");
        setContentView(webView);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
    }



    private class AndroidAndJSInterface {
        @JavascriptInterface
        public void showToast(){
            Toast.makeText(JavaAndJSActivity.this, "我被js调用了", Toast.LENGTH_SHORT).show();
        }
    }
}
