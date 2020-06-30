package com.mwk.encrypt;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 对文件或文件夹路径进行加密或解密
 * <p>
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
public class PathEncryptAndDecrypt_1 {

	/**
	 * 缓存容量
	 */
	private static final int BUFFER_SIZE = 2 * 1024;

	/**
	 * 用来和字节码相加
	 */
	private static byte SALT = 0;

	/**
	 * 加密方法
	 */
	private final static String E = "e";

	/**
	 * 解密方法
	 */
	private final static String D = "d";

	private final static String TIPS = "Tips：jar with three parameters\n" +
			"{param1 : encrypt[" + E + "] || decrypt[" + D + "]} \n" +
			"{param2 : file path} \n" +
			"{param3 : password is number}\n" +
			"example java -jar addpwd.jar e E:\\test 1";

	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		if (!check(args)) {
			return;
		}

		File src = new File(args[1]);
		File tempZip;
		String title;
		switch (args[0]) {
			//加密
			case E:
				tempZip = toZip(src);
				encryptFile(tempZip);
				System.out.println("请牢记你的密码，忘记则文件无法解密!!");
				title = "encrypt";
				break;
			//解密
			case D:
				tempZip = decryptFile(src);
				unZip(tempZip);
				title = "decrypt";
				break;
			default:
				System.err.println(TIPS);
				return;
		}
		//删除临时压缩文件
		tempZip.deleteOnExit();
		//删除源文件及子文件
		delAllFile(src);

		System.out.println( title + " success use time：" + (System.currentTimeMillis() - start) + " ms");
	}

	private static boolean check(String[] args) {
		if (args.length != 3) {
			System.err.println(TIPS);
			return false;
		}

		try {
			SALT = Integer.valueOf(args[2]).byteValue();
		} catch (Exception e) {
			System.err.println(args[2] + " must be a number");
			return false;
		}

		File file = new File(args[1]);
		if (!file.exists()) {
			System.err.println(args[1] + " file path not exists");
			return false;
		}
		return true;
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
	 * @return
	 */
	private static File decryptFile(File encryptFile) {
		String decryptFile = encryptFile.getParent() + File.separator + getDecryptFileName(encryptFile.getName());
		File desc = new File(decryptFile);
		try {
			byte[] bytes = bytesSubSalt(readBytes(encryptFile));
			write(bytes, desc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desc;
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
	 * 这样的话就可以理解加盐运算 {@link PathEncryptAndDecrypt_1#bytesAddSalt(byte[])}
	 * 后：(byte)(127+1) = -128，还是正常byte数据所以文件可以正常生成；
	 * 去盐运算{@link PathEncryptAndDecrypt_1#bytesSubSalt(byte[])}
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
	 * 在{@link PathEncryptAndDecrypt_1#readBytes(File)}示例中使用BufferedInputStream缓冲字节流读取
	 * 执行read时，每次都会先从缓冲区进行读取，默认缓冲区大小是8192字节。操作完缓冲区数据后，会从硬盘中获取
	 * 下一部分字节流放在缓冲区中。缓冲区即是内存，总所周知操作内存比操作硬盘效率要高得多。
	 * 所以一般情况下使用BufferedInputStream & BufferedOutputStream进行操作文件，因为效率最高
	 * 但是当你操作的文件小于8192字节时，read时直接读取缓存，使用BufferedInputStream效率最高
	 * 当文件字节越来越大时，使用FileInputStream的效率就趋近于使用BufferedInputStream
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

	//----------------------------------以下文件压缩与解压方法-----------------------------------------

	/**
	 * 对文件进行压缩，压缩后的文件在当前文件的父路径下
	 *
	 * @param sourceFile 源文件或文件夹
	 * @return
	 */
	public static File toZip(File sourceFile) {
		File zipPath = new File(sourceFile.getParent() + File.separator + sourceFile.getName() + ".zip");
		toZip(sourceFile, zipPath);
		return zipPath;
	}

	/**
	 * 对文件进行压缩
	 *
	 * @param sourceFile 源文件或文件夹
	 * @param zipPath    压缩文件路径
	 */
	private static void toZip(File sourceFile, File zipPath) {
		try {
			if (zipPath.createNewFile()) {
				try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath))) {
					compress(sourceFile, zos, sourceFile.getName());
				} catch (IOException e) {
					System.err.println("压缩成ZIP异常：" + e);
				}
			}
		} catch (IOException e) {
			System.err.println("压缩文件创建异常：" + e);
		}

	}

	/**
	 * 递归压缩
	 *
	 * @param sourceFile 源文件或文件夹
	 * @param zos        压缩文件路径输出流
	 * @param name       路径名称
	 * @throws IOException
	 */
	private static void compress(File sourceFile, ZipOutputStream zos, String name) throws IOException {
		byte[] buf = new byte[BUFFER_SIZE];
		if (sourceFile.isFile()) {
			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
			zos.putNextEntry(new ZipEntry(name));
			// copy文件到zip输出流中
			try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(sourceFile))) {
				int len;
				while ((len = in.read(buf)) != -1) {
					zos.write(buf, 0, len);
				}
			}
		} else {
			File[] listFiles = sourceFile.listFiles();
			if (listFiles == null || listFiles.length == 0) {
				// 需要保留原来的文件结构时,需要对空文件夹进行处理
				// 空文件夹的处理
				zos.putNextEntry(new ZipEntry(name + "/"));
			} else {
				for (File file : listFiles) {
					compress(file, zos, name + "/" + file.getName());
				}
			}
		}
	}

	/**
	 * zip解压
	 * 解压在压缩包所在父路径
	 *
	 * @param srcFile zip源文件
	 */
	public static void unZip(File srcFile) {
		unZip(srcFile, srcFile.getParent());
	}

	/**
	 * zip解压
	 *
	 * @param srcFile     zip源文件
	 * @param destDirPath 解压后的目标文件夹
	 * @throws RuntimeException 解压失败会抛出运行时异常
	 */
	public static void unZip(File srcFile, String destDirPath) {
		// 判断源文件是否存在
		if (!srcFile.exists()) {
			System.err.println(srcFile.getPath() + "所指文件不存在");
			return;
		}
		// 开始解压
		try (ZipFile zipFile = new ZipFile(srcFile)) {
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				// 如果是文件，就先创建一个文件，然后用io流把内容copy过去
				File targetFile = new File(destDirPath + "/" + entry.getName());
				// 如果是文件夹，就创建个文件夹
				if (entry.isDirectory()) {
					if (!targetFile.mkdirs()) {
						System.err.println(targetFile + "文件创建失败");
						return;
					}
				} else {
					// 保证这个文件的父文件夹必须要存在
					if (!targetFile.getParentFile().exists()) {
						if (!targetFile.getParentFile().mkdirs()) {
							System.err.println(targetFile + "文件创建失败");
							return;
						}
					}
					if (targetFile.createNewFile()) {
						// 将压缩文件内容写入到这个文件中
						try (InputStream is = zipFile.getInputStream(entry);
						     FileOutputStream fos = new FileOutputStream(targetFile)) {
							int len;
							byte[] buf = new byte[BUFFER_SIZE];
							while ((len = is.read(buf)) != -1) {
								fos.write(buf, 0, len);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println("解压异常" + e);
		}
	}

	/**
	 * 删除文件或文件夹
	 *
	 * @param directory
	 */
	public static void delAllFile(File directory) {
		if (!directory.isDirectory()) {
			directory.delete();
		} else {
			File[] files = directory.listFiles();
			// 空文件夹
			assert files != null;
			if (files.length == 0) {
				directory.delete();
				return;
			}

			// 删除子文件夹和子文件
			for (File file : files) {
				if (file.isDirectory()) {
					delAllFile(file);
				} else {
					file.delete();
				}
			}

			// 删除文件夹本身
			directory.delete();
		}
	}

}
