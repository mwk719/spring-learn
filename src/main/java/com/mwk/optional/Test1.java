package com.mwk.optional;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;

public class Test1 {

    private Integer a = 1;
    private Integer b = 5;

    public static void main(String[] args) throws Exception {
        Test1 test = new Test1();

        Optional<Test1> opt = Optional.ofNullable(test);
        System.out.println(opt.isPresent());
    }

    @Test
    public void sum() throws Exception {
        //Test1 test = null;
        Test1 test = new Test1();
        
        Optional<Test1> opt = Optional.ofNullable(test);
        assertNotNull(opt.isPresent() ? opt.get().a + opt.get().b : null);
    }

}
