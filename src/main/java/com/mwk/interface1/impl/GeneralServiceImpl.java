package com.mwk.interface1.impl;

import com.mwk.interface1.service.ManService;
import org.springframework.stereotype.Service;

/**
 * @author MinWeikai
 * @date 2020/5/25 11:13
 */
@Service
public class GeneralServiceImpl implements ManService {
	@Override
	public void say() {
		System.out.println("你唱的是啥");
	}
}
