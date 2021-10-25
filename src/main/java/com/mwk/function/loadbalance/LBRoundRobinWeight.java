package com.mwk.function.loadbalance;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 加权轮询
 *
 * @author MinWeikai
 * @date 2021/10/25 14:47
 */
@Slf4j
public class LBRoundRobinWeight extends AbstractLoadBalance {
    @Override
    public String getIp() {
        Map<String, Integer> ipServerMap = new ConcurrentHashMap<>(IP_MAP.size());
        ipServerMap.putAll(IP_MAP);
        Set<String> ipSet = ipServerMap.keySet();
        List<String> ipList = new ArrayList<>();

        // 根据权重排出所有需要轮询的IP
        for (String ip : ipSet) {
            int weight = IP_MAP.get(ip);
            for (int i = 0; i < weight; i++) {
                ipList.add(ip);
            }
        }

        int max = ipList.size();
        String ip;
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
                .setNameFormat("WeightRobin-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(10, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.execute(() -> {
            LBRoundRobinWeight weightRobin = new LBRoundRobinWeight();
            for (int i = 0; i < 15; i++) {
                log.debug("{}", weightRobin.getIp());
            }
        });

//        singleThreadPool.execute(() -> {
//            WeightRobin weightRobin = new WeightRobin();
//            for (int i = 0; i < 5; i++) {
//                log.debug("{}", weightRobin.getIp());
//            }
//        });
        singleThreadPool.shutdown();


    }
}
