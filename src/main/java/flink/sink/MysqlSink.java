package flink.sink;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @ClassName MysqlSink
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/21 下午2:50
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class MysqlSink extends RichSinkFunction<String> {
    private transient Connection connection = null;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        //创建mysql链接
        connection = DriverManager.getConnection("jdbc:mysql://47.103.29.15:3306/iot_platform?serverTimezone=Hongkong&useUnicode=true&characterEncoding=utf-8&useSSL=false",
                "root", "123456");
    }

    @Override
    public void invoke(String value, Context context) throws Exception {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO category () VALUES (?,?,?)");
//            statement.setString();
            statement.executeUpdate();
            statement.close();
        }finally {
            if (statement!=null){
                statement.close();
            }
        }


    }

    @Override
    public void close() throws Exception {
        super.close();
        connection.close();
    }
}
