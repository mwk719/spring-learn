package com.mwk.bean.autowiring;

public class AutowiringServiceImpl implements AutowiringService {

    AutowiringDao autowiringDao;

    public AutowiringServiceImpl(AutowiringDao autowiringDao) {
        super();
        this.autowiringDao = autowiringDao;
        System.out.println("构造方法注入 autowiringDao");
    }

    public void setAutowiringDao(AutowiringDao autowiringDao) {
        this.autowiringDao = autowiringDao;
        System.out.println("set方法注入 autowiringDao");
    }

    @Override
    public void say(String value) {
        autowiringDao.say(value);
    }

}
