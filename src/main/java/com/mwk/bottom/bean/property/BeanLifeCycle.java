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

    private String name;

    public void setName(String name) {
        this.name = name;
        //// TODO  设置属性方法在自定义初始化方法后执行 ??
        System.out.println("2. 设置属性，执行setXXX方法");
    }

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
        System.out.println("7. 自定义初始化方法:customStart()");
    }

    /**
     * 自定义销毁方法
     */
    private void customStop() {
        System.out.println("10. 自定义销毁方法:customStop()");

    }


    @Override
    public void destroy() {
        System.out.println("9. BeanLifeCycle:destroy()");

    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("6. BeanLifeCycle:afterPropertiesSet()");

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


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("6. 如果这个Bean关联了BeanPostProcessor接口，将会调用postProcessBeforeInitialization" +
                "(Object obj, String s)方法，BeanPostProcessor经常被用作是Bean内容的更改，并且由于这个是在Bean初始化结束" +
                "时调用那个的方法，也可以被应用于内存或缓存技术");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("8. 如果这个Bean关联了BeanPostProcessor接口，将会调用postProcessAfterInitialization" +
                        "(Object obj, String s)方法、；// 注：以上工作完成以后就可以应用这个Bean了，那这个Bean是一个" +
                        "Singleton的，所以一般情况下我们调用同一个id的Bean会是在内容地址相同的实例，当然在Spring配置" +
                        "文件中也可以配置非Singleton，这里我们不做赘述。");
        return bean;
    }


}
