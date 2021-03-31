package com.mwk.entity;

import com.mwk.annotation.RespVoProperty;
import lombok.Data;

@Data
public class PersonVo {

    private String name;

    private String money;

    @RespVoProperty(unit = "年龄")
    private String age;

    private String weight;

}
