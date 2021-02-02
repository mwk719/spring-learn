package com.mwk.utils;

import java.util.Scanner;

/**
 * 人工智能回复
 *
 * @author MinWeikai
 * @date 2020/12/21 10:19
 */
public class AiReply {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str;
        while (true) {
            str = sc.next();
            str = str.replace("吗", "")
                    .replace("你能", "我能")
                    .replaceAll("？", "！");
            System.out.println(str);
        }
    }
}
