package com.mwk.bean.resource;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.mwk.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class AutowiringServiceTest extends UnitTestBase {

    public AutowiringServiceTest() {
        super("classpath:spring-resoure.xml");
    }

    @Test
    public void Test() {

        MyResource myResource =super.getBean("myResource");
        //String path="classpath:application.properties";
        String path="url:https://www.imooc.com/video/3758/0";
        try {
            myResource.getResource(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
