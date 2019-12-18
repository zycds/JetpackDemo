package com.zhangyc.note.proxy.invocationproxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class StuInvocationHandler<T> implements InvocationHandler {

    private static final String TAG = StuInvocationHandler.class.getSimpleName();

    private T target;

    public StuInvocationHandler(T t) {
        target = t;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d(TAG, "invoke: " + method.getName());
        return method.invoke(target, args);
    }
}
