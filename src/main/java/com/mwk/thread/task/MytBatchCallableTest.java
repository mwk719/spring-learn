package com.mwk.thread.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 测试任务类
 *
 * @author MinWeikai
 * @date 2021/8/7 10:48
 */
public class MytBatchCallableTest extends AbstractBatchCallable {

	private static final Logger log = LoggerFactory.getLogger(MytBatchCallableTest.class);

	@Override
	public void exec() {
		List<Integer> list = (List<Integer>) this.list;
		log.debug("集合值:{}", list);
	}
}
