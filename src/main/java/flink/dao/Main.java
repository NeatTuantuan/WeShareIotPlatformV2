package flink.dao;

import com.alibaba.fastjson.JSONObject;
import netty.deviceMessage.DeviceMessage;

import java.util.HashSet;
import java.util.Iterator;

/**
 * @ClassName Main
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/5 下午5:05
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class Main {
    public static void main(String[] args) {
        DeviceMessage deviceMessage = new DeviceMessage();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("test",3);
        deviceMessage.setFormatData(jsonObject);

        deviceMessage.setDEVICE_ID("1");

        test(deviceMessage);

//        System.out.println(deviceMessage.getFormatData().get("test"));
    }

    public static void test(DeviceMessage deviceMessage){
        //从Reids中取处所有变量报警规则
        HashSet<VariableRule> set = new HashSet<>();

        VariableRule variableRule = new VariableRule();
        variableRule.setDeviceID("1");
        variableRule.setAttribute("test");
        variableRule.setVariableFlag(1);
        set.add(variableRule);

        //遍历set，查看有没有设备id对应的规则
        Iterator<VariableRule> it = set.iterator();
        while (it.hasNext()) {
            VariableRule rule = it.next();
            //如果有对应规则
            if (rule.getDeviceID() == deviceMessage.getDEVICE_ID()){
                switch (rule.getVariableFlag()){
                    case 0:
                        rule.setBoolTypeRule(new BoolTypeRule());
                        rule.Judge(deviceMessage);
                        //提交给线程池发送告警信息。
                        break;
                    case 1:
                        rule.setDigitTypeRule(new DigitTypeRule());
                        rule.Judge(deviceMessage);
                        System.out.println(rule.Judge(deviceMessage));
                        //提交给线程池发送告警信息。
                        break;
                    default:
                        //dosth

                }
            }
        }
    }
}
