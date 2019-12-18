package com.zhangyc.note.proxy;

import com.zhangyc.note.proxy.invocationproxy.StuInvocationHandler;
import com.zhangyc.note.proxy.staticproxy.Monitor;
import com.zhangyc.note.proxy.staticproxy.Person;
import com.zhangyc.note.proxy.staticproxy.Student;

import java.lang.reflect.Proxy;

public class ProxyMain {

    public static void main(String...args) {
        /*
        * 静态代理 ： 编译阶段已经知道了功能提供者，代理人，调用方的关系。
        * */
        Student student = new Student();
        Monitor monitor = new Monitor(student);
        monitor.giveMoney();


        /*
        * 动态代理 ：事先不知道代理谁，根据传入的实参执行方法
        * */
        StuInvocationHandler stuInvocationHandler = new StuInvocationHandler<Person>(student);//与代理对象关联的InvocationHandler
        /*
        * 创建代理对象，代理对象的每个执行方法都会执行invoke方法
        * */
        Person person = (Person)Proxy.newProxyInstance(Person.class.getClassLoader(), new Class<?>[]{Person.class}, stuInvocationHandler);
        person.giveMoney();

    }

}
