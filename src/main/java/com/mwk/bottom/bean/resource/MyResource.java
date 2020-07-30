package com.mwk.bottom.bean.resource;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

public class MyResource implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void getResource(String path) throws IOException {
        Resource resource = applicationContext.getResource(path);
        System.out.println(resource.getFilename());
        System.out.println(resource.contentLength());
    }

}
