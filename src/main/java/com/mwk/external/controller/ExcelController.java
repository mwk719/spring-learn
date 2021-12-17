package com.mwk.external.controller;

import com.ibiz.excel.picture.support.model.Workbook;
import com.ibiz.excel.picture.support.util.WebUtil;
import com.mwk.external.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private ExcelService excelService;

    /**
     * 使用接口下载excel示例
     * http://localhost:8080/excel/export/1_0_4/5
     * 版本支持
     * <dependency>
     * 			<groupId>top.minwk</groupId>
     * 			<artifactId>excel-x</artifactId>
     * 			<version>1.0.4</version>
     * 		</dependency>
     * @param response
     * @throws IOException
     */
    @GetMapping("/export/1_0_4/{row}")
    public void export1_0_4(HttpServletResponse response, @PathVariable int row) throws IOException {
        Workbook workBook = excelService.export1_0_4(row);
        WebUtil.writeExcel(workBook, "注解导出图片示例1_0_4".concat(String.valueOf(System.currentTimeMillis())).concat(".xlsx"), response);
    }

    /**
     * 使用接口下载excel示例
     * http://localhost:8080/excel/export/1_1_0/5
     * 版本支持
     * <dependency>
     * 			<groupId>top.minwk</groupId>
     * 			<artifactId>excel-x</artifactId>
     * 			<version>1.1.0</version>
     * 		</dependency>
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/export/1_1_0/{row}")
    public void export1_1_0(HttpServletResponse response, @PathVariable int row) throws IOException {
        Workbook workBook = excelService.export1_1_0(row);
        WebUtil.writeExcel(workBook, "注解导出图片示例1_1_0".concat(String.valueOf(System.currentTimeMillis())).concat(".xlsx"), response);
    }
}
