package com.mwk.ioc.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.mwk.base.UnitTestBase;
import com.mwk.ioc.service.PersonService;

@RunWith(BlockJUnit4ClassRunner.class)
public class PersonDaoTest extends UnitTestBase {

    public PersonDaoTest() {
        super("classpath:spring-injection.xml");
    }

    @Test
    public void sayTest() {
        PersonService personService = super.getBean("personService");
        personService.say("自动注入测试:" + personService.hashCode());
//        personService = super.getBean("personService");
//        personService.say("自动注入测试:" + personService.hashCode());
    }

}
