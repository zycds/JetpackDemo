package com.zhangyc.note.web;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class JsInterface {

    /*
    * js调用原生的三种方法：
    * （1）webView.addJavascriptInterface(new WebViewJsInterface(this),"android");
    *           android 4.2 之前由于未要求使用@JavascriptInterface 注解，会导致来自恶意的js攻击,有漏洞
    *  （2） WebViewClient shouldOverrideUrlLoading
    *           获取网页跳转链接，判断是否拦截，
    *           不存在方式1的漏洞，缺点js想要得到返回值，只能通过loadUrl调用js，再把返回值传回去
    *  (3), WebChromeClient  拦截 onAlert onPrompt onConfirm
    * */
    private static final String TAG = JsInterface.class.getSimpleName();

    @JavascriptInterface
    public int onResultSum(int result) {
        Log.i(TAG, "onResultSum: " + result);
        return result;
    }

    @JavascriptInterface
    public String getPhone() {
        return "haha";
    }
}
