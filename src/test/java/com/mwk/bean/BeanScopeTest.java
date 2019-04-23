package com.mwk.bean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.mwk.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class BeanScopeTest extends UnitTestBase {

    public BeanScopeTest() {
        super("classpath:spring-beanscope.xml");
    }

    @Test
    public void sayTest() {
        BeanScope beanScope = super.getBean("beanScope");
        beanScope.say("bean作用域:" + beanScope.hashCode());
        
        beanScope = super.getBean("beanScope");
        beanScope.say("bean作用域:" + beanScope.hashCode());
    }
    
    @Test
    public void sayTest1() {
        BeanScope beanScope = super.getBean("beanScope");
        beanScope.say("bean作用域:" + beanScope.hashCode());
    }
}
