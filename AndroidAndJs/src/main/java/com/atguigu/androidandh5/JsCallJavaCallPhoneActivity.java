package com.atguigu.androidandh5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * 作者：尚硅谷-杨光福 on 2016/7/28 11:19
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：java和js互调
 */
public class JsCallJavaCallPhoneActivity extends Activity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_call_java_video);
        webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js

        //支持缩放按钮
        webSettings.setBuiltInZoomControls(true);

        //设置客户端-不跳转到默认浏览器中
        webView.setWebViewClient(new WebViewClient());

        //添加javascript接口android
        webView.addJavascriptInterface(new MyJavaScript(),"Android");

        //设置客户端
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Toast.makeText(JsCallJavaCallPhoneActivity.this, "页面加载完成", Toast.LENGTH_SHORT).show();
            }
        });

        //加载本地html或者网络的html
        webView.loadUrl("file:///android_asset/JsCallJavaCallPhone.html");

    }


    class MyJavaScript {
        @JavascriptInterface
        public void showcontacts(){
            // 下面的代码建议在子线程中调用
            String json = "[{\"name\":\"阿福\", \"phone\":\"18601042258\"}]";
            // 调用JS中的方法
            webView.loadUrl("javascript:show('" + json + "')");
        }

        @JavascriptInterface
        public void call(String phone){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+phone));
            startActivity(intent);

        }

    }
}
