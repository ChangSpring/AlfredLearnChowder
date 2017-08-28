package com.alfred.study.thread;

/**
 * Created by Alfred on 2017/6/8.
 */

public class ThreadTest {

    private void test(){
        MyThread myThread1 = new MyThread("A");
        MyThread myThread2 = new MyThread("B");
        myThread1.start();
        myThread2.start();

        MyRunnable myRunnable = new MyRunnable();
        new Thread(myRunnable).start();
        new Thread(myRunnable).start();
        new Thread(myRunnable).start();
    }
}
