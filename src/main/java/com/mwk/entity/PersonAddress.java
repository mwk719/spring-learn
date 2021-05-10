package com.mwk.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author MinWeikai
 * @date 2021/5/10 15:11
 */
@Data
@ToString(callSuper = true)
public class PersonAddress {
    public PersonAddress() {
    }

    public PersonAddress(Integer id, String city, String address, String houseNo) {
        this.id = id;
        this.city = city;
        this.address = address;
        this.houseNo = houseNo;
    }


    private Integer id;

    private String city;

    private String address;

    private String houseNo;


}
