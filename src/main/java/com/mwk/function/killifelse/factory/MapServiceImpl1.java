package com.mwk.function.killifelse.factory;

import org.springframework.stereotype.Service;

@Service
public class MapServiceImpl1 implements MapService {
    @Override
    public String saySomething() {
        return "MapServiceImpl----1";
    }
}
