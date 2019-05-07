package com.mwk.entity;

public class Man extends Person {

    public void say() {
        System.out.println("hello man");
    }
    
    public static void main(String[] args) {
        Person man=new Man();
        man.say();
        man.see();
    }

}
