package com.mwk.bean.autowiring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.mwk.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class AutowiringServiceTest extends UnitTestBase {

    public AutowiringServiceTest() {
        super("classpath:spring-autowiring.xml");
    }

    @Test
    public void Test() {
        AutowiringService autowiringService = super.getBean("autowiringService");
        autowiringService.say("自动装配");
    }
}
