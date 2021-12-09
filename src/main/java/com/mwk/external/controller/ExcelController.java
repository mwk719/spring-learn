package com.mwk.external.controller;

import com.ibiz.excel.picture.support.model.Sheet;
import com.ibiz.excel.picture.support.model.Workbook;
import com.ibiz.excel.picture.support.util.WebUtil;
import com.mwk.entity.UserPicture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * excel操作接口
 *
 * @author MinWeikai
 * @date 2021/12/9 13:54
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    static final String CURRENT_PATH = "E:\\test\\";
    private static final String TEMP_PATH = CURRENT_PATH + "excel\\";
    private final static String IMG_PATH = "E:\\test\\img\\";

    private final static String IMG_PATH_1 = IMG_PATH + "1.jpg";
    private final static String IMG_PATH_2 = IMG_PATH + "2.jpg";

    /**
     * 使用接口下载excel示例
     * http://localhost:8080/excel/export
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        Workbook workBook = Workbook.getInstance();
        Sheet sheet = workBook.createSheet("测试");
        UserPicture u1;
        for (int r = 0; r < 5; r++) {
            u1 = new UserPicture();
            u1.setAge(15);
            u1.setName("测试-" + r);
            u1.setPicture(IMG_PATH_1);
            u1.setHeaderPicture(IMG_PATH_2);
            sheet.createRow(u1);
        }
        WebUtil.writeExcel(workBook, "注解导出图片示例".concat(String.valueOf(System.currentTimeMillis())).concat(".xlsx"), response);
    }
}
