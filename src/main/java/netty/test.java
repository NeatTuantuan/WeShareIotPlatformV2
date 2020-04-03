package netty;

import java.util.ResourceBundle;

/**
 * @ClassName test
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/3 上午10:34
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class test {
    public static void main(String[] args) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("systemConfig");
        System.out.println(resourceBundle.getString("IP"));
    }
}
