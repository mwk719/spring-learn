package com.mwk.function.stream;

import cn.hutool.json.JSONUtil;
import com.mwk.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MinWeikai
 * @date 2021/7/1 15:19
 */
public class listopt {

    public static void main(String[] args) {
        List<Person> arels = new ArrayList<>();
        Person rel = new Person();
        rel.setName("1");
        arels.add(rel);
        Person rel1 = new Person();
        rel1.setName("2");
        arels.add(rel1);
        Person arel2 = new Person();
        arel2.setName("5");
        arels.add(arel2);

        List<Person> brels = new ArrayList<>();
        Person brel = new Person();
        brel.setName("2");
        brels.add(brel);
        Person brel1 = new Person();
        brel1.setName("5");
        brels.add(brel1);
        Person brel2 = new Person();
        brel2.setName("7");
        brels.add(brel2);

        // 求差集
        List<Person> list = brels.stream().filter(item -> !arels.stream().map(Person::getName)
                .collect(Collectors.toList()).contains(item.getName())).collect(Collectors.toList());
        System.out.println(JSONUtil.toJsonStr(list));

        
    }
}
