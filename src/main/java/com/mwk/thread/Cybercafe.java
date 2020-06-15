package com.mwk.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * 网吧
 * http://minwk.top/semaphore-cybercafe/
 *
 * @author MinWeikai
 * @date 2020/6/9 18:13
 */
@Slf4j
public class Cybercafe {

	/**
	 * 电脑数量
	 */
	private static int computerCount = 5;

	/**
	 * 客户数量
	 */
	private static int customerCount = 8;

	/**
	 * 电脑
	 * computerCount 表示有{@value customerCount}台电脑，即最大并发为{@value customerCount}
	 */
	private static Semaphore computer = new Semaphore(computerCount);


	public static void main(String[] args) {
		customerUseComputer();
	}

	/**
	 * 客户使用电脑，当电脑不够用时则客户阻塞等待有电脑空余，
	 * 电脑空余时则客户立即上机。当没有客户上机时则网吧下班
	 */
	private static void customerUseComputer() {

		for (int i = 0; i < customerCount; i++) {
			//客户上机
			Thread customer = new Thread(() -> {
				try {
					Thread thread = Thread.currentThread();
					String name = thread.getName();

					//客户使用，锁定电脑
					computer.acquire();
					log.info("客户[{}]使用电脑[吃鸡]----开始", name);
					Thread.sleep(new Random().nextInt(20) * 1000);
					log.info("客户[{}]使用电脑[吃鸡]----结束", name);
					//客户使用完，释放电脑
					computer.release();

					int free = computer.availablePermits();
					log.info("空余电脑[{}]", free);
					//空余电脑数=总电脑数，网吧下班
					if (free == computerCount) {
						log.info("无人使用电脑，空余电脑[{}]，网吧关门下班", free);
					}
				} catch (InterruptedException e) {
					log.error("客户使用电脑失败", e);
				}
			});
			customer.start();
		}
	}
}
