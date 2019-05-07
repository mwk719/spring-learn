package com.mwk.bean.annation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 
 * 作用域
 */
@Scope("prototype")
@Component
public class BeanAnnation {

    public void say(String word) {
        System.out.println("BeanAnnation:" + word + "  " + this.hashCode());
    }

}
