package com.mwk.ioc.service;

import com.mwk.ioc.dao.PersonDao;

public class PersonServiceImpl implements PersonService {

    PersonDao personDao;

    /**
     * 构造器注入
     * 
     * @param personDao
     */
    public PersonServiceImpl(PersonDao personDao) {
        super();
        this.personDao = personDao;
    }

    /**
     * 设值注入
     * 
     * @param personDao
     */
    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public void say(String value) {
        System.out.println("PersonService:" + value);
        personDao.say(value);
    }

}
