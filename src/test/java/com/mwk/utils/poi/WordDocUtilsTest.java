package com.mwk.utils.poi;

import cn.hutool.core.io.FileUtil;
import com.mwk.entity.Person;
import com.mwk.utils.poi.bean.ImageBean;
import com.mwk.utils.qrcode.QrCodeUser;
import com.mwk.utils.qrcode.UserQrCodeUtil;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @author MinWeikai
 * @date 2021/5/21 16:33
 */
public class WordDocUtilsTest {

    public static Map<String, Object> getUserQrCodeInfo() {
        Map<String, Object> map = new HashMap<>();

        List<Person> list = Arrays.asList(
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("李四r"),
                new Person("李四1"),
                new Person("张三2")
        );
        String qrCodeUrl = "https://minwk.top/";

        int width = 240;
        int height = 106;

        List<ImageBean> imgList = new ArrayList<>();
        list.stream().forEach(u->{
            ImageBean imageBean = new ImageBean();
            //192 240
            imageBean.setWidth(width);
            //85 106
            imageBean.setHeight(height);
            imageBean.setType("jpg");
            imageBean.setContent(
                    UserQrCodeUtil.createByQrCodeUrl(qrCodeUrl,
                            new QrCodeUser(u.getName(), "A型","码农"))
            );
            imgList.add(imageBean);
        });
        UserQrCodeUtil.deleteTempFiles();
        map.put("pic001", imgList);
        return map;
    }

    public static void main(String[] args) {
        // word模板
        String docTemplatePath = "word/user_qrcode_export.docx";
        // logo
        UserQrCodeUtil.setLogoPath("qrcode/yingluolifeng.png");
        // 背景板
        UserQrCodeUtil.setBgImgPath("qrcode/qr_code_label.png");
        // 临时文件路径
        UserQrCodeUtil.setTempPath("E:\\test\\spring-learn\\img");

        // 文件信息
        Map<String, Object> params = getUserQrCodeInfo();

        String fileName = "测试人员二维码.docx";
        String exportDoc = "E:\\test\\spring-learn\\word\\".concat(fileName);

        try (BufferedOutputStream os = FileUtil.getOutputStream(exportDoc)) {
            InputStream is = new FileInputStream(FileUtil.file(docTemplatePath));
            WordDocUtils.testWriteDocx(is, params).write(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
