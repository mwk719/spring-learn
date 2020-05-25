package com.mwk.interface1;

import com.mwk.interface1.service.ManService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author MinWeikai
 * @date 2020/5/25 11:25
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ManServiceTest {

	@Qualifier("generalServiceImpl")
	@Autowired
	private ManService generalService;

	@Qualifier("rapperServiceImpl")
	@Autowired
	private ManService rapperService;

	@Test
	public void sayTest() {
		rapperService.say();
		generalService.say();
	}
}
