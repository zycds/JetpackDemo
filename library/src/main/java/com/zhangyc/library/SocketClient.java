package com.zhangyc.library;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import com.blankj.utilcode.util.NetworkUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketClient {

    private static final String TAG = SocketClient.class.getSimpleName();

    private static PrintWriter printWriter;
    private static BufferedReader in;
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    private static String receiveMsg;

    private static Socket mSocket;

    public static void connect() {
        executorService.execute(new ConnectService());
    }

    public static void sendMsg(String msg) {
        executorService.execute(new SendService(msg));
    }

    private static class SendService implements Runnable {
        private String msg;

        SendService(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            if(printWriter != null) printWriter.println(this.msg);
        }
    }

    private static class ConnectService implements Runnable {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {//可以考虑在此处添加一个while循环，结合下面的catch语句，实现Socket对象获取失败后的超时重连，直到成功建立Socket连接
            try {
                mSocket = new Socket(NetworkUtils.getIpAddressByWifi(), 8090);      //步骤一
                mSocket.setSoTimeout(60000);
                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(   //步骤二
                        mSocket.getOutputStream(), StandardCharsets.UTF_8)), true);
                in = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), StandardCharsets.UTF_8));
                receiveMsg();
            } catch (Exception e) {
                Log.e(TAG, ("connectService:" + e.getMessage()));   //如果Socket对象获取失败，即连接建立失败，会走到这段逻辑
            }
        }
    }

    private static void receiveMsg() {
        try {
            while (mSocket != null && mSocket.isConnected()) {                                      //步骤三
                if ((receiveMsg = in.readLine()) != null) {
                    Log.d(TAG, "receiveMsg:" + receiveMsg);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "receiveMsg: ");
            e.printStackTrace();
        }
    }

}
