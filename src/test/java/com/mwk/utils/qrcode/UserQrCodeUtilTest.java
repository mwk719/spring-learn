package com.mwk.utils.qrcode;

import java.io.IOException;

/**
 * @author MinWeikai
 * @date 2021-05-19 18:06:39
 */
public class UserQrCodeUtilTest {

    public static void main(String[] args) throws IOException {

        // logo
        String logoPath = "qrcode/yingluolifeng.png";
        // 二维码链接
        String qrCodeUrl = "https://minwk.top/";
        // 背景板
        UserQrCodeUtil.setBgImgPath("qrcode/qr_code_label.png");

        // 临时文件路径
        UserQrCodeUtil.setTempPath("E:\\test\\spring-learn\\img");
        UserQrCodeUtil.setLogoPath(logoPath);
        UserQrCodeUtil.createByQrCodeUrl(qrCodeUrl, new QrCodeUser("李四", "A型", "监工李四和"));


    }
}
