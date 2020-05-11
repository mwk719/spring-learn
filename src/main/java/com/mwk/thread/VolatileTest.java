package com.mwk.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class VolatileTest extends Thread {

	private volatile int age = 0;

	private int size = 10;

	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public VolatileTest() {
		super();
	}

	@Override
	public void run() {
		write();
	}

	private void write() {
		TimeInterval timer = DateUtil.timer();
		String name = Thread.currentThread().getName();
		//lock.writeLock().lock();
		for (int i = 0; i < size; i++) {
			int temp = age + 1;
			System.out.println(name + "：运行：" + i + " 修改前数值【" + age + "】,修改后数值【" + temp + "】，差值：【" + ((temp - age) == 1?"正确":"错的哈") + "】");
			age = temp;
			try {
				sleep(100L);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//lock.writeLock().unlock();
		System.out.println(name + "：花费时间：" + timer.intervalMs());
	}

	public static void main(String[] args) {
		VolatileTest syncThread = new VolatileTest();
		Thread t2 = new Thread(syncThread, "write2");
		for (int i = 0; i <10 ; i++) {
			Thread t1 = new Thread(syncThread, "write1");
			t1.start();
		}
		t2.start();

	}

}
