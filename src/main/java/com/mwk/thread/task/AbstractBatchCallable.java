package com.mwk.thread.task;


import com.mwk.utils.Pager;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * 线程批量处理执行抽象类，需要批量处理数据的任务都可以继承此抽象类
 *
 * @author MinWeikai
 * @date 2021/8/7 10:44
 */
public abstract class AbstractBatchCallable implements Callable<Integer> {

	/**
	 * 需要批量处理的数据集
	 */
	protected List list;

	private Pager pager;

	@Override
	public Integer call() {
		if (CollectionUtils.isEmpty(list)) {
			return 0;
		}
		this.exec();
		return list.size() < pager.getPageSize() ? 0 : 1;
	}

	/**
	 * 自定义的执行方法
	 */
	protected abstract void exec();

	public AbstractBatchCallable setPager(Pager pager) {
		this.pager = pager;
		this.list = pager.getContent();
		return this;
	}
}
