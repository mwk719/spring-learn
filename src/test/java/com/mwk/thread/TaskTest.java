package com.mwk.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author MinWeikai
 * @date 2019/12/3 14:34
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskTest {

	@Autowired
	private Task task;

	@Test
	public void test() throws Exception {
		int n = 10;
		List<Future<Integer>> futures = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			Future<Integer> future = task.run(i);
			futures.add(future);
		}
		int a=0;
		for (Future<Integer> f : futures) {
			a+=f.get();
		}

		log.info("线程执行状态：{}",a);
	}
}
