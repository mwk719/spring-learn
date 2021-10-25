package com.mwk.function.loadbalance;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * ip hash
 *
 * @author MinWeikai
 * @date 2021/10/25 16:57
 */
@Slf4j
public class LBIPHash extends AbstractLoadBalance {

    @Override
    public String getIp() {
        return null;
    }

    public String getIp(String reqIp) {
        Map<String, Integer> ipServerMap = new ConcurrentHashMap<>(IP_MAP.size());
        ipServerMap.putAll(IP_MAP);
        Set<String> ipSet = ipServerMap.keySet();
        List<String> ipList = new ArrayList<>(ipSet.size());
        ipList.addAll(ipSet);

        int hashCode = reqIp.hashCode();
        int max = ipList.size();
        // 对访问id的哈希code/ip数量取余
        int pos = hashCode % max;
        return ipList.get(pos);
    }

    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("LBIPHash-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(10, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.execute(() -> {
            LBIPHash lbipHash = new LBIPHash();
            for (int i = 0; i < 5; i++) {
                String reqIp = "192.168.1" + i + ".250";
                log.debug("请求IP[{}] 响应IP[{}]", reqIp, lbipHash.getIp(reqIp));
            }
        });
        singleThreadPool.shutdown();


    }
}
