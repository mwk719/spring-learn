package com.mwk.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 级联数据
 *
 * @author MinWeikai
 * @date 2021/3/13 17:51
 */
@Data
@Builder
public class AreaData {

    private String name;

    private List<AreaData> children;
}
