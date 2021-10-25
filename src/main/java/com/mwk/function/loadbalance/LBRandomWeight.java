package com.mwk.function.loadbalance;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;

/**
 * 加权随机
 *
 * @author MinWeikai
 * @date 2021/10/25 16:50
 */
@Slf4j
public class LBRandomWeight extends AbstractLoadBalance {
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

        Random random = new Random();
        int pos = random.nextInt(ipList.size());
        log.debug("随机数：{}", pos);
        return ipList.get(pos);
    }

    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("LBRandomWeight-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(10, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.execute(() -> {
            LBRandomWeight random = new LBRandomWeight();
            for (int i = 0; i < 5; i++) {
                log.debug("{}", random.getIp());
            }
        });
        singleThreadPool.shutdown();


    }
}
