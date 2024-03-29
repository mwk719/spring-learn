package com.mwk.external.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.ibiz.excel.picture.support.model.*;
import com.ibiz.excel.picture.support.processor.ExcelTableProcessor;
import com.ibiz.excel.picture.support.util.WebUtil;
import com.mwk.entity.ImageDemoData;
import com.mwk.entity.Student;
import com.mwk.entity.UserPicture;
import com.mwk.external.base.BaseExcelParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * excel操作接口
 *
 * @author MinWeikai
 * @date 2021/12/9 13:54
 */
@RestController
@RequestMapping("/excel")
public class ExcelController extends BaseExcelParam {

    private static final Logger log = LoggerFactory.getLogger(ExcelController.class);

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
        // 测试使用占用最小内存 1
        Workbook workBook = Workbook.getInstance(1);
        Sheet sheet = workBook.createSheet("测试");
        // 给标题行加上背景色，加颜色时，会对字体加粗
        sheet.addCellStyle(new CellStyle(0, "66cc66"));
        List<UserPicture> list = new ArrayList<>();
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
            list.add(userPicture);
        }
        sheet.write(UserPicture.class).createRow(list);
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
        List<UserPicture> list = new ArrayList<>();
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
            list.add(userPicture);
        }
        sheet.write(UserPicture.class).createRow(list);
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
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("EasyExcel导出图片示例", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), ImageDemoData.class).sheet("图片示例").doWrite(list);
    }


    /**
     * 使用接口下载excel示例
     * http://localhost:8080/excel/export/2_2_0/250
     * 版本支持
     * <dependency>
     * 			<groupId>top.minwk</groupId>
     * 			<artifactId>excel-x</artifactId>
     * 			<version>2.2.0</version>
     * 		</dependency>
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/export/2_2_0/{row}")
    public void export2_2_0(HttpServletResponse response, @PathVariable int row) throws IOException {
        // 测试使用占用最小内存 1
        Workbook workBook = Workbook.getInstance();
        Sheet sheet = workBook.createSheet("测试");
        // 给第一行加上背景色和字体配置
        CellStyle cellStyle = new CellStyle(0, "F0F0F0");
        Font font = Font.build()
                .setFontName("黑体")
                .setFontHeightInPoints((short) 15)
                .setColor("FF4040")
                .setBoldWeight(true);
        cellStyle.setFont(font);
        sheet.addCellStyle(cellStyle);

        // 给第三行字体放大到18
        CellStyle cellStyle1 = new CellStyle();
        Font font1 = new Font();
        font1.setFontHeightInPoints((short) 18);
        cellStyle1.setRowNumber(2);
        cellStyle1.setFont(font1);
        sheet.addCellStyle(cellStyle1);

        // 给第五行加上背景色
        sheet.addCellStyle(new CellStyle(4, "AB82FF"));

        List<UserPicture> list = new ArrayList<>();
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
            userPicture.setPictures(getPictures(new Random().nextInt(5)));
            // 导出url图片集合
            userPicture.setUrlPictures(getUrls(5));
            list.add(userPicture);
        }
        sheet.write(UserPicture.class).createRow(list);
        WebUtil.writeExcel(workBook, "ExportExample2_2_0".concat(String.valueOf(System.currentTimeMillis())).concat(".xlsx"), response);
    }

    /**
     * 动态配置表头excel导出
     * http://localhost:8080/excel/export/dynamic-config-header
     * 版本支持
     * <dependency>
     * 			<groupId>top.minwk</groupId>
     * 			<artifactId>excel-x</artifactId>
     * 			<version>2.3.0</version>
     * 		</dependency>
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/export/dynamic-config-header")
    public void exportDynamicConfigHeader(HttpServletResponse response) throws IOException {
        // 模拟需要导出的数据集合
        List<Student> students = new ArrayList<>();
        students.add(new Student("李四", 16, null, null, 0));
        students.add(new Student("张三", 17, null,
                Arrays.asList("https://portrait.gitee.com/uploads/avatars/user/552/1657608_mwk719_1641537497.png",
                "https://img2.baidu.com/it/u=2602880481,728201544&fm=26&fmt=auto"), 1));
        students.add(new Student("王五", 15, IMG_PATH_1, null, 2));

        // 配置导出excel的表头、顺序、对应导出的数据集合的字段、是否是图片、单元格宽度等
        List<BizExcelRel> excels = new ArrayList<>();
        excels.add(new BizExcelRel("姓名", "name", 2));
        excels.add(new BizExcelRel("年龄", "age", 3));
        excels.add(new BizExcelRel("表现", "performance", 4));
        excels.add(new BizExcelRel("头像", "headPicture", 5, true, 20));
        excels.add(new BizExcelRel("相册", "album", 6, true));

        // 创建excel
        Workbook workBook = Workbook.getInstance(100);
        Sheet sheet = workBook.createSheet("测试");
        // 创建样式
        CellStyle cellStyle = new CellStyle(0, "F0F0F0");
        // 创建数据字典
        Map<String, String> performanceMap = new HashMap<>(3);
        performanceMap.put("0", "一般");
        performanceMap.put("1", "良好");
        performanceMap.put("2", "优秀");

        // 构建sheet
        ExcelTableProcessor.sheet(sheet)
                // 添加样式
                .addCellStyle(cellStyle)
                // 添加对应属性字段的数据字典
                .registryEnumMap("performance", performanceMap)
                // 构建excel
                .buildExcel(excels, students);
        WebUtil.writeExcel(workBook, "ExportExampleDynamicConfigHeader".concat(String.valueOf(System.currentTimeMillis())).concat(".xlsx"), response);
    }
}
