package com.mwk.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 铅笔盒
 *
 * @author MinWeikai
 * @date 2021/4/3 12:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PencilCase extends BasePojo {

	public PencilCase() {
	}

	public PencilCase(String name, Integer num) {
		this.name = name;
		this.num = num;
	}

	public PencilCase(Integer id,String name, Integer num, Integer schoolbagId) {
		this.name = name;
		this.num = num;
		this.schoolbagId = schoolbagId;
		super.setId(id);
	}

	private String name;

	private Integer num;

	private Integer schoolbagId;
}
