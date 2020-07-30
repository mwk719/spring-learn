package com.mwk.bottom.bean.annation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BeanInvoker {

    @Autowired
    List<BeanService> beanList;

    @Autowired
    Map<String, BeanService> beanMap;

    @Autowired
    @Qualifier("beanServiceImplOne")
    BeanService beanService;

    public void say() {
        if (null != beanList && 0 != beanList.size()) {
            for (BeanService beanService : beanList) {
                System.out.println(beanService.getClass().getName());
            }
        } else {
            System.err.println(" beanList is null !!! ");
        }

        if (null != beanMap && 0 != beanMap.size()) {
            for (Map.Entry<String, BeanService> entry : beanMap.entrySet()) {
                System.out.println(entry.getKey() + "  " + entry.getValue().getClass().getName());
            }
        } else {
            System.err.println(" beanMap is null !!! ");
        }

        if (null != beanService) {
            System.out.println(beanService.getClass().getName());
        } else {
            System.err.println(" beanList is null !!! ");
        }

    }
}
