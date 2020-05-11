package com.mwk.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest extends Thread {

	private int age = 0;

	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public ReadWriteLockTest() {
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
		lock.writeLock().lock();
		for (int i = 0; i < 5; i++) {
			System.out.println(name + "：运行：" + i + " 修改前数值【" + age + "】,修改后数值【" + (age = age + 1) + "】");
			try {
				sleep(500L);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		lock.writeLock().unlock();
		System.out.println(name+"：花费时间："+timer.intervalMs());
	}

	private void read() {
		TimeInterval timer = DateUtil.timer();
		String name = Thread.currentThread().getName();
		lock.readLock().lock();
		for (int i = 0; i < 5; i++) {
			System.out.println(name + "：运行：" + i + " 数值=" + age);
			try {
				sleep(200L);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		lock.readLock().unlock();
		System.out.println(name+"：花费时间："+timer.intervalMs());
	}

	public static void main(String[] args) {
		ReadWriteLockTest syncThread = new ReadWriteLockTest();
		Thread t1 = new Thread(syncThread, "write");
		Thread t2 = new Thread(syncThread, "read");
		t1.start();
		t2.start();

	}

}
