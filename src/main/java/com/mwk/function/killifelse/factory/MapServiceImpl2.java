package com.mwk.function.killifelse.factory;

import org.springframework.stereotype.Service;

// 如果未指定value，获取实例时将默认为开头小写 mapServiceImpl2
@Service(value = "MapServiceImpl2")
public class MapServiceImpl2 implements MapService {

    @Override
    public String saySomething() {
        return "MapServiceImpl----2";
    }
}
