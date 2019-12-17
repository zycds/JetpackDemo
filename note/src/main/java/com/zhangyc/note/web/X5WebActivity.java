package com.zhangyc.note.web;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zhangyc.note.R;
import com.zhangyc.note.databinding.ActivityX5webBinding;

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
public class X5WebActivity extends AppCompatActivity {

    public static final String TAG = X5WebActivity.class.getSimpleName();

    private ActivityX5webBinding mBinding;
    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_x5web);

        WebSettings settings = mBinding.webView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        mBinding.webView.loadUrl("file:///android_asset/test.html");
    }

}
