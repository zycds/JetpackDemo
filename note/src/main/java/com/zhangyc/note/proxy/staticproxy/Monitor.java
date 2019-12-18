package com.zhangyc.note.proxy.staticproxy;

public class Monitor implements Person {

    private Student student;

    public Monitor(Student student) {
        this.student = student;
    }

    @Override
    public void giveMoney() {
        student.giveMoney();
    }
}
