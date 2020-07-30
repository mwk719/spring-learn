package com.mwk.bottom.bean.aware;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.mwk.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class AwareApplicationContextTest extends UnitTestBase {

    public AwareApplicationContextTest() {
        super("classpath:spring-aware.xml");
    }

    @Test
    public void Test() {
        System.out.println("AwareApplicationContextTest:"+super.getBean("awareApplicationContext").hashCode());;
    }
}
