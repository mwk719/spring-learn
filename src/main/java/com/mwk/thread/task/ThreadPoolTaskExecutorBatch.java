package com.mwk.thread.task;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 批量线程处理器
 *
 * @author MinWeikai
 * @date 2021/7/22 11:03
 */
public class ThreadPoolTaskExecutorBatch<T> {

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

    /**
     * 自动分配线程数
     */
    private boolean autoPoolSize = true;

    /**
     * 需要批量处理的数据集
     */
    private List<T> list;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 服务处理方法
     */
    private Object objectService;

    /**
     * 任务名称
     */
    private String taskName;

    public ThreadPoolTaskExecutorBatch() {
    }


    public ThreadPoolTaskExecutorBatch<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    public ThreadPoolTaskExecutorBatch<T> setObjectService(Object objectService) {
        this.objectService = objectService;
        return this;
    }

    public ThreadPoolTaskExecutorBatch<T> setType(Integer type) {
        this.type = type;
        return this;
    }

    public ThreadPoolTaskExecutorBatch<T> setTaskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public ThreadPoolTaskExecutorBatch<T> setPoolSize(int poolSize) {
        this.poolSize = poolSize;
        return this;
    }

    public ThreadPoolTaskExecutorBatch<T> setPageSize(int pageSize) {
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
        List<Future<Object>> list = new ArrayList<>();
        while (proceed) {
            rounds++;
            int temp = 0;
            for (int k = 0; k < this.poolSize; k++) {
                page++;
//                list.add(this.getTaskSender().send(taskName,
//                        new ExecutorBatch<T>(this.type, new Page<>(this.list, page, this.pageSize), objectService)
//                        )
//                );
            }
            try {
                for (Future<Object> result : list) {
                    temp += Convert.toInt(result.get());
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
