package com.mwk.external.controller;

import com.alibaba.excel.EasyExcel;
import com.ibiz.excel.picture.support.model.CellStyle;
import com.ibiz.excel.picture.support.model.Sheet;
import com.ibiz.excel.picture.support.model.Workbook;
import com.ibiz.excel.picture.support.util.WebUtil;
import com.mwk.entity.ImageDemoData;
import com.mwk.entity.UserPicture;
import com.mwk.external.service.BaseExcelService;
import com.mwk.external.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * excel操作接口
 *
 * @author MinWeikai
 * @date 2021/12/9 13:54
 */
@RestController
@RequestMapping("/excel")
public class ExcelController extends BaseExcelService {

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
     * http://localhost:8080/excel/export/2_0_0/205
     * 版本支持
     * <dependency>
     * 			<groupId>top.minwk</groupId>
     * 			<artifactId>excel-x</artifactId>
     * 			<version>2.0.0</version>
     * 		</dependency>
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/export/2_0_0/{row}")
    public void export2_0_0(HttpServletResponse response, @PathVariable int row) throws IOException {
        Workbook workBook = excelService.export2_0_0(row);
        WebUtil.writeExcel(workBook, "注解导出图片示例2_0_0".concat(String.valueOf(System.currentTimeMillis())).concat(".xlsx"), response);
    }

    /**
     * 使用接口下载excel示例
     * http://localhost:8080/excel/export/2_1_0/250
     * 版本支持
     * <dependency>
     * 			<groupId>top.minwk</groupId>
     * 			<artifactId>excel-x</artifactId>
     * 			<version>2.1.0</version>
     * 		</dependency>
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/export/2_1_0/{row}")
    public void export2_1_0(HttpServletResponse response, @PathVariable int row) throws IOException {
        Workbook workBook = excelService.export2_1_0(row);
        WebUtil.writeExcel(workBook, "注解导出图片示例2_1_0".concat(String.valueOf(System.currentTimeMillis())).concat(".xlsx"), response);
    }


    /**
     * 最新使用示例代码导出excel示例
     * http://localhost:8080/excel/export/lastversion/250
     * 最新版本地址 https://search.maven.org/artifact/top.minwk/excel-x
     * <dependency>
     * 			<groupId>top.minwk</groupId>
     * 			<artifactId>excel-x</artifactId>
     * 			<version>${excel-x.version}</version>
     * 		</dependency>
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/export/lastversion/{row}")
    public void exportLastVersion(HttpServletResponse response, @PathVariable int row) throws IOException {
        /*
        操作窗口
        当写入excel数据行数大于flushSize时{@link Sheet.SheetHandler#createRow(int)},
        会刷新数据到流,调用该方法
        {@link  com.ibiz.excel.picture.support.flush.DrawingXmlRelsHandler#copyPictureAppendDrawingRelsXML(Sheet, Picture)}
        将图片刷新在磁盘中
        不会占用内存空间
        flushSize = -1 时不刷新流
        */
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
            userPicture.setPicture("E:\\test\\img\\1.jpg");
            // 导出url单张图片
            userPicture.setHeaderPicture("https://portrait.gitee.com/uploads/avatars/user/552/1657608_mwk719_1641537497.png");
            // 导出本地图片集合
            userPicture.setPictures(Arrays.asList("E:\\test\\img\\1.jpg","E:\\test\\img\\2.jpg"));
            // 导出url图片集合
            userPicture.setUrlPictures(Arrays.asList("https://portrait.gitee.com/uploads/avatars/user/552/1657608_mwk719_1641537497.png",
                    "https://img2.baidu.com/it/u=2602880481,728201544&fm=26&fmt=auto"));
            sheet.createRow(userPicture);
        }
        WebUtil.writeExcel(workBook, "最新使用示例代码导出".concat(String.valueOf(System.currentTimeMillis())).concat(".xlsx"), response);
    }

    /**
     * 使用easyexcel下载excel示例,作为对照测试学习
     * http://localhost:8080/excel/export/easyexcel/250
     * 版本支持
     * 		<dependency>
     * 			<groupId>com.alibaba</groupId>
     * 			<artifactId>easyexcel</artifactId>
     * 			<version>3.0.5</version>
     * 		</dependency>
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/export/easyexcel/{row}")
    public void exportEasyExcel(HttpServletResponse response, @PathVariable int row) throws IOException {
        List<ImageDemoData> list = excelService.exportEasyExcel(row);
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("EasyExcel导出图片示例", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), ImageDemoData.class).sheet("图片示例").doWrite(list);
    }
}
