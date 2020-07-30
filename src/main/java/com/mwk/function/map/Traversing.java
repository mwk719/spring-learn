package com.mwk.function.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * map的遍历
 * 
 * @@我这辈子都不会忘记这个耻辱！！！
 * 
 *                    @author 闵渭凯
 *
 *                    2019年5月7日
 */
public class Traversing {

    public static void main(String[] args) {

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "刘一");
        map.put(2, "毛二");
        map.put(3, "张三");
        map.put(4, "李四");
        map.put(5, "王五");
        System.out.println(map.toString());

        System.out.println("------------1. 常见方式-----------");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("key=" + entry.getKey() + "，value=" + entry.getValue());
        }

        System.out.println("------------2. 先取key再取value，效率低-----------");
        for (Integer key : map.keySet()) {
            System.out.println("key=" + key + "，value=" + map.get(key));
        }

        System.out.println("------------3. 使用Iterator遍历-----------");
        Iterator<Entry<Integer, String>> entrys = map.entrySet().iterator();
        while (entrys.hasNext()) {
            Map.Entry<Integer, String> entey = entrys.next();
            System.out.println("key=" + entey.getKey() + "，value=" + entey.getValue());
        }

        System.out.println("------------集合遍历-----------");
        map.values().forEach(System.out::println);
        // 包含“一”
        map.values().stream().filter(value -> value.contains("一")).forEach(System.out::println);

    }

}
