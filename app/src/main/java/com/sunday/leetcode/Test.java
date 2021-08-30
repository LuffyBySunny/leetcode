package com.sunday.leetcode;


import android.util.Log;

/**
 * Created by Sunday on 2021/8/24
 */
public class Test {



    public void outPut(String str1, String str2) throws InterruptedException {
        Object lock = new Object();
        System.out.print("开始执行方法");
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    for (int i = 0; i < str1.length(); i++) {
                        System.out.print(str1.toCharArray()[i]);
                        lock.notify();
                        try {
                            if (i != str1.length() - 1) {
                                lock.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        Runnable task2 = new Runnable() {
            @Override
            public void run() {
                System.out.print("线程2开始执行");
                synchronized (lock) {
                    for (int i = 0; i < str2.length(); i++) {
                        System.out.print(str2.toCharArray()[i]);
                        lock.notify();
                        try {
                            if (i != str2.length() - 1) {
                                lock.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        new Thread(task1).start();
        new Thread(task2).start();


    }
}
