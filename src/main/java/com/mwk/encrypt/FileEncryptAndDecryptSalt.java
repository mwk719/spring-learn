//package com.mwk.encrypt;
//
//import cn.hutool.core.util.StrUtil;
//import org.springframework.util.CollectionUtils;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * 加密解密原理
// * <p>
// * 加密：
// * 获取文件的字节码然后对其进行加盐运算，将运算后的字节码信息生成在一个文件名同原名称后缀为.mwk的文件中。
// * 该文件包含两行：第一行原文件名和后缀，第二行为原文件的字节码；
// * 可称其为加密文件。
// * 解密：
// * 读取加密文件中的字节码内容，将其转为字节码流，然后输出为解密文件。
// * <p>
// * 解密后的文件不建议用文本工具打开，因为可能很大。
// * <p>
// * 问题：
// * 因为读取文件方法使用的是获取文件字节码数组的方式，所以
// *
// * @author MinWeikai
// * @date 2020/6/20 10:45
// */
//public class FileEncryptAndDecryptSalt {
//
//	/**
//	 * 用来和字节码相加
//	 */
//	private final static byte SALT = 6;
//
//	public static void main(String[] args) {
//		//源文件
//		File src = new File("E:\\test\\1.txt");
//		//byte文件
//		String encryptFilePath = "E:\\test\\1.mwk";
//
//		long start = System.currentTimeMillis();
//		//加密
//		writeEncryptFile(src);
//		System.out.println("加密时间：" + (System.currentTimeMillis() - start));
//
//		//生成解byte文件
////		decryptFile(encryptFilePath);
////		System.out.println("解密时间：" + (System.currentTimeMillis() - start));
//	}
//
//	/**
//	 * 获取加密的文件名称
//	 *
//	 * @param fileName
//	 * @return
//	 */
//	private static String getEncryptFileName(String fileName) {
//		int index = fileName.lastIndexOf(".");
//		return fileName.substring(0, index) + ".mwk";
//	}
//
//	/**
//	 * 解密文件
//	 *
//	 * @param encryptFilePath 加密文件路径
//	 */
//	private static void decryptFile(String encryptFilePath) {
//		File encryptFile = new File(encryptFilePath);
//		List<String> contents = readLines(encryptFile);
//		if (CollectionUtils.isEmpty(contents)) {
//			return;
//		}
//		int nameLine = 0;
//		int byteLine = 1;
//		if (contents.size() < 2) {
//			byteLine = 0;
//		}
//		File decryptFile = new File(encryptFile.getParent() + File.separator + contents.get(nameLine));
//		try {
//			if (decryptFile.createNewFile()) {
//				write(byteStrToBytes(contents.get(byteLine)), decryptFile);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 根据字节流生成文件
//	 *
//	 * @param data
//	 * @param dest
//	 */
//	private static void write(byte[] data, File dest) {
//		try (FileOutputStream out = new FileOutputStream(dest)) {
//			out.write(data);
//			out.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 按行读取文件内容
//	 *
//	 * @param src
//	 * @return
//	 */
//	private static List<String> readLines(File src) {
//		List<String> collection = new ArrayList<>(2);
//		try (BufferedReader reader = getReader(new FileInputStream(src))) {
//			reader.lines().forEach(collection::add);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return collection;
//	}
//
//	private static BufferedReader getReader(InputStream in) {
//		if (null == in) {
//			return null;
//		} else {
//			return new BufferedReader(new InputStreamReader(in));
//		}
//	}
//
//	/**
//	 * 生成加密文件
//	 *
//	 * @param src
//	 */
//	private static void writeEncryptFile(File src) {
//		byte[] bytes = new byte[0];
//		try {
//			bytes = bytesSalt(readBytes(src));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		List<String> contents = new ArrayList<>(2);
//		String byteString = Arrays.toString(bytes);
//		contents.add(src.getName());
//		contents.add(byteString);
//		String encryptFilePath = src.getParent() + File.separator + getEncryptFileName(src.getName());
//		try (PrintWriter writer = new PrintWriter(encryptFilePath)) {
//			contents.forEach(writer::println);
//			writer.flush();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 字节码数组加盐
//	 *
//	 * @param bytes
//	 * @return
//	 */
//	private static byte[] bytesSalt(byte[] bytes) {
//		for (int i = 0; i < bytes.length; i++) {
//			bytes[i] = (byte) (bytes[i] + SALT);
//		}
//		return bytes;
//	}
//
//	/**
//	 * 获取文件的byte数组
//	 *
//	 * @param file
//	 * @return
//	 */
//	private static byte[] readBytes(File file) throws Exception {
//		long len = file.length();
//		long max = 2147483647L;
//		if (len >= max) {
//			throw new IOException("File is larger then max array size");
//		} else {
//			byte[] bytes = new byte[(int) len];
//			try (FileInputStream in = new FileInputStream(file)) {
//				int readLength = in.read(bytes);
//				if ((long) readLength < len) {
//					throw new IOException(StrUtil.format("File length is [{}] but read [{}]!", len, readLength));
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return bytes;
//		}
//	}
//
//
//	/**
//	 * byte数组字符串按原内容转byte数组
//	 *
//	 * @param byteStr
//	 * @return
//	 */
//	private static byte[] byteStrToBytes(String byteStr) {
//		String[] strings = byteStr.replace("[", "")
//				.replace("]", "")
//				.trim()
//				.split(", ");
//		byte[] bytes = new byte[strings.length];
//		for (int i = 0; i < strings.length; i++) {
//			bytes[i] = (byte) (Integer.valueOf(strings[i]) - SALT);
//		}
//		return bytes;
//	}
//
//}
