package com.mwk.thread.task;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 测试处理集合调用方法
 *
 * @author MinWeikai
 * @date 2021/8/13 22:04
 */
public class ThreadPoolTaskExecutorBatchTest {

	public static void main(String[] args) {
		List<Integer> list = IntStream.range(1, 1995).boxed().collect(Collectors.toList());
		System.out.println("待执行集合：" + list);
		ThreadPoolTaskExecutorBatch
				.build()
				.setAbstractBatchCallable(MytBatchCallableTest.class)
				.setList(list)
//				.setAutoPoolSize(false)
//				.setPoolSize(10)
//				.setPageSize(50)
				.start();
	}
}
