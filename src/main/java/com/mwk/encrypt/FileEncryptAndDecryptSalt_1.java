package com.mwk.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 加密解密原理
 * <p>
 * 加密：
 * 获取文件的字节码然后对其进行加盐运算，将运算后的字节码信息重新生成后缀为.mwk的文件
 * 可称其为加密文件。
 * 解密：
 * 读取加密文件中的字节码内容，将其转为字节码流，进行去盐运算，然后输出为解密文件。
 * <p>
 * 加密后的文件不建议用文本工具打开，因为可能很大。
 * <p>
 * 问题：
 * 因为读取文件方法使用的是FileInputStream获取文件字节码流的方式，所以不建议特别大的文件；
 * 在文件读取过程中会获取文件的字节数组存在内存中，文件过大获取到的数组也会很大，
 * 容易造成内存溢出，而且读取字节数组的时间也会增长。
 *
 * @author MinWeikai
 * @date 2020-06-24 18:31:00
 */
public class FileEncryptAndDecryptSalt_1 {

	/**
	 * 用来和字节码相加
	 */
	private final static byte SALT = 1;

	public static void main(String[] args) {
		//原文件
		File src = new File("E:\\test\\fiddler5.zip");
		//目标文件
		File dest = new File("E:\\test\\阿里巴巴Java开发.pdf.mwk");

		long start = System.currentTimeMillis();
		//加密
		encryptFile(src);
		System.out.println("加密时间：" + (System.currentTimeMillis() - start));

		//生成解byte文件
//		decryptFile(dest);
//		System.out.println("解密时间：" + (System.currentTimeMillis() - start));
	}

	/**
	 * 获取加密的文件名称
	 *
	 * @param fileName
	 * @return
	 */
	private static String getEncryptFileName(String fileName) {
		return fileName + ".mwk";
	}

	/**
	 * 获取解密文件名称
	 *
	 * @param fileName
	 * @return
	 */
	private static String getDecryptFileName(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	/**
	 * 解密文件
	 *
	 * @param encryptFile 加密文件
	 */
	private static void decryptFile(File encryptFile) {
		try {
			byte[] bytes = bytesSubSalt(readBytes(encryptFile));
			String decryptFile = encryptFile.getParent() + File.separator + getDecryptFileName(encryptFile.getName());
			writeBytes(bytes, new File(decryptFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成加密文件
	 *
	 * @param src
	 */
	private static void encryptFile(File src) {
		try {
			byte[] bytes = bytesAddSalt(readBytes(src));
			String encryptFilePath = src.getParent() + File.separator + getEncryptFileName(src.getName());
			writeBytes(bytes, new File(encryptFilePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 字节码数组加盐
	 *
	 * @param bytes
	 * @return
	 */
	private static byte[] bytesAddSalt(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) (bytes[i] + SALT);
		}
		return bytes;
	}

	/**
	 * 字节码数组去盐
	 *
	 * @param bytes
	 * @return
	 */
	private static byte[] bytesSubSalt(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) (bytes[i] - SALT);
		}
		return bytes;
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
