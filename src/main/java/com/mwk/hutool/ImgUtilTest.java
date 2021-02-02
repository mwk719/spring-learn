package com.mwk.hutool;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;

/**
 * @author MinWeikai
 * @date 2020/12/23 17:17
 */
public class ImgUtilTest {

	public static void main(String[] args) {
		ImgUtil.pressImage(
				FileUtil.file("E:/test/xingyun0001.jpg"),
				FileUtil.file("E:/test/xingyun0003.jpg"),
				ImgUtil.read(FileUtil.file("E:/test/xingyun0002.jpg")), //水印图片
				10, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
				10, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
				0.1f
		);
	}
}
