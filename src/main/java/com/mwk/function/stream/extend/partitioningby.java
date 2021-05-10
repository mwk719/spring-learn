package com.mwk.function.stream.extend;

import com.mwk.entity.PersonDTO;
import one.util.streamex.StreamEx;

import java.util.List;
import java.util.Map;

/**
 * @author MinWeikai
 * @date 2021/5/10 17:10
 */
public class partitioningby {

    /**
     * 分区操作与分组非常相似，但将输入流分为两个列表或流，
     * 其中第一个列表中的元素满足给定的谓词，而第二个列表中的元素则不满足。
     * @param args
     */
    public static void main(String[] args) {
        // StreamEx partitioning by方法将返回地图中的两个列表对象。
        StreamEx<PersonDTO> s1 = StreamEx.of(
                PersonDTO.builder().id(1).name("John").city("London").address("address1").houseNo("100").build(),
                PersonDTO.builder().id(2).name("Tom").city("Manchester").address("address1").houseNo("101").build(),
                PersonDTO.builder().id(3).name("Paul").city("London").address("address2").houseNo("200").build(),
                PersonDTO.builder().id(4).name("Joan").city("Manchester").address("address2").houseNo("200").build()
        );
        Map<Boolean, List<PersonDTO>> m = s1.partitioningBy(dto -> dto.getAddress().equals("address1"));
        System.out.println(m);


    }
}
