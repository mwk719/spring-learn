package com.mwk.utils.qrcode;

/**
 * 用户二维码
 * @author MinWeikai
 * @date 2021/5/21 10:20
 */
public class QrCodeUser {

    public QrCodeUser() {
    }

    public QrCodeUser(String name) {
        this.name = name;
    }

    public QrCodeUser(String name, String bloodType, String post) {
        this.name = name;
        this.bloodType = bloodType;
        this.post = post;
    }

    private String name;

    /**
     * 血型
     */
    private String bloodType;

    /**
     * 职务
     */
    private String post;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
