package com.mwk.bean;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class BeanLifeCycle implements InitializingBean,DisposableBean{
    
    private void start() {
       System.out.println("BeanLifeCycle:start()");

    }
    
    private void stop() {
        System.out.println("BeanLifeCycle:stop()");

    }
    
    private void defaultStart() {
        System.out.println("BeanLifeCycle:defaultStart()");

    }
    
    private void defaultStop() {
        System.out.println("BeanLifeCycle:defaultStop()");

    }

    @Override
    public void destroy() throws Exception {
        System.out.println("BeanLifeCycle:destroy()");
        
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("BeanLifeCycle:afterPropertiesSet()");
        
    }

}
