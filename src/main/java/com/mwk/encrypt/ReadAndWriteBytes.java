package com.mwk.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * byte的读取与输出
 *
 * @author MinWeikai
 * @date 2020-06-24 17:32:28
 */
public class ReadAndWriteBytes {

	public static void main(String[] args) {
		//原文件
		File src = new File("E:\\test\\1.txt");
		//目标文件
		File dest = new File("E:\\test\\2.txt");
		//原文件的字节数组
		byte[] srcBytes = readBytes(src);
		System.out.println("原文件字节数组：" + Arrays.toString(srcBytes));
		//根据原字节数组生成目标文件，实现文件的复制
		writeBytes(srcBytes, dest);
	}

	/**
	 * 根据字节流生成文件
	 *
	 * @param data
	 * @param dest
	 */
	static void writeBytes(byte[] data, File dest) {
		try (FileOutputStream out = new FileOutputStream(dest)) {
			out.write(data);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static byte[] readBytes(File file) {
		long len = file.length();
		byte[] bytes = new byte[(int) len];
		try (FileInputStream in = new FileInputStream(file)) {
			int readLength = in.read(bytes);
			if ((long) readLength < len) {
				throw new IOException("文件读取失败");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}
}
