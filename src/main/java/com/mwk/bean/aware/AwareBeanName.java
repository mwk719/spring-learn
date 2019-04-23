package com.mwk.bean.aware;

import org.springframework.beans.factory.BeanNameAware;

public class AwareBeanName implements BeanNameAware {

    @Override
    public void setBeanName(String name) {
        System.out.println("AwareBeanName:" + name);

    }

}
