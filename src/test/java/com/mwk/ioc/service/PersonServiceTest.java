package com.mwk.ioc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.mwk.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class PersonServiceTest extends UnitTestBase {

    public PersonServiceTest() {
        super("classpath:spring-ioc.xml");
    }

    @Test
    public void sayTest() {
        PersonService personService = super.getBean("personService");
        personService.say("自动注入测试:" + personService.hashCode());
//        personService = super.getBean("personService");
//        personService.say("自动注入测试:" + personService.hashCode());
    }

}
