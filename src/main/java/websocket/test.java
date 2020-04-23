package websocket;

/**
 * @ClassName test
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/22 上午11:45
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class test {
    public static void main(String[] args) {
        testLambda testLambda1 = (int a, int b, int c)->a*b*c;
        System.out.print(testLambda1.operation2(1,2,3));
    }
}
