package com.mwk.function.loadbalance;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 轮询
 *
 * @author MinWeikai
 * @date 2021/10/25 11:02
 */
@Slf4j
public class LBRoundRobin extends AbstractLoadBalance {

    @Override
    public String getIp() {
        Map<String, Integer> ipServerMap = new ConcurrentHashMap<>(IP_MAP.size());
        ipServerMap.putAll(IP_MAP);
        Set<String> ipSet = ipServerMap.keySet();
        List<String> ipList = new ArrayList<>(ipSet.size());
        ipList.addAll(ipSet);

        int max = ipList.size();
        String ip;


        /*
         1. 使用静态变量pos保证同步
         2. 非静态则认为锁的是对象，高并发时每次都从第一个开始轮询，并没有达到真正的轮询
         */
        synchronized (pos) {
            if (pos[0] >= max) {
                pos[0] = 0;
            }
            ip = ipList.get(pos[0]);
            pos[0]++;
        }

        return ip;
    }

    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("RoundRobin-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(10, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.execute(() -> {
            LBRoundRobin roundRobin = new LBRoundRobin();
            for (int i = 0; i < 3; i++) {
                log.debug("线程{} 第{}个请求 {}", Thread.currentThread().getId(), i, roundRobin.getIp());
            }
        });

        singleThreadPool.execute(() -> {
            LBRoundRobin roundRobin = new LBRoundRobin();
            for (int i = 0; i < 3; i++) {
                log.debug("线程{} 第{}个请求 {}", Thread.currentThread().getId(), i, roundRobin.getIp());
            }
        });

        singleThreadPool.execute(() -> {
            LBRoundRobin roundRobin = new LBRoundRobin();
            for (int i = 0; i < 3; i++) {
                log.debug("线程{} 第{}个请求 {}", Thread.currentThread().getId(), i, roundRobin.getIp());
            }
        });

        singleThreadPool.execute(() -> {
            LBRoundRobin roundRobin = new LBRoundRobin();
            for (int i = 0; i < 3; i++) {
                log.debug("线程{} 第{}个请求 {}", Thread.currentThread().getId(), i, roundRobin.getIp());
            }
        });

        singleThreadPool.execute(() -> {
            LBRoundRobin roundRobin = new LBRoundRobin();
            for (int i = 0; i < 3; i++) {
                log.debug("线程{} 第{}个请求 {}", Thread.currentThread().getId(), i, roundRobin.getIp());
            }
        });
        singleThreadPool.shutdown();
    }
}
