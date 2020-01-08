package com.zhangyc.note;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.zhangyc.note.view.TextThumbSeekBar;
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

    private TextThumbSeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new MainHandler(this);
//        startActivity(new Intent(this, OfficeActivity.class));

        MyTimerTask myTimerTask = new MyTimerTask();
        Timer timer = new Timer();
        timer.schedule(myTimerTask, 1000);

        myTimerTask.cancel();
        timer.cancel();


        seekBar = findViewById(R.id.seekBar);

        seekBar.setProgressDrawable(R.drawable.progress);

        seekBar.setMax(99);
        seekBar.setProgress(99);
        seekBar.setProgressText((seekBar.getProgress() + 1) + "/" + (seekBar.getMax() + 1));
        seekBar.setTextProgressSize(32f);

        seekBar.setThumbOffset(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MainActivity.this.seekBar.setProgressText((progress + 1) + "/" + (seekBar.getMax() + 1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MainActivity.this.seekBar.setProgress(seekBar.getProgress());
                MainActivity.this.seekBar.setProgressText((seekBar.getProgress() + 1) + "/" + (seekBar.getMax() + 1));
            }
        });
    }


    static class MyTimerTask extends TimerTask {

        @Override
        public void run() {

        }

    }



}
