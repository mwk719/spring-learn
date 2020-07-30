package com.mwk.dontknow;

import java.util.Scanner;

public class test1 {

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner in = new Scanner(System.in);

        int floor = in.nextInt();

        int specialFloor = 13;
        // 必要时调整楼层
        int actualFloor;
        if (floor > specialFloor) {
            actualFloor = floor - 1;
        } else {
            actualFloor = floor;
        }

        System.out.println("到达楼层：" + actualFloor);

    }

}
