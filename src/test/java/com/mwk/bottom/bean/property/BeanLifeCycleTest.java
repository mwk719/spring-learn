package com.mwk.bottom.bean.property;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.mwk.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class BeanLifeCycleTest extends UnitTestBase {

    public BeanLifeCycleTest() {
        super("classpath:spring-beanlife.xml");
    }

    @Test
    public void sayTest() {
       super.getBean("beanLifeCycle");
    }
}
