package com.mwk.test;

import com.mwk.function.killifelse.factory.MapService;
import com.mwk.function.killifelse.factory.MapServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MinWeikai
 * @date 2019/12/3 14:34
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestConstroller {

	@Autowired
	MapServiceFactory mapservice;

	@Test
	public void test() throws Exception {
		Map<String, String> map = new HashMap<>();
		MapService mapService = mapservice.getInstance("mapServiceImpl1");
		map.put("mapServiceImpl1", mapService.saySomething());

		mapService = mapservice.getInstance("MapServiceImpl2");
		map.put("mapServiceImpl2", mapService.saySomething());
	}
}
