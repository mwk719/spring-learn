package com.mwk.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.mwk.entity.AreaData;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author MinWeikai
 * @date 2021/3/13 17:49
 */
public class CascadeUtil {

    private static  <T> List<T> takeIsaAuditTask(List<T> sources) {
        List<T> tasks = new ArrayList<>();
        if(CollectionUtil.isEmpty(sources)){
            return Collections.emptyList();
        }
        List<T> tempTasks;
        for (T pojo : sources) {
            List<T> temps = (List<T>) ReflectionUtil.invokeGetterMethod(pojo,"Children");
            if (CollectionUtil.isNotEmpty(temps)) {
                assert temps != null;
                tasks.addAll(temps);
                tempTasks = takeIsaAuditTask(temps);
                if(!CollectionUtils.isEmpty(tempTasks)){
                    tasks.addAll(tempTasks);
                }
            }
        }
        return tasks;
    }

    private static  <T> List<T> takeIsaAuditTask1(List<T> sources) {
        takeIsaAuditTask(sources).addAll(sources);
        return sources;
    }

    public static void main(String[] args) {
        List<AreaData> data = Arrays.asList(
                AreaData.builder().name("深圳")
                        .children(Arrays.asList(
                                AreaData.builder().name("宝安").children(Arrays.asList(
                                        AreaData.builder().name("西乡").build(),
                                        AreaData.builder().name("坪洲").build()
                                ))
                                        .build(),
                                AreaData.builder().name("南山").build()
                        ))
                        .build(),
                AreaData.builder().name("广州")
                        .build()
        );

        System.out.println(JSONUtil.parse(data));

        System.out.println(takeIsaAuditTask(data));

        System.out.println(takeIsaAuditTask1(data));

    }
}
