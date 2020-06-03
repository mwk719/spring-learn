package com.mwk.lock;

public class DeadLock {
	public static final String LOCK_1 = "lock1";
	public static final String LOCK_2 = "lock2";

	/*
	形成死锁的条件：
	1. 互斥条件：一个资源每次只能被一个进程使用。
    2. 请求与保持条件：一个进程因请求资源而阻塞时，对已获得的资源保持不放。
	3. 不剥夺条件：进程已获得的资源，在末使用完之前，不能强行剥夺。
	4. 循环等待条件：若干进程之间形成一种头尾相接的循环等待资源关系。
	 */

	public static void main(String[] args) {
		Thread threadA = new Thread(() -> {
			try {
				while (true) {
					synchronized (DeadLock.LOCK_1) {
						System.out.println(Thread.currentThread().getName() + " 锁住 lock1");
						Thread.sleep(1000);
						synchronized (DeadLock.LOCK_2) {
							System.out.println(Thread.currentThread().getName() + " 锁住 lock2");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		Thread threadB = new Thread(() -> {
			try {
				while (true) {
					synchronized (DeadLock.LOCK_2) {
						System.out.println(Thread.currentThread().getName() + " 锁住 lock2");
						Thread.sleep(1000);
						synchronized (DeadLock.LOCK_1) {
							System.out.println(Thread.currentThread().getName() + " 锁住 lock1");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		threadA.start();
		threadB.start();

		/*
		以上形成死锁；
		threadA，先获取DeadLock.LOCK_1，再获取DeadLock.LOCK_2
	    threadB，先获取DeadLock.LOCK_2，再获取DeadLock.LOCK_1
	    threadA获取LOCK_2需要等待threadB释放
	    threadB获取LOCK_1需要等待threadA释放
	    形成循环等待条件
		 */



		/*
		1. 执行顺序相同则可解锁；
		2. 定义DeadLock.LOCK_1与DeadLock.LOCK_2字符串相同则可解锁
		  【因为字符串有一个常量池，如果不同的线程持有的锁是具有相同字符的字符串锁时，那么两个锁实际上就是同一个锁。】

		Thread threadA = new Thread(() -> {
			try {
				while (true) {
					synchronized (DeadLock.LOCK_1) {
						System.out.println(Thread.currentThread().getName() + " 锁住 lock1");
						Thread.sleep(1000);
						synchronized (DeadLock.LOCK_2) {
							System.out.println(Thread.currentThread().getName() + " 锁住 lock2");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		Thread threadB = new Thread(() -> {
			try {
				while (true) {
					synchronized (DeadLock.LOCK_1) {
						System.out.println(Thread.currentThread().getName() + " 锁住 lock1");
						Thread.sleep(1000);
						synchronized (DeadLock.LOCK_2) {
							System.out.println(Thread.currentThread().getName() + " 锁住 lock2");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		threadA.start();
		threadB.start();

		 */
	}
}
