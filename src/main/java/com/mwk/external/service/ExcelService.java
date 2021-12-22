package com.mwk.external.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.FileUtil;
import com.ibiz.excel.picture.support.model.CellStyle;
import com.ibiz.excel.picture.support.model.Sheet;
import com.ibiz.excel.picture.support.model.Workbook;
import com.mwk.entity.UserPicture;
import com.mwk.external.controller.ExcelController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author MinWeikai
 * @date 2021/12/16 14:28
 */
@Service
public class ExcelService {
    private static final Logger log = LoggerFactory.getLogger(ExcelController.class);

    public static final String CURRENT_PATH = "E:\\test\\";
    public static final String TEMP_PATH = CURRENT_PATH + "excel\\";
    public static final String IMG_PATH = "E:\\test\\img\\";

    public final static String IMG_PATH_1 = IMG_PATH + "1.jpg";
    public final static String IMG_PATH_2 = IMG_PATH + "2.jpg";

    private final static String IMAGES_PATH = CURRENT_PATH + "images\\";

    public Workbook export1_0_4(int row) {
        Workbook workBook = Workbook.getInstance();
        Sheet sheet = workBook.createSheet("测试");
        UserPicture u1;
        for (int r = 0; r < row; r++) {
            u1 = new UserPicture();
            u1.setAge(15);
            u1.setName("测试-" + r);
            u1.setPicture(IMG_PATH_1);
            u1.setHeaderPicture(IMG_PATH_2);
            sheet.createRow(u1);
        }
        return workBook;
    }

    public Workbook export1_1_0(int row) {
        TimeInterval timer = DateUtil.timer();
        Workbook workBook = Workbook.getInstance();
        Sheet sheet = workBook.createSheet("测试");
        // 给标题行加上背景色，加颜色时，会对字体加粗
        CellStyle cellStyle = workBook.createCellStyle();
        cellStyle.setFgColorRgb("9AC0CD");
        // 图片数组
        File[] files = FileUtil.ls(IMAGES_PATH);
        UserPicture userPicture;
        for (int r = 0; r < row; r++) {
            userPicture = new UserPicture();
            userPicture.setAge(15);
            userPicture.setName("测试-" + r);
            userPicture.setPicture(IMG_PATH_1);
            // 根据图片数组和要获取图片的数量，随机从图片数组中取出若干
            userPicture.setPictures(getPictures(files, 9));
            sheet.createRow(userPicture);
            if(r == 0){
                sheet.getRow(r).setCellStyle(cellStyle);
            }
        }
        log.info("file download cost time : {} 毫秒", timer.interval());
        return workBook;
    }

    /**
     * 根据图片数组和要获取图片的数量，随机从图片数组中取出若干
     * @param files 图片数组
     * @param getCount 获取图片的数量
     * @return
     */
    private static List<String> getPictures(File[] files, int getCount) {
        List<String> list = new ArrayList<>(getCount);
        for (int i = 0; i < getCount; i++) {
            int index = new Random().nextInt(files.length);
            list.add(files[index].getAbsolutePath());
        }
        return list;
    }

    public static void main(String[] args) {
        File[] files = FileUtil.ls(IMAGES_PATH);
        List<String> images = getPictures(files, 9);
        System.out.println(images);
    }

}
