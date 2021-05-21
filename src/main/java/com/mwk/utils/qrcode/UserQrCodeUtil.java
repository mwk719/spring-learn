package com.mwk.utils.qrcode;

import cn.hutool.core.img.Img;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.mwk.utils.PathUtil;
import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于生成用户二维码标签
 *
 * @author MinWeikai
 * @date 2021/5/19 15:57
 */
public class UserQrCodeUtil {

    /**
     * logo路径
     */
    private static String LOGO_PATH;

    /**
     * 临时文件夹地址
     */
    private static String TEMP_PATH;

    private static List<String> TEMP_FILES_PATH;
    /**
     * 背景图
     */
    private static String BG_IMG_PATH;


    /**
     * 通过qrCodeUrl和用户名生成二维码标签
     * todo 待优化，可抽象出公共方法
     *
     * @param qrCodeUrl
     * @param user
     * @return
     */
    public static byte[] createByQrCodeUrl(String qrCodeUrl, QrCodeUser user) {
        String logoPath = LOGO_PATH;
        String qrCodePath = TEMP_PATH + "/temp_user_qrcode.jpg";
        String qrCodeBgPath = TEMP_PATH + "/temp_user_qrcode_bg.jpg";

        TEMP_FILES_PATH = new ArrayList<>();
        TEMP_FILES_PATH.add(qrCodePath);
        TEMP_FILES_PATH.add(qrCodeBgPath);

        File bai = FileUtil.file(BG_IMG_PATH);

        File qrCodeFile = FileUtil.file(qrCodePath);
        File qrCodeBgFile = FileUtil.file(qrCodeBgPath);
        // 设置文字
        pressText(bai, qrCodeBgFile, user);
        QrCodeUtil.generate(
                qrCodeUrl, //二维码内容
                QrConfig.create().setImg(logoPath).setWidth(200).setHeight(200).setQrVersion(7), //附带logo
                qrCodeFile//写出到的文件
        );
        ImgUtil.pressImage(
                qrCodeBgFile,
                qrCodeFile,
                ImgUtil.read(qrCodeFile), //水印图片
                -110, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                0, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                1f
        );

        return FileUtil.readBytes(qrCodeFile);
    }

    private static void pressText(File bai, File qrCodeBgFile, QrCodeUser user) {
        Font font = new Font("黑体", Font.BOLD, 25);
        int x = 340;
        int y = 59;
        ImgUtil.write(Img.from(bai)
                .setPositionBaseCentre(false)
                .pressText(user.getName(), Color.BLACK,
                        font,
                        x, y, 1f)
                .getImg(), qrCodeBgFile);

        ImgUtil.write(Img.from(qrCodeBgFile)
                .setPositionBaseCentre(false)
                .pressText(user.getBloodType(), Color.BLACK,
                        font,
                        x, y + 55, 1f)
                .getImg(), qrCodeBgFile);

        ImgUtil.write(Img.from(qrCodeBgFile)
                .setPositionBaseCentre(false)
                .pressText(user.getPost(), Color.BLACK,
                        font,
                        x, y + 117, 1f)
                .getImg(), qrCodeBgFile);
    }

    /**
     * 删除临时文件
     */
    public static void deleteTempFiles() {
        if (CollectionUtils.isEmpty(TEMP_FILES_PATH)) {
            return;
        }
        TEMP_FILES_PATH.parallelStream().forEach(FileUtil::del);
        TEMP_FILES_PATH.clear();
    }

    /**
     * 限制字符串长度，多余的截取，不够的补充空格
     *
     * @param str
     * @param limit
     * @return
     */
    public static String limitStr(String str, int limit) {
        String fillStr = " ";
        int x = 0;
        if (StrUtil.isNotEmpty(str)) {
            x = str.length();
            if (x > limit) {
                return str.substring(0, limit);
            }
        }
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < limit - x; i++) {
            sb.append(fillStr);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String logoPath = PathUtil.getResources("yingluolifeng.png", UserQrCodeUtil.class);

        UserQrCodeUtil.setLogoPath(logoPath);

        String qrCodeUrl = "https://minwk.top/";
        UserQrCodeUtil.setTempPath("E:\\test\\img");

        UserQrCodeUtil.createByQrCodeUrl(qrCodeUrl, new QrCodeUser("李四", "A型", "监工李四和"));
    }

    public static void setLogoPath(String logoPath) {
        LOGO_PATH = logoPath;
    }

    public static void setTempPath(String tempPath) {
        TEMP_PATH = tempPath;
    }

    public static void setBgImgPath(String bgImgPath) {
        BG_IMG_PATH = bgImgPath;
    }
}