package com.mwk.lock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author MinWeikai
 * @date 2021/10/26 10:15
 */
@Slf4j
public class ReqCount {

    /**
     * 线程共享变量
     */
    private static int count1 = 0;

    private static final Integer[] count2 = {0};

    /**
     * 该方法内部是同步的
     * @return
     */
    public synchronized int req1() {
        log.debug("req1开始 线程{} count1 = {}", Thread.currentThread().getId(), count1);
        ++count1;
        log.debug("req1结束 线程{} count1 = {}", Thread.currentThread().getId(), count1);
        return count1;
    }

    public int req2() {
        log.debug("req2开始 线程{} count2 = {}", Thread.currentThread().getId(), count2[0]);
        // 内部同步
        synchronized (count2) {
            ++count2[0];
        }
        log.debug("req2结束 线程{} count2 = {}", Thread.currentThread().getId(), count2[0]);
        return count2[0];
    }


    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("ReqCount-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(10, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        int n = 6;
        for (int j = 0; j < n; j++) {
            singleThreadPool.execute(() -> {
                ReqCount reqCount = new ReqCount();
                for (int i = 0; i < 1; i++) {
//                    log.debug("req1 线程{} 第{}个请求 {}", Thread.currentThread().getId(), i, reqCount.req1());
                    log.debug("req2 线程{} 第{}个请求 {}", Thread.currentThread().getId(), i, reqCount.req2());
                }
            });
        }

        singleThreadPool.shutdown();
    }
}
