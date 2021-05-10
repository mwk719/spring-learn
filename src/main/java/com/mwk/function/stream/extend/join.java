package com.mwk.function.stream.extend;

import com.mwk.entity.Person;
import com.mwk.entity.PersonAddress;
import com.mwk.entity.PersonDTO;
import org.jooq.lambda.Seq;

import java.util.Optional;

/**
 * @author MinWeikai
 * @date 2021-05-10 15:24:25
 * <p>
 * https://mp.weixin.qq.com/s/mLqkianmxVld4kjqNNYbBQ
 *
 * 源码地址
 * https://gitee.com/mwk719/jOOL
 */
public class join {

    /**
     * 当涉及联接操作时，只有jOOλ提供了一些方法。由于它专用于面向对象的查询，
     * 因此我们可以在许多联接选项之间进行选择。例如有innerJoin，leftOuterJoin，
     * rightOuterJoin和crossJoin方法。在下面可见的源代码中，您可以看到一个示例innerJoin用法。
     * 此方法采用两个参数：要加入的流和谓词以匹配来自第一个流和加入流的元素。
     * 如果要基于innerJoin结果创建新对象，则应另外调用mapoperation。
     * @param args
     */
    public static void main(String[] args) {

        Seq<Person> s1 = Seq.of(
                new Person(1, "John"),
                new Person(2, "Tom"),
                new Person(3, "Paul"));
        Seq<PersonAddress> s2 = Seq.of(
                new PersonAddress(2, "London", "Street1", "100"),
                new PersonAddress(3, "Manchester", "Street1", "101"),
                new PersonAddress(4, "London", "Street2", "200"));
        Seq<PersonDTO> s3 = s1.leftOuterJoin(s2, (p, pa) -> p.getId().equals(pa.getId())).map(t -> PersonDTO.builder()
                .id(t.v1.getId())
                .name(t.v1.getName())
                .city(Optional.ofNullable(t.v2).map(PersonAddress::getCity).orElse(null))
                .address(Optional.ofNullable(t.v2).map(PersonAddress::getAddress).orElse(null))
                .houseNo(Optional.ofNullable(t.v2).map(PersonAddress::getHouseNo).orElse(null))
                .build());
        s3.forEach(System.out::println);






    }
}
