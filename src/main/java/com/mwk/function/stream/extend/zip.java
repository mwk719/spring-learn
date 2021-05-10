package com.mwk.function.stream.extend;

import com.mwk.entity.Person;
import com.mwk.entity.PersonAddress;
import com.mwk.entity.PersonDTO;
import one.util.streamex.StreamEx;

/**
 * @author MinWeikai
 * @date 2021/5/10 16:40
 */
public class zip {

    /**
     * guava使用
     *
     * 当前描述的所有三个库均支持压缩。让我们从Guava示例开始。它提供了唯一专用于zip的zip方法-静态方法，该方法具有三个参数：第一流，第二流和映射功能。
     * @param args
     */
//    public static void main(String[] args) {
//        Stream<Person> s1 = Stream.of(
//                new Person(1, "John"),
//                new Person(2, "Tom"),
//                new Person(3, "Paul")
//        );
//        Stream<PersonAddress> s2 = Stream.of(
//                new PersonAddress(1, "London", "Street1", "100"),
//                new PersonAddress(2, "Manchester", "Street1", "101"),
//                new PersonAddress(3, "London", "Street2", "200")
//        );
//        Stream<PersonDTO> s3 = Streams.zip(s1, s2, (p, pa) -> PersonDTO.builder()
//                .id(p.getId())
//                .name(p.getName())
//                .city(pa.getCity())
//                .address(pa.getAddress())
//                .houseNo(pa.getHouseNo()).build());
//
//        s3.forEach(dto -> {
////            Assertions.assertNotNull(dto.getId());
////            Assertions.assertNotNull(dto.getFirstName());
////            Assertions.assertNotNull(dto.getCity());
//            System.out.println(dto);
//        });
//    }


    /**
     * StreamEx
     *
     * StreamEx提供比guava上更多的可能性。我们可以在给定的流上调用某些静态方法或非静态方法。让我们看一下如何使用StreamExzipWith方法执行它。
     * @param args
     */
    public static void main(String[] args) {
        StreamEx<Person> s1 = StreamEx.of(
                new Person(1, "John"),
                new Person(2, "Tom"),
                new Person(3, "Paul")
        );
        StreamEx<PersonAddress> s2 = StreamEx.of(
                new PersonAddress(1, "London", "Street1", "100"),
                new PersonAddress(2, "Manchester", "Street1", "101"),
                new PersonAddress(3, "London", "Street2", "200")
        );

        StreamEx<PersonDTO> s3 = s1.zipWith(s2, (p, pa) -> PersonDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .city(pa.getCity())
                .address(pa.getAddress())
                .houseNo(pa.getHouseNo()).build());
        s3.forEach(dto -> {
//            Assertions.assertNotNull(dto.getId());
//            Assertions.assertNotNull(dto.getFirstName());
//            Assertions.assertNotNull(dto.getCity());
            System.out.println(dto);
        });

    }






}
