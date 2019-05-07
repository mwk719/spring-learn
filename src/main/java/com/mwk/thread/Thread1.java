package com.mwk.thread;

public class Thread1 extends Thread {

    private String name;

    public Thread1(String name) {
        super();
        this.name = name;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + "运行：" + i);
            try {
                sleep((long) (Math.random() * 1000));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread1 t1 = new Thread1("A");
        Thread1 t2 = new Thread1("B");
        t1.start();
        t2.start();
    }

}
