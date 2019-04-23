package com.mwk.ioc.dao;

public class PersonDaoImpl implements PersonDao {

    @Override
    public void say(String value) {
        System.out.println("PersonDao:" + value);
    }

}
