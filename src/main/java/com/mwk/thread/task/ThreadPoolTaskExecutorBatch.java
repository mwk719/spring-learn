package com.mwk.thread.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.mwk.utils.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 批量线程处理器
 *
 * @author MinWeikai
 * @date 2021/7/22 11:03
 */
public class ThreadPoolTaskExecutorBatch {

    private static final Logger log = LoggerFactory.getLogger(ThreadPoolTaskExecutorBatch.class);

    /**
     * 每轮线程数
     */
    private int poolSize = 10;

    /**
     * 每页数量，每轮处理数据量
     */
    private int pageSize = 10;


    private int maxPoolSize = 10;

    private int maxPageSize = 10;

    private Class abstractBatchCallable;

    /**
     * 自动分配线程数
     */
    private boolean autoPoolSize = true;

    /**
     * 需要批量处理的数据集
     */
    private List list;


    public static ThreadPoolTaskExecutorBatch build() {
		return new ThreadPoolTaskExecutorBatch();
    }

    public ThreadPoolTaskExecutorBatch setAbstractBatchCallable(Class abstractBatchCallable) {
        this.abstractBatchCallable = abstractBatchCallable;
        return this;
    }

    public static  AbstractBatchCallable getInstance(Class batchCallable) throws Exception {
        return (AbstractBatchCallable) batchCallable.newInstance();
    }

    public ThreadPoolTaskExecutorBatch setList(List list) {
        this.list = list;
        return this;
    }

    public ThreadPoolTaskExecutorBatch setPoolSize(int poolSize) {
        this.poolSize = poolSize;
        return this;
    }

    public ThreadPoolTaskExecutorBatch setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public void setAutoPoolSize(boolean autoPoolSize) {
        this.autoPoolSize = autoPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public void start() {
        TimeInterval timer = DateUtil.timer();
        log.info("----开始生成数据----");
        if(!this.autoPoolSizeByList()){
            return;
        }
        log.debug("批任务处理信息：autoPoolSize={} poolSize={} pageSize={} maxPoolSize={} maxPageSize={}",
                this.autoPoolSize, this.poolSize, this.pageSize, this.maxPoolSize, this.maxPageSize);
        //是否继续
        boolean proceed = true;
        //线程创建轮数
        int rounds = 0;
        //起始页数
        int page = 0;
        List<Callable<Integer>> list = new ArrayList<>();
	    Pager pager;
        while (proceed) {
            rounds++;
            int temp = 0;
            for (int k = 0; k < this.poolSize; k++) {
                page++;
	            try {
		            pager = new Pager<>(this.list, page, pageSize);
		            if(pager.getContent().size() == 0){
		            	break;
		            }
		            list.add(getInstance(abstractBatchCallable).send(pager));
	            } catch (Exception e) {
		            e.printStackTrace();
	            }
            }
            try {
                ExecutorService executor = Executors.newCachedThreadPool();
                List<Future<Integer>> results = executor.invokeAll(list);
                executor.shutdown();
                for (Future<Integer> result : results) {
                    temp += result.get();
                }
            } catch (Exception e) {
                log.error("生成数据出错" + e.getMessage(), e);
            }
            log.info("----线程创建轮【" + rounds + "】，页数：" + page + "，当前轮结束状态" + temp);
            if (temp < this.poolSize) {
                proceed = false;
            }
            list.clear();
        }

        log.info("----总轮数：" + rounds + "，总页数：" + page + "，耗时：" + timer.intervalSecond());
    }

    /**
     * 自动计算批任务执行线程数、每线程执行任务数
     * @return
     */
    private boolean autoPoolSizeByList(){
        if(!this.autoPoolSize){
            return true;
        }
        int allSize = this.list.size();
        if(allSize == 0){
            return false;
        }
        // 任务总数小于等于最大线程数，则创建任务数线程，每线程执行1任务
        if(allSize <= this.maxPoolSize){
            this.setPoolSize(allSize);
            this.setPageSize(1);
            return true;
        }
        // 任务总数小于等于最大线程数*最大线程执行任务数，则线程数最大值，每线程执行任务总数除以线程数最大值进位值
        int rem = allSize % this.maxPoolSize;
        int value = allSize / this.maxPoolSize;
        if(allSize <= this.maxPoolSize * this.maxPageSize){
            this.setPoolSize(this.maxPoolSize);
            this.setPageSize(rem == 0 ? value : (value + 1));
            return true;
        }
        return true;
    }

}
