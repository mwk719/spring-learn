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
		File src = new File("E:\\test\\阿里巴巴Java开发.zip");
		//加密文件
		File encrypt = new File("E:\\test\\阿里巴巴Java开发.pdf.mwk");

		long start = System.currentTimeMillis();
		//加密
		encryptFile(src);
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
	private static void encryptFile(File src) {
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
	 * bytes[i] = (byte) (bytes[i] + SALT);
	 * 在含byte类型数据运算时，Java编译器会自动转换byte类型为int
	 * 这里加盐运算(bytes[i] + SALT)会转换为两个int类型相加，然后强转为byte接收
	 * 因为byte的取值范围为-128~127，可以将其类比为一个时钟：
	 * 从-128开始到127结束，然后又从-128开始
	 * 比如：(byte)(127+1) = -128
	 * 这样的话就可以理解加盐运算 {@link FileEncryptAndDecryptSalt_2#bytesAddSalt(byte[])}
	 * 后：(byte)(127+1) = -128，还是正常byte数据所以文件可以正常生成；
	 * 去盐运算{@link FileEncryptAndDecryptSalt_2#bytesSubSalt(byte[])}
	 * 后：(byte)(-128-1) = 127，恢复为原来的byte，这样就可以生成原有文件
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
	 * 在{@link FileEncryptAndDecryptSalt_1#readBytes(File)}示例中使用FileInputStream字节流读取
	 * 执行read时，每次都从硬盘中读取文件字节。
	 * 在{@link FileEncryptAndDecryptSalt_2#readBytes(File)}示例中使用BufferedInputStream缓冲字节流读取
	 * 执行read时，每次都会先从缓冲区进行读取，默认缓冲区大小是8192字节。操作完缓冲区数据后，会从硬盘中获取
	 * 下一部分字节流放在缓冲区中。缓冲区即是内存，总所周知操作内存比操作硬盘效率要高得多。
	 * 所以一般情况下使用BufferedInputStream & BufferedOutputStream进行操作文件，因为效率最高
	 * 但是当你操作的文件小于8192字节时，read时直接读取缓存，使用BufferedInputStream效率最高
	 * 当文件字节越来越大时，使用FileInputStream的效率就趋近于使用BufferedInputStream
	 * <p>
	 * 文件过大时容易造成内存溢出，最好的方式还是边读边写，内存里不要留过多的数据
	 *
	 * @param file
	 * @return
	 * @see BufferedInputStream 缓冲输入流
	 * @see FileInputStream 缓冲流
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
