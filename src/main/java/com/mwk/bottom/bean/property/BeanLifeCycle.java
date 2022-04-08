package com.mwk.bottom.bean.property;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Spring框架中Bean的生命周期
 *
 * @author MinWeikai
 * @date 2022-04-07 16:27:42
 */
public class BeanLifeCycle implements InitializingBean, DisposableBean, BeanNameAware {

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
        System.out.println("BeanLifeCycle:setBeanName() " + s);
    }
}
