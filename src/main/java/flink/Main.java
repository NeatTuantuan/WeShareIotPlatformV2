package flink;


import com.alibaba.fastjson.JSONObject;
import netty.devicemessage.DeviceMessage;
import netty.devicemessage.VariableReport;
import netty.util.DeviceMessageJson;

import java.io.IOException;
import java.util.Collections;

/**
 * @ClassName Main
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/5 下午5:05
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class Main {
    public static void main(String[] args) throws IOException {
//deviceMessage.getVariableReport().getVariableJson().get(rule.getAttribute())
        VariableReport variableReport = new VariableReport();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("test","1");
        variableReport.setVariableJson(jsonObject);

        System.out.println((String) variableReport.getVariableJson().get("ggg"));

    }
}
