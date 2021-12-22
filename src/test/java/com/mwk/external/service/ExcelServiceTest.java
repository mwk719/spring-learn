package com.mwk.external.service;

import com.ibiz.excel.picture.support.model.Workbook;
import com.ibiz.excel.picture.support.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author MinWeikai
 * @date 2021/12/16 14:33
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelServiceTest {

    @Autowired
    private ExcelService excelService;

    @Test
    public void export1_0_4Test() {
        Workbook workBook = excelService.export1_0_4(5);
        WebUtil.writeExcelTest(workBook, "注解导出图片示例1_0_4".concat(String.valueOf(System.currentTimeMillis())).concat(".xlsx"),
                ExcelService.TEMP_PATH);
    }

    @Test
    public void export1_1_0Test() {
        Workbook workBook = excelService.export1_1_0(201);
        WebUtil.writeExcelTest(workBook, "注解导出图片示例1_1_0".concat(String.valueOf(System.currentTimeMillis())).concat(".xlsx"),
                ExcelService.TEMP_PATH);
    }
}