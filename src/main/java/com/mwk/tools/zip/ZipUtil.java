package com.mwk.tools.zip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 可对文件夹或文件进行压缩
 * 当文件夹包含子文件夹时会递归原路径压缩
 *
 * @author MinWeikai
 * @date 2020-06-30 15:22:00
 */
public class ZipUtil {
	private static final int BUFFER_SIZE = 2 * 1024;
	private static final Logger log = LoggerFactory.getLogger(ZipUtil.class);

	public static void main(String[] args) {
		File src = new File("E:\\test\\阿里巴巴Java开发.pdf");
		//ZipUtil.toZip(src);

		unZip(new File("E:\\test\\阿里巴巴Java开发.pdf.mwk"));
	}

	/**
	 * 对文件进行压缩，压缩后的文件在当前文件的父路径下
	 *
	 * @param sourceFile 源文件或文件夹
	 */
	public static void toZip(File sourceFile) {
		File zipPath = new File(sourceFile.getParent() + File.separator + sourceFile.getName() + ".mwk");
		toZip(sourceFile, zipPath);
	}

	/**
	 * 对文件进行压缩
	 *
	 * @param sourceFile 源文件或文件夹
	 * @param zipPath    压缩文件路径
	 */
	private static void toZip(File sourceFile, File zipPath) {
		long start = System.currentTimeMillis();
		try {
			if (zipPath.createNewFile()) {
				try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath))) {
					compress(sourceFile, zos, sourceFile.getName());
					long end = System.currentTimeMillis();
					log.debug("压缩完成，耗时：" + (end - start) + " ms");
				} catch (IOException e) {
					log.error("压缩成ZIP异常：", e);
				}
			}
		} catch (IOException e) {
			log.error("压缩文件创建异常：", e);
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
			log.error(srcFile.getPath() + "所指文件不存在");
			return;
		}
		// 开始解压
		try (ZipFile zipFile = new ZipFile(srcFile)) {
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				// 如果是文件夹，就创建个文件夹
				if (entry.isDirectory()) {
					String dirPath = destDirPath + "/" + entry.getName();
					File dir = new File(dirPath);
					if (!dir.mkdirs()) {
						log.error(dirPath + "文件夹创建失败");
						return;
					}
				} else {
					// 如果是文件，就先创建一个文件，然后用io流把内容copy过去
					File targetFile = new File(destDirPath + "/" + entry.getName());
					// 保证这个文件的父文件夹必须要存在
					if (!targetFile.getParentFile().exists()) {
						if (!targetFile.getParentFile().mkdirs()) {
							log.error(targetFile + "文件创建失败");
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
			log.error("解压异常", e);
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