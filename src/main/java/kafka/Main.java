package kafka;

import java.util.Scanner;

/**
 * @ClassName Main
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/10 下午7:14
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int array[] = new int[n];
        for (int i = 0 ; i < n ; i++){
            array[i] = in.nextInt();
        }
        int temp[] = new int[n+1];
        temp[0] = 0;
        temp[1] = array[0];
        temp[2] = array[0]+array[1];

        for (int i = 3 ; i < n+1 ; i++){
            temp[i] = array[i-1]+array[i-2]+temp[i-3];
        }

        for (int i = 0; i < temp.length; i++) {
            System.out.print(temp[i]+",");
        }
    }
}
