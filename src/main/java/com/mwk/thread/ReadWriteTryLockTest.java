package com.mwk.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author MinWeikai
 * @date 2020/5/21 18:11
 */
@Slf4j
public class ReadWriteTryLockTest extends Thread {

	private ReentrantLock reentrantLock = new ReentrantLock();

	@Override
	public void run() {
		Thread thread = Thread.currentThread();
		try {
			if (reentrantLock.tryLock(1, TimeUnit.SECONDS)) {
				log.info("开始-线程名：{}，是否存活：{}", thread.getName(), thread.isAlive());
				for (int i = 0; i < 5; i++) {
					log.info("第{}次执行", i);
					try {
						Thread.sleep(500L);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (reentrantLock.isHeldByCurrentThread()) {
				reentrantLock.unlock();
			}
		}
		log.info("结束-线程名：{}，是否存活：{}", thread.getName(), thread.isAlive());
	}
}
