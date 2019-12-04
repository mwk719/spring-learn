package com.mwk.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

@Slf4j
@Component
public class Task {

	public static Random random = new Random();

	@Async("taskExecutor")
	public Future<Integer> run(int i) throws Exception {
		long sleep = random.nextInt(10000);
		log.info("开始任务:{}，需耗时：{}毫秒", i, sleep);
		Thread.sleep(sleep);
		log.info("完成任务");
		return new AsyncResult<>(1);
	}

}