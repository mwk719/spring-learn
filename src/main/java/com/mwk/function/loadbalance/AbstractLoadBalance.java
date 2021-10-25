package com.mwk.function.loadbalance;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象负载均衡测试
 *
 * @author MinWeikai
 * @date 2021/10/25 13:53
 */
public abstract class AbstractLoadBalance {

    protected static final Map<String, Integer> IP_MAP = new HashMap<>();
    protected static final Integer[] pos = {0};

    static {
        IP_MAP.put("192.168.7.1", 1);
        IP_MAP.put("192.168.7.2", 3);
        IP_MAP.put("192.168.7.3", 6);
    }

    public abstract String getIp();

}
