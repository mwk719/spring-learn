package com.mwk.function.loadbalance;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;

/**
 * 随机
 * @author MinWeikai
 * @date 2021/10/25 16:40
 */
@Slf4j
public class LBRandom extends AbstractLoadBalance {
    @Override
    public String getIp() {
        Map<String, Integer> ipServerMap = new ConcurrentHashMap<>(IP_MAP.size());
        ipServerMap.putAll(IP_MAP);
        Set<String> ipSet = ipServerMap.keySet();
        List<String> ipList = new ArrayList<>(ipSet.size());
        ipList.addAll(ipSet);

        Random random = new Random();
        int pos = random.nextInt(ipList.size());
        log.debug("随机数：{}", pos);
        return ipList.get(pos);
    }

    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("LBRandom-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(10, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.execute(() -> {
            LBRandom random = new LBRandom();
            for (int i = 0; i < 5; i++) {
                log.debug("{}", random.getIp());
            }
        });
        singleThreadPool.shutdown();


    }
}
