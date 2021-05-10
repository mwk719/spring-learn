package com.mwk.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author MinWeikai
 * @date 2021/5/10 15:13
 */
@Data
@Builder
public class PersonDTO {

    private Integer id;

    private String name;

    private String city;

    private String address;

    private String houseNo;
}
