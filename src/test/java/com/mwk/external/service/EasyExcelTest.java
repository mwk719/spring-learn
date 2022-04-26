package com.mwk.external.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.mwk.entity.ImageDemoData;
import com.mwk.external.base.BaseExcelParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2022/1/11 13:48
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class EasyExcelTest extends BaseExcelParam {

    /**
     * 图片导出
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ImageDemoData}
     * <p>
     * 2. 直接写即可
     */
    @Test
    public void imageWrite() throws Exception {
        String fileName = TEMP_PATH.concat("EasyExcel_导出excel示例").concat(String.valueOf(System.currentTimeMillis())).concat(".xlsx");
        List<ImageDemoData> list =  ListUtils.newArrayList();
//        ImageDemoData imageDemoData;
//        for (int i = 0; i < 100; i++) {
//            imageDemoData = new ImageDemoData();
//            list.add(imageDemoData);
//            imageDemoData.setByteArray(FileUtils.readFileToByteArray(new File(IMG_PATH_1)));
//            imageDemoData.setFile(new File(IMG_PATH_2));
//            imageDemoData.setString(IMG_PATH_3);
//            imageDemoData.setUrl(new URL(getUrl()));
//        }
        // 写入数据
        EasyExcel.write(fileName, ImageDemoData.class).sheet().doWrite(list);
    }
}
