package com.zhangyc.note.proxy.staticproxy;

public class Student implements Person {
    @Override
    public void giveMoney() {
        System.out.println("student give money.");
    }
}
