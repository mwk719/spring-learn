package com.mwk.bottom.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class IO2 {

    /**
     * 在目标文件的指定位置添加指定字符串
     * 
     * @param filePath
     * @param position
     * @param contents
     */
    public static void addContainsToFile(String filePath, int position, String contents) {
        File file = new File(filePath);
        File path = new File("C:\\temp_path\\text\\");
        // 文件判断
        if (!(file.exists() && file.isFile())) {
            System.err.println("文件不存在");
            return;
        }
        // 位置判断
        if (position < 0 || position > file.length()) {
            System.err.println("position不合法");
            return;
        }
        // 创建临时文件
        try {
            File tempFile = File.createTempFile("temp", ".temp", path);
            FileOutputStream out = new FileOutputStream(tempFile);
            FileInputStream in = new FileInputStream(tempFile);
            // 再退出jvm时自动删除
            tempFile.deleteOnExit();
            RandomAccessFile rw = new RandomAccessFile(file, "rw");
            rw.seek(position);
            int temp;
            // 将position位置后的内容写入临时文件夹
            while ((temp = rw.read()) != -1) {
                out.write(temp);
            }
            rw.seek(position);
            rw.write(contents.getBytes());

            while ((temp = in.read()) != -1) {
                rw.write(temp);
            }
            rw.close();
            out.close();
            in.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        File inFile = new File("C:\\temp_path\\text\\1.txt");
        String filePath = "C:\\temp_path\\text\\1.txt";
        int position = 3;
        String contents = "888888";
        addContainsToFile(filePath, position, contents);

    }

}
