package com.mwk.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * Synchronized与摘猕猴桃的关系
 * https://minwk.top/synchronized-kiwifruit/
 *
 * @author MinWeikai
 * @date 2020/9/2 10:15
 */
@Slf4j
public class SynchronizedKiwifruitThread extends Thread {

	/**
	 * 猕猴桃树
	 */
	private Integer KIWI_TREES = 400;

	/**
	 * 农民数量
	 */
	private Integer FARMER = 2;

	/**
	 * 农民-1 每小时摘10棵
	 */
	private Integer FARMER_0_EFFICIENCY = 10;

	/**
	 * 农民-2 每小时摘8棵
	 */
	private Integer FARMER_1_EFFICIENCY = 8;

	/**
	 * 每人工作时间
	 */
	private Integer WORK_TIME = 1;

	/**
	 * 总共工作时间
	 */
	private Integer TOTAL_WORK_TIME = 0;

	/**
	 * 农民工作效率不同
	 *
	 * @param name
	 */
	private void farmerWork(String name) {
		switch (name) {
			case "farmer0":
				this.work(name, this.FARMER_0_EFFICIENCY);
				break;
			case "farmer1":
				this.work(name, this.FARMER_1_EFFICIENCY);
				break;
			default:
				this.work(name, this.FARMER_0_EFFICIENCY);
				break;
		}
	}

	/**
	 * 进行工作
	 *
	 * @param name
	 * @param efficiency
	 */
	private void work(String name, Integer efficiency) {
		log.info("{} 开始工作，工作效率每小时摘 {} 棵", name, efficiency);
		//收获数
		Integer harvestNum = this.WORK_TIME * efficiency;
		//剩余数
		this.KIWI_TREES = this.KIWI_TREES - harvestNum;
		this.TOTAL_WORK_TIME = this.TOTAL_WORK_TIME + this.WORK_TIME;
		log.info("{} 已工作 {} 小时，已摘 {} 棵，还剩 {} 棵", name, this.WORK_TIME, harvestNum, this.KIWI_TREES < 0 ? 0 : this.KIWI_TREES);
		log.info("{} 结束工作，去休息", name);
	}

	/**
	 * 交替工作
	 */
	private void alternateWork(SynchronizedKiwifruitThread kiwifruitThread) {
		for (int i = 0; i < this.FARMER; i++) {
			Thread thread = new Thread(kiwifruitThread, "farmer" + i);
			thread.start();
		}
	}

	/**
	 * 持续交替做完工作
	 */
	private void continueAlternateDoneWork(SynchronizedKiwifruitThread kiwifruitThread) {
		while (this.KIWI_TREES > 0) {
			this.alternateWork(kiwifruitThread);
		}
		log.info("摘猕猴桃总耗时 {} 小时", kiwifruitThread.TOTAL_WORK_TIME);
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		synchronized (this) {
			if (this.KIWI_TREES <= 0) {
				return;
			}
			this.farmerWork(name);
		}
	}

	public static void main(String[] args) {
		//创建摘猕猴桃线程
		SynchronizedKiwifruitThread kiwifruitThread = new SynchronizedKiwifruitThread();
		//交替工作一次
		//kiwifruitThread.alternateWork(kiwifruitThread);

		//相互摘猕猴桃直到完成
		kiwifruitThread.continueAlternateDoneWork(kiwifruitThread);
	}

}
