package com.mwk.bottom.bean.property;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring框架中Bean的生命周期
 *
 * @author MinWeikai
 * @date 2022-04-07 16:27:42
 */
public class BeanLifeCycle implements InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware, ApplicationContextAware, BeanPostProcessor {

    public BeanLifeCycle() {
        /*
         解析xml文件到BeanDefinition以
         Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>
         key为bean name
         */
        System.out.println("1. 通过构造方法实例化一个 BeanLifeCycle");
    }

    /**
     * 自定义初始化方法
     */
    private void customStart() {
        System.out.println("自定义初始化方法:customStart()");
    }

    /**
     * 自定义销毁方法
     */
    private void customStop() {
        System.out.println("自定义销毁方法:customStop()");

    }

//    private void defaultStart() {
//        System.out.println("BeanLifeCycle:defaultStart()");
//
//    }
//
//    private void defaultStop() {
//        System.out.println("BeanLifeCycle:defaultStop()");
//
//    }

    @Override
    public void destroy() {
        System.out.println("BeanLifeCycle:destroy()");

    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("BeanLifeCycle:afterPropertiesSet()");

    }

    @Override
    public void setBeanName(String s) {
        System.out.println("3. 如果实现了BeanNameAware接口，调用setBeanName()方法，设置XML里面注册的ID=" + s);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("4. 如果这个 Bean 已经实现了 BeanFactoryAware 接口，会调用它实现的 setBeanFactory，" +
                "setBeanFactory(BeanFactory)传递的是 Spring 工厂自身（可以用这个方式来获取其它 Bean，" +
                "只需在 Spring 配置文件中配置一个普通的 Bean 就可以）");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("5. 如果这个 Bean 已经实现了 ApplicationContextAware 接口，会调用\n" +
                "setApplicationContext(ApplicationContext)方法，传入 Spring 上下文（同样这个方式也\n" +
                "可以实现步骤 4 的内容，但比 4 更好，因为 ApplicationContext 是 BeanFactory 的子接\n" +
                "口，有更多的实现方法）");
    }


}
