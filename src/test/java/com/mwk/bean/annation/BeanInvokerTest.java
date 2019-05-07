package com.mwk.bean.annation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.mwk.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class BeanInvokerTest extends UnitTestBase {

    public BeanInvokerTest() {
        super("classpath:spring-bean-annation.xml");
    }

    @Test
    public void Test() {
        BeanInvoker beanInvoker = super.getBean("beanInvoker");
        beanInvoker.say();
    }
}
