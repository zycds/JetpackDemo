package com.zhangyc.note.web;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.tencent.smtt.sdk.TbsReaderView;
import com.zhangyc.note.R;
import com.zhangyc.note.databinding.ActivityOfficeBinding;

import java.io.File;

public class OfficeActivity extends AppCompatActivity {

    private ActivityOfficeBinding mDataBinding;
    TbsReaderView mTbsReaderView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_office);

        mTbsReaderView = new TbsReaderView(this, new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {

            }
        });
        mDataBinding.tbsLayout.addView(mTbsReaderView, new RelativeLayout.LayoutParams(-1, -1));

//        displayFile("/sdcard/a.docx", "a.docx");
//        displayFile("/sdcard/b.xlsx", "b.xlsx");
        displayFile("/sdcard/c.pdf", "c.pdf");
    }


    private String tbsReaderTemp = Environment.getExternalStorageDirectory() + "/TbsReaderTemp";
    private void displayFile(String filePath, String fileName) {

        //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
        String bsReaderTemp = tbsReaderTemp;
        File bsReaderTempFile =new File(bsReaderTemp);
        if (!bsReaderTempFile.exists()) {
            Log.d("print","准备创建/TbsReaderTemp！！");
            boolean mkdir = bsReaderTempFile.mkdir();
            if(!mkdir){
                Log.d("print","创建/TbsReaderTemp失败！！！！！");
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("filePath", filePath);
        bundle.putString("tempPath", tbsReaderTemp);
        boolean result = mTbsReaderView.preOpen(getFileType(fileName), false);
        Log.d("print","查看文档---"+result);
        if (result) {
            mTbsReaderView.openFile(bundle);
        }else{

        }
    }

    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            Log.d("print", "paramString---->null");
            return str;
        }
        Log.d("print", "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d("print", "i <= -1");
            return str;
        }

        str = paramString.substring(i + 1);
        int print = Log.d("print", "paramString.substring(i + 1)------>" + str);
        return str;
    }
}
