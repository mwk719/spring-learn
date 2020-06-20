package com.mwk.encrypt;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MinWeikai
 * @date 2020/6/20 10:45
 */
public class File_1 {

	public static void main(String[] args) throws FileNotFoundException {
		//源文件
		File src = new File("E:\\test\\商品提点.xlsx");
		//byte文件
		String byteFile = "E:\\test\\1.byte";
		//解byte文件
		String decryptFile = "E:\\test\\1.decrypt";

		writeEncryptFile(src, byteFile);

		//读取byte文件
//		File byteFile_1 = new File(byteFile);
//		List<String> byteString_1 = FileUtil.readUtf8Lines(byteFile_1);;
//		System.out.println(byteString_1.size());

		//生成解byte文件
//		File byteFile_1 = new File(byteFile);
//		String byteString_1 = FileUtil.readUtf8String(byteFile_1);
//		FileUtil.touch(new File(decryptFile));
//		FileUtil.writeBytes(byteStrToBytes(byteString_1), decryptFile);

	}

	private static void writeEncryptFile(File src, String byteFile) throws FileNotFoundException {
		//生成byte文件
		byte[] bytes = readBytes(src);
		List<String> contents = new ArrayList<>(2);
		String byteString = Arrays.toString(bytes);
		contents.add("名字.txt");
		contents.add(byteString);
		PrintWriter writer = new PrintWriter(new File(byteFile));
		contents.forEach(writer::println);
		writer.flush();
		writer.close();
		//FileUtil.writeLines(contents, byteFile, CharsetUtil.UTF_8);
	}

	private static byte[] readBytes(File file) throws IORuntimeException {
		long len = file.length();
		if (len >= 2147483647L) {
			throw new IORuntimeException("File is larger then max array size");
		} else {
			byte[] bytes = new byte[(int) len];
			FileInputStream in = null;

			try {
				in = new FileInputStream(file);
				int readLength = in.read(bytes);
				if ((long) readLength < len) {
					throw new IOException(StrUtil.format("File length is [{}] but read [{}]!", len, readLength));
				}
			} catch (Exception var10) {
				throw new IORuntimeException(var10);
			} finally {
				IoUtil.close(in);
			}

			return bytes;
		}
	}


	/**
	 * byte数组字符串按原内容转byte数组
	 */
//	private static byte[] byteStrToBytes(String byteStr) {
//		String[] strings = byteStr.replace("[", "")
//				.replace("]", "")
//				.trim()
//				.split(", ");
//		byte[] bytes = new byte[strings.length];
//		for (int i = 0; i < strings.length; i++) {
//			bytes[i] = Integer.valueOf(strings[i]).byteValue();
//		}
//		return bytes;
//	}

}
