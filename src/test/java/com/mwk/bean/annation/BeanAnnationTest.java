package com.mwk.bean.annation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.mwk.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class BeanAnnationTest extends UnitTestBase {

    public BeanAnnationTest() {
        super("classpath:spring-bean-annation.xml");
    }

    @Test
    public void Test() {
        BeanAnnation beanAnnation = super.getBean("beanAnnation");
        beanAnnation.say("注解");

        beanAnnation = super.getBean("beanAnnation");
        beanAnnation.say("注解");
    }
}
