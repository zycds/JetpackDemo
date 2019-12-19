package com.zhangyc.note;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.zhangyc.note.web.OfficeActivity;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    static class MainHandler extends Handler {

        private WeakReference<MainActivity> mainActivityWeakReference;

        public MainHandler(MainActivity mainActivity) {
            mainActivityWeakReference = new WeakReference<>(mainActivity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (mainActivityWeakReference != null) mainActivityWeakReference.get().handlerMsg(msg);
        }
    }

    public void handlerMsg(Message message) {}


    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new MainHandler(this);
        startActivity(new Intent(this, OfficeActivity.class));

        MyTimerTask myTimerTask = new MyTimerTask();
        Timer timer = new Timer();
        timer.schedule(myTimerTask, 1000);

        myTimerTask.cancel();
        timer.cancel();


        Looper.myLooper().quit();

    }


    static class MyTimerTask extends TimerTask {

        @Override
        public void run() {

        }

    }



}
