package com.mwk.function.killifelse.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MapServiceFactory {
    @Autowired
    Map<String, MapService> mapservice;

    public MapService getInstance(String name){
        MapService mapService = mapservice.get(name);
        if( mapService == null){
            System.out.println("没有此实现类");
            return null;
        }
        return mapService;
    }
}
