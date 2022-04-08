package com.mwk.function.map;

/**
 * @author MinWeikai
 * @date 2022/4/7 10:35
 */
public class HashMapLearn {

    /**
     * 获取hash值
     *
     * @param key
     * @return
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public static void main(String[] args) {
        System.out.println(hash("452"));
    }
}
