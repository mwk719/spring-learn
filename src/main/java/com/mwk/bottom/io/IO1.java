package com.mwk.bottom.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class IO1 {

    public static void main(String[] args) {
        File inFile = new File("C:\\temp_path\\text\\1.txt");
        File outFile = new File("C:\\temp_path\\text\\2.txt");

        try {
            OutputStream os = new FileOutputStream(inFile);
            // 创建时，需要给予一个outputStream，这个很好理解，
            // 因为对象操作肯定是字节操作，不能使用字符操作
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(outFile);
            oos.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            InputStream is = new FileInputStream(file);
//            ObjectInputStream ios = new ObjectInputStream(is);
//            MyService object = (MyService) ios.readObject();
//            System.out.println(object.name);
//            is.close();
//            ios.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

    }

}
