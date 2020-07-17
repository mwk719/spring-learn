package com.mwk.encrypt;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 假设你已经看了我之前的两篇博客
 * [字节流运算实现文件的加密解密1.0](https://minwk.top/FileEncryptAndDecrypt1.0/)
 * [字节流运算实现文件的加密解密2.0](https://minwk.top/FileEncryptAndDecrypt2.0/)
 * 如果没看的话，可以去飞速的浏览一下，方便这篇博客的理解（我不会告诉你，我是让你帮我的博客增加点击量的）
 * 好，相信你已经点过去看了一下。
 * 我是相信你的哦！
 * 那么好，通过对前两篇博客的理解，我便可以开发出一个工具可实现对文件的深加密
 * 这就是这篇博客[字节流运算实现文件的加密解密3.0](https://minwk.top/FileEncryptAndDecrypt3.0/)
 * 要说的可制作jar工具包用来对文件或文件夹进行加密解密，此处点题。
 * 好处：
 * 1. 可对文件进行深加密
 * 2. 有效的防止别人偷窥你的文件
 * 3. 加密原理开源，可对算法进行修改；理论上只要别人不知道你的加密原理，
 * 即便知道密码，也根本无法破解
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * jar包下载
 * http://tools.minwk.top/addpwd.jar?e=1594701294&token=uu5yo0LPImJOiG5Q_yAHtG8lOpeujhf2OplcFmLk:DJTe5cpuBGKRyn__oDEypOindW8=
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * <p>
 * 加密原理
 * 1. 先对文件或文件夹进行zip压缩{@link FileEncryptAndDecryptSalt_3#toZip(File)}
 * 2. 对压缩后的文件执行加密方法{@link FileEncryptAndDecryptSalt_3#encryptFile(File)}进行加密，
 * 使用{@link FileEncryptAndDecryptSalt_3#bytesAddSalt(byte[])}密码加盐算法，
 * 最后生成后缀为{@code .mwk}的文件为加密文件；
 * 解密原理
 * 1. 对后缀为{@code .mwk}的加密文件执行{@link FileEncryptAndDecryptSalt_3#decryptFile(File)}
 * 进行解密，然后得到同名的压缩包文件。
 * 2. 对压缩包文件执行{@link FileEncryptAndDecryptSalt_3#unZip(File)}进行解压得到原文件
 *
 * @author MinWeikai
 * @date 2020-06-30 18:51:29
 * @see #SUFFIX 加密文件后缀
 * @see #SALT 用来和字节码相加盐
 * @see #E 加密方法
 * @see #D 解密方法
 * @see #SUCCESS 程序执行状态
 * @see #BUFFER_SIZE 缓存容量
 */
public class FileEncryptAndDecryptSalt_3 {

	/**
	 * 执行正常
	 * 当执行正常时最后会删除源文件，
	 * 执行异常时直接退出，不会删除源文件
	 */
	private static boolean SUCCESS = true;

	/**
	 * 用来和字节码相加
	 */
	private static byte SALT = 0;

	/**
	 * 缓存容量
	 */
	private static final int BUFFER_SIZE = 2 * 1024;

	/**
	 * 加密方法
	 */
	private final static String E = "e";

	/**
	 * 解密方法
	 */
	private final static String D = "d";

	/**
	 * 后缀
	 */
	private final static String SUFFIX = ".mwk";

	/**
	 * 提示
	 */
	private final static String TIPS = "提示：jar包后面必须跟三个参数\n" +
			"{参数1：加密[" + E + "] || 解密[" + D + "]} \n" +
			"{参数2：文件绝对路径} \n" +
			"{参数3：密码，必须是数字}\n" +
			"示例：java -jar addpwd.jar e E:/test 1";


	/**
	 * 加密解密方法执行入口，必须传3个参数
	 *
	 * @param args [0] 操作类型：加密传 e ，解密传 d
	 *             [1] 要操作文件的绝对路径
	 *             [2] 加密或解密的密码，必须是数字
	 */
	public static void main(String[] args) {
		//args = new String[]{"e", "E:/test", "250"};
		//args = new String[]{"d", "E:/test.mwk", "250"};

		//参数校验
		check(args);
		if (!SUCCESS) {
			return;
		}

		System.out.println("执行已开始，请勿随意退出......");
		long start = System.currentTimeMillis();

		File src = new File(args[1]);
		File tempZip;
		String title;
		switch (args[0]) {
			//加密
			case E:
				//压缩
				tempZip = toZip(src);
				//加密
				encryptFile(tempZip);
				title = "加密";
				break;
			//解密
			case D:
				//解密到临时文件
				tempZip = decryptFile(src);
				//对临时文件进行解压到正常文件，解压异常则删除临时文件
				//因为密码错误时生成的包可能无法解压
				unZip(tempZip);
				delAllFile(tempZip);
				title = "解密";
				break;
			default:
				errTips(TIPS);
				return;
		}

		//删除源文件及子文件，保留操作后的文件
		if (SUCCESS) {
			delAllFile(src);
			System.out.println(title + " 执行成功 用时：" + (System.currentTimeMillis() - start) + " ms");
			if(E.equals(args[0])){
				System.out.println("请牢记你的密码，忘记则文件无法解密!!");
			}
			return;
		}
		System.err.println(title + " 异常 已退出 请检查密码是否正确!!!");
	}

	private static void check(String[] args) {
		if (args.length != 3) {
			errTips(TIPS);
			return;
		}

		try {
			SALT = Integer.valueOf(args[2]).byteValue();
		} catch (Exception e) {
			errTips(args[2] + " 必须是数字");
			return;
		}

		File file = new File(args[1]);
		if (!file.exists()) {
			errTips(args[1] + " 文件路径不存在");
			return;
		}

		if (D.equals(args[0]) && !file.getName().endsWith(SUFFIX)) {
			errTips(file.getName() + " 解密文件路径必须是 ["+SUFFIX+"]");
		}
	}

	/**
	 * 错误提示，发生错误时源文件不可删除
	 *
	 * @param tips 提示
	 */
	private static void errTips(String tips) {
		SUCCESS = false;
		System.err.println(tips);
	}

	/**
	 * 解密文件
	 *
	 * @param encryptFile 加密文件
	 * @return
	 */
	private static File decryptFile(File encryptFile) {
		String decryptFile = encryptFile.getParent() + File.separator + encryptFile.getName() + SUFFIX;
		File desc = new File(decryptFile);
		try {
			byte[] bytes = bytesSubSalt(readBytes(encryptFile));
			write(bytes, desc);
		} catch (Exception e) {
			errTips("解密文件异常：" + e.getMessage());
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
			String encryptFilePath = src.getParent() + File.separator + src.getName();
			write(bytes, new File(encryptFilePath));
		} catch (Exception e) {
			errTips("加密文件异常：" + e.getMessage());
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
			errTips("根据字节流生成文件异常：" + e.getMessage());
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
	 * 这样的话就可以理解加盐运算 {@link FileEncryptAndDecryptSalt_3#bytesAddSalt(byte[])}
	 * 后：(byte)(127+1) = -128，还是正常byte数据所以文件可以正常生成；
	 * 去盐运算{@link FileEncryptAndDecryptSalt_3#bytesSubSalt(byte[])}
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
	 * 在{@link FileEncryptAndDecryptSalt_3#readBytes(File)}示例中使用BufferedInputStream缓冲字节流读取
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
			errTips("获取字节流异常：" + e.getMessage());
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
		File zipPath = new File(sourceFile.getParent() + File.separator + sourceFile.getName() + SUFFIX);
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
					errTips("压缩成ZIP异常：" + e.getMessage());
				}
			}
		} catch (IOException e) {
			errTips("压缩文件创建异常：" + e.getMessage());
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
			} catch (Exception e) {
				errTips("压缩文件获取流异常：" + e.getMessage());
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
			errTips(srcFile.getPath() + "所指文件不存在");
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
						errTips(targetFile + "文件创建失败");
						return;
					}
				} else {
					// 保证这个文件的父文件夹必须要存在
					if (!targetFile.getParentFile().exists()) {
						if (!targetFile.getParentFile().mkdirs()) {
							errTips(targetFile + "文件创建失败");
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
			errTips("解压异常" + e.getMessage());
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
