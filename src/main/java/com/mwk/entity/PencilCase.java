package com.mwk.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 铅笔盒
 *
 * @author MinWeikai
 * @date 2021/4/3 12:55
 */
@Builder
@Data
public class PencilCase {

	private String name;

	private Integer num;
}
