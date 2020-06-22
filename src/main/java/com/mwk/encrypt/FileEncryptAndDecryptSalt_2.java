package com.mwk.encrypt;

import java.io.*;

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
 * 因为读取文件方法使用的是获取文件字节码流的方式，所以不建议特别大的文件，
 *
 * @author MinWeikai
 * @date 2020/6/22 20:45
 */
public class FileEncryptAndDecryptSalt_2 {

	/**
	 * 用来和字节码相加
	 */
	private final static byte SALT = 6;

	public static void main(String[] args) {
		//源文件
		File src = new File("E:\\test\\阿里巴巴Java开发.pdf");
		//加密文件
		File encrypt = new File("E:\\test\\阿里巴巴Java开发.pdf.mwk");

		long start = System.currentTimeMillis();
		//加密
		writeEncryptFile(src);
		System.out.println("加密时间：" + (System.currentTimeMillis() - start));

		//生成解byte文件
		decryptFile(encrypt);
		System.out.println("解密时间：" + (System.currentTimeMillis() - start));
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
			write(bytes, new File(decryptFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成加密文件
	 *
	 * @param src
	 */
	private static void writeEncryptFile(File src) {
		try {
			byte[] bytes = bytesAddSalt(readBytes(src));
			String encryptFilePath = src.getParent() + File.separator + getEncryptFileName(src.getName());
			write(bytes, new File(encryptFilePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据字节流生成文件
	 *
	 * @param data
	 * @param dest
	 */
	private static void write(byte[] data, File dest) {
		try (FileOutputStream out = new FileOutputStream(dest)) {
			out.write(data);
			out.flush();
		} catch (IOException e) {
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
	 * 获取文件的byte数组
	 *
	 * @param file
	 * @return
	 */
	private static byte[] readBytes(File file) {
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
			byte[] flush = new byte[(int) file.length()];
			while (bis.read(flush) != -1) {
				return flush;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

}
