package com.mwk.interface1.impl;

import com.mwk.interface1.service.ManService;
import org.springframework.stereotype.Service;

/**
 * @author MinWeikai
 * @date 2020/5/25 11:24
 */
@Service
public class RapperServiceImpl implements ManService {
	@Override
	public void say() {
		System.out.println("你看这面又长有宽");
	}
}
