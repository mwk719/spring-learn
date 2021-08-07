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

//    private TaskSender taskSender;


    /**
     * 每轮线程数
     */
    private int poolSize = 10;

    /**
     * 每页数量，每轮处理数据量
     */
    private int pageSize = 10;

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

//    public TaskSender getTaskSender() {
//        if (Objects.isNull(taskSender)) {
//            taskSender = SpringUtils.getBean(TaskSender.class);
//        }
//        return taskSender;
//    }

    public void start() {
        TimeInterval timer = DateUtil.timer();
        log.info("----开始生成数据----");
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
//                list.add();
//                this.getTaskSender().send(taskName,
//                        new ExecutorBatch<T>(this.type, new Pager<>(this.list, page, this.pageSize), objectService)
//                )
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

        log.info("----总轮数：" + rounds + "，总页数：" + page + "，耗时：" + timer.intervalMinute());
    }

}
