package com.mwk.bean.aware;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.mwk.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class AwareBeanNameTest extends UnitTestBase {

    public AwareBeanNameTest() {
        super("classpath:spring-aware.xml");
    }

    @Test
    public void Test() {
        System.out.println("AwareBeanNameTest:"+super.getBean("awareApplicationContext").hashCode());;
    }
}
