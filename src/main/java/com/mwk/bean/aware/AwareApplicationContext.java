package com.mwk.bean.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AwareApplicationContext implements BeanNameAware, ApplicationContextAware {

    private String beanName;

    /**
     * 当前的applicationContext， 这也可以调用容器的服务
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("AwareApplicationContext:" + applicationContext.getBean(beanName).hashCode());

    }

    @Override
    public void setBeanName(String name) {
        beanName = name;
    }

}
