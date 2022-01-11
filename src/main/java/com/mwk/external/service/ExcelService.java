package com.mwk.external.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.alibaba.excel.util.ListUtils;
import com.ibiz.excel.picture.support.model.CellStyle;
import com.ibiz.excel.picture.support.model.Sheet;
import com.ibiz.excel.picture.support.model.Workbook;
import com.mwk.entity.ImageDemoData;
import com.mwk.entity.UserPicture;
import com.mwk.external.controller.ExcelController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author MinWeikai
 * @date 2021/12/16 14:28
 */
@Service
public class ExcelService extends BaseExcelService {
    private static final Logger log = LoggerFactory.getLogger(ExcelController.class);

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

    public Workbook export2_0_0(int row) {
        TimeInterval timer = DateUtil.timer();
        Workbook workBook = Workbook.getInstance();
        Sheet sheet = workBook.createSheet("测试");
        UserPicture userPicture;
        for (int r = 0; r < row; r++) {
            userPicture = new UserPicture();
            userPicture.setAge(15);
            userPicture.setName("测试-" + r);
            userPicture.setPicture(IMG_PATH_1);
            // 根据图片数组和要获取图片的数量，随机从图片数组中取出若干
            userPicture.setPictures(getPictures(new Random().nextInt(10)));
            sheet.createRow(userPicture);
            if(r == 0){
                // 给标题行加上背景色，加颜色时，会对字体加粗
                sheet.getRow(r).setCellStyle(new CellStyle("9AC0CD"));
            }
        }
        log.info("file download cost time : {} 毫秒", timer.interval());
        return workBook;
    }

    public Workbook export2_1_0(int row) {
        // 测试使用占用最小内存 1
        Workbook workBook = Workbook.getInstance(1);
        Sheet sheet = workBook.createSheet("测试");
        // 给标题行加上背景色，加颜色时，会对字体加粗
        sheet.addCellStyle(new CellStyle(0, "66cc66"));
        UserPicture userPicture;
        for (int r = 0; r < row; r++) {
            userPicture = new UserPicture();
            userPicture.setAge(15);
            userPicture.setName("测试-" + r);
            // 导出本地单张图片
            userPicture.setPicture(IMG_PATH_1);
            // 导出url单张图片
            userPicture.setHeaderPicture(getUrl());
            // 导出本地图片集合
            userPicture.setPictures(getPictures(5));
            // 导出url图片集合
            userPicture.setUrlPictures(getUrls(5));
            sheet.createRow(userPicture);
        }
        return workBook;
    }

    public List<ImageDemoData> exportEasyExcel(int row) {
        List<ImageDemoData> list =  ListUtils.newArrayList();
        ImageDemoData imageDemoData;
        for (int i = 0; i < row; i++) {
            imageDemoData = new ImageDemoData();
            list.add(imageDemoData);
//            imageDemoData.setByteArray(FileUtils.readFileToByteArray(new File(IMG_PATH_1)));
//            imageDemoData.setFile(new File(IMG_PATH_2));
            imageDemoData.setString(IMG_PATH_3);
//            imageDemoData.setUrl(new URL(getUrl()));
        }
        return list;
    }
}
