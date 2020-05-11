package com.mwk.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;

/**
 * 在同一时刻，只有一个线程可以执行某个方法或某个代码块
 */
public class SyncThread extends Thread {

	private int age = 0;

	public SyncThread() {
		super();
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		if ("write".equals(name)) {
			write();
		} else {
			read();
		}

	}

	private void write() {
		TimeInterval timer = DateUtil.timer();
		String name = Thread.currentThread().getName();
		synchronized (this) {
			for (int i = 0; i < 5; i++) {
				System.out.println(name + "：运行：" + i + " 修改前数值【" + age + "】,修改后数值【" + (age = age + 1) + "】");
				try {
					sleep(500L);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(name + "：花费时间：" + timer.intervalMs());
	}

	private void read() {
		TimeInterval timer = DateUtil.timer();
		String name = Thread.currentThread().getName();
		synchronized (this) {
			for (int i = 0; i < 5; i++) {
				System.out.println(name + "：运行：" + i + " 数值=" + age);
				try {
					sleep(200L);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(name + "：花费时间：" + timer.intervalMs());
	}


	public static void main(String[] args) {
		SyncThread syncThread = new SyncThread();
		Thread t1 = new Thread(syncThread, "write");
		Thread t2 = new Thread(syncThread, "read");
		t2.start();
		System.out.println("是否存活："+t2.isAlive());
		t1.start();
		System.out.println("活跃线程数："+activeCount());

	}

}
