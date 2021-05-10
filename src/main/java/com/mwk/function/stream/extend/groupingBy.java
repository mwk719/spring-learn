package com.mwk.function.stream.extend;

import com.mwk.entity.PersonDTO;
import one.util.streamex.StreamEx;

import java.util.List;
import java.util.Map;

/**
 * @author MinWeikai
 * @date 2021/5/10 16:53
 */
public class groupingBy {

    /**
     * Java流API仅通过Java.util.Stream.Collectors中的静态方法groupingBy支持的下一个有用操作是分组
     * （s1.collect（Collectors.groupingBy（PersonDTO:：getCity）））。
     * 在流上执行这样一个操作的结果是，您得到一个带有键的映射，这些键是将分组函数应用于输入元素后得到的值，
     * 其对应的值是包含输入元素的列表。这个操作是某种聚合，因此得到java.util.List，
     * 结果是没有java.util.stream.stream。StreamEx和jOOλ 提供一些分组流的方法。
     * 让我们从StreamEx groupingBy操作示例开始。假设我们有一个PersonDTO对象的输入流，
     * 我们将按个人的家乡城市对它们进行分组。
     * @param args
     */
    public static void main(String[] args) {
        StreamEx<PersonDTO> s1 = StreamEx.of(
                PersonDTO.builder().id(1).name("John").city("London").address("address1").houseNo("100").build(),
                PersonDTO.builder().id(2).name("Tom").city("Manchester").address("address1").houseNo("101").build(),
                PersonDTO.builder().id(3).name("Paul").city("London").address("address2").houseNo("200").build(),
                PersonDTO.builder().id(4).name("Joan").city("Manchester").address("address2").houseNo("200").build()
        );
//        Map<String, List<PersonDTO>> m = s1.groupingBy(PersonDTO::getCity);
//        System.out.println(m);

        Map<String, List<PersonDTO>> m = s1.groupingBy(e -> e.getCity() + e.getHouseNo());
        System.out.println(m);
//        Assertions.assertNotNull(m.get("London"));
//        Assertions.assertTrue(m.get("London").size() == 2);
//        Assertions.assertNotNull(m.get("Manchester"));
//        Assertions.assertTrue(m.get("Manchester").size() == 2);






//        Seq<PersonDTO> s2 = Seq.of(
//                PersonDTO.builder().id(1).name("John").city("London").address("address1").houseNo("100").build(),
//                PersonDTO.builder().id(2).name("Tom").city("Manchester").address("address1").houseNo("101").build(),
//                PersonDTO.builder().id(3).name("Paul").city("London").address("address2").houseNo("200").build(),
//                PersonDTO.builder().id(4).name("Joan").city("Manchester").address("address2").houseNo("200").build()
//        );
//        Map<String, List<PersonDTO>> m2 = s2.groupBy(PersonDTO::getCity);
//
//
//        System.out.println(m2);
    }
}
