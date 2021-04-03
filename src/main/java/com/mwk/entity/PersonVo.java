package com.mwk.entity;

import com.mwk.annotation.RespVoProperty;
import lombok.Data;

@Data
public class PersonVo {

    private String name;

    @RespVoProperty(unit = "块")
    private String money;

    @RespVoProperty(unit = "岁", replaceStr = "-")
    private String age;

    @RespVoProperty(unit = "kg", keepDecimal = 2)
    private String weight;

}
