package com.mwk.function.self;

import com.google.common.base.Function;
import com.mwk.entity.PencilCase;
import com.mwk.entity.PersonDTO;
import com.mwk.entity.Schoolbag;
import com.mwk.utils.FunctionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 函数接口使用测试 {@link Function}
 *
 * @author MinWeikai
 * @date 2021/11/24 16:23
 */
public class FunctionTest {

    private static final Logger log = LoggerFactory.getLogger(FunctionTest.class);

    public static void main(String[] args) throws Exception {
        PersonDTO personDTO = PersonDTO.builder().city("深圳").name("李四").build();
        // 根据字段取值
//        getValueByFiledName(personDTO, PersonDTO::getName);


        String name = FunctionUtil.resolveMethodName(PersonDTO::getName);
        log.debug("解析出的属性名：{}", name);


        List<PersonDTO> list = Arrays.asList(
                PersonDTO.builder().city("深圳").name("李四").build(),
                PersonDTO.builder().city("深圳").name("王五").build(),
                PersonDTO.builder().city("西安").name("张三").build()
        );
        // 实现stream中取集合中某字段值集合
        List<String> names = list.stream().map(PersonDTO::getName).collect(Collectors.toList());
        log.debug("stream中取集合中某字段值集合：{}", names);

        // 自己实现的取集合中某字段值集合
        names = getValuesByFiledName(list, PersonDTO::getName);
        log.debug("自己实现的取集合中某字段值集合：{}", names);

        //--------------------------------------------------------
        List<Schoolbag> schoolbags = Arrays.asList(
                new Schoolbag(1, "黑色"),
                new Schoolbag(2, "白色"),
                new Schoolbag(3, "灰色")
        );

        List<PencilCase> pencilCases = Arrays.asList(
                new PencilCase(1, "中性笔", 2, 2),
                new PencilCase(2, "铅笔", 5, 3),
                new PencilCase(3, "钢笔", 1, 1)
        );

        // TODO 待办。。构建为整体

    }




    private static <K, V> void getValueByFiledName(V value, Function<? super V, K> fun) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        //writeReplace改了好像会报异常
        Method write = fun.getClass().getDeclaredMethod("writeReplace");
        write.setAccessible(true);
        Object sl = write.invoke(fun);
        SerializedLambda serializedLambda = (SerializedLambda) sl;
        System.out.println("serializedLambda数据为："+serializedLambda);
        System.out.println("传入的方法名为:" + serializedLambda.getImplMethodName());
    }

//    private static <K, V> K getValueByFiledName(V value, Function<? super V, K> fun){
//        return fun.apply(value);
//    }



    private static <K, V> List<K> getValuesByFiledName(Iterable<V> values, Function<? super V, K> filedName) {
        List<K> list = new ArrayList<>();
        values.forEach(value -> list.add(filedName.apply(value)));
        return list;
    }


}
