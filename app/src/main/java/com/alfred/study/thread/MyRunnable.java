package com.alfred.study.thread;

/**
 * Created by Alfred on 2017/6/8.
 */

public class MyRunnable implements Runnable{
    private int ticket = 10;

    public synchronized void run() {
        for (int i = 0; i < 10; i++){
            if (this.ticket > 0){
                System.out.println("卖票 : ticket " + this.ticket--);
            }
        }
    }
}
