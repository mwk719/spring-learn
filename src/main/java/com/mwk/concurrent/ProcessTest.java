package com.mwk.concurrent;

import java.util.concurrent.Semaphore;

/**
 * 信号量Semaphore 通过使用计数器counter来控制对共享资源的访问。
 * 如果计数器大于零，则允许访问。如果为零，则拒绝访问。
 * 计数器对共享资源的访问许可进行计数。因此，要访问资源，线程必须要从信号量得到许可。
 */
public class ProcessTest {
    static Semaphore empty = new Semaphore(1,true);//资源区是否为空
    static Semaphore apple = new Semaphore(0,true);//资源区苹果信号
    static Semaphore banana = new Semaphore(0,true);//资源区香蕉信号

    public static void main(String[] args) {
        //父亲的进程
        Thread father = new Thread(() -> {
            while (true) {
                try {
                    empty.acquire();//申请操作权限相当于wait(S)
                    int random = Math.random() >= 0.5 ? 1 : 0;
                    if (random == 1) {
                        System.out.println("父亲放入了一个苹果");
                        Thread.sleep(1000);//休眠表示放入的过程
                        apple.release();//唤醒儿子的访问
                    } else {
                        System.out.println("父亲放入了一个香蕉");
                        Thread.sleep(1000);
                        banana.release();//唤醒女儿的访问
                    }
                } catch (InterruptedException e) {
                    System.out.println("父亲获取资源失败！");
                    e.printStackTrace();
                }
            }
        });
        //女儿的进程
        Thread daughter = new Thread(() -> {
            while (true){
            try {
                banana.acquire();//获取banana的资源
                System.out.println("女儿取走了一个香蕉！");
                Thread.sleep(1000);//取走的过程
                empty.release();//释放父亲放的信号量
            } catch (InterruptedException e) {
                System.out.println("女儿获取资源失败！");
                e.printStackTrace();

                }
            }
        });
        Thread son = new Thread(() -> {
            while (true){
            try {
                apple.acquire();//儿子获取资源
                System.out.println("儿子取走了一个苹果！");
                Thread.sleep(1000);//取的过程
                empty.release();//释放资源
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("儿子获取资源失败！");
                }
            }
        });
        father.setName("父亲");
        daughter.setName("女儿");
        son.setName("儿子");
        father.start();
        daughter.start();
        son.start();
    }

}
