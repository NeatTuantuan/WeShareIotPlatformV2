package flink.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HttpApi
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/10 下午3:04
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class HttpApi implements Runnable{
    private String ip;
    private int port;
    private String deviceId;
    private String message;

    public HttpApi(String ip, int port, String deviceId, String message){
        this.ip = ip;
        this.port = port;
        this.deviceId = deviceId;
        this.message = message;
    }

    /**
     * post方法发送json数据
     */
    @Override
    public void run(){
        try{
            String url = "http://"+ip+":"+port+"/"+deviceId;
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();

            connection.setDoInput(true); // 设置可输入
            connection.setDoOutput(true); // 设置该连接是可以输出的
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("message", message);
            PrintWriter pw = new PrintWriter(new BufferedOutputStream(connection.getOutputStream()));
            pw.write(objectMapper.writeValueAsString(data));
            pw.flush();
            pw.close();

            connection.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
