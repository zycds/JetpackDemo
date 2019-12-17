package com.zhangyc.note.web;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.zhangyc.note.R;
import com.zhangyc.note.databinding.ActivityWebBinding;

/**
 * Author : zhang yc
 * Description: WebActivity
 *
 * (1) 系统自带内核  WebKit  优点：没有额外的Jar负担，原生api   缺点： 性能在不同手机上表现差别大   4.4以后换成chromium内核
 * (2) X5内核         优点： 提供了兼容性解决方案， qq  微信  缺点： 解决能力一般，某些方面加大了开发工作量， 不支持corodva
 * (3) 基于chrome webkit 的crosswalk WebView     没有兼容性问题，支持corodva ，缺点：18M jar包 区分不同的cpu架构  arm x86
 *
 *
 *
 * Create time : 2019/12/17  15:40
 * Modify time : 2019/12/17 15:40
 * Modify description : WebActivity
 * Modifier :  zhang yc
 */
public class WebActivity extends AppCompatActivity {

    public static final String TAG = WebActivity.class.getSimpleName();

    private ActivityWebBinding mBinding;

    private JsInterface mJsInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_web);
        webViewLoadUrl();
    }

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    private void webViewLoadUrl() {
        WebSettings settings = mBinding.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        javaScript_call_androidClient();
       /* mBinding.webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.i(TAG, "onJsAlert: " + message);
                return true;
            }
        });*/
        mBinding.webView.loadUrl("file:///android_asset/test.html");
        mJsInterface = new JsInterface();
        mBinding.webView.addJavascriptInterface(mJsInterface, "android");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.webView.stopLoading();
        mBinding.webView.getSettings().setJavaScriptEnabled(false);
        mBinding.webView.clearHistory();
        mBinding.webView.removeAllViews();
        mBinding.webView.destroy();
    }

    public void call_JavaScript(View view) {
        /*
        *  Android 调用 Js 的俩种方式
        * */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //效率高，有返回值可以获取到返回值
            mBinding.webView.evaluateJavascript("javascript:changeImage()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Log.i(TAG, "onReceiveValue: " + value);
                }
            });
        } else {
            mBinding.webView.loadUrl("javascript:changeImage()");
        }
    }

    public void javaScript_call_androidClient() {

        mBinding.webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    String scheme = request.getUrl().getScheme();
                    if (request.getUrl() == null) return false;
                    Log.i(TAG, "shouldOverrideUrlLoading: " + scheme);
                    if ("androidclient".equals(scheme)) {
                        String authority = request.getUrl().getAuthority();
                        if ("onResultSum".equalsIgnoreCase(authority)) {
                            Log.d(TAG, "shouldOverrideUrlLoading: onResultSum.");
                            mJsInterface.onResultSum(200);
                        }
                        return true;
                    }
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

    }
}
