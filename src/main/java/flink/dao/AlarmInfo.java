package flink.dao;

import lombok.Data;

/**
 * @ClassName AlarmInfo
 * @Description TODO 封装是否告警的相关信息
 * @Author tuantuan
 * @Date 2020/5/12 下午8:03
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class AlarmInfo {
    /**
     * 告警字段
     */
    private String attribute;
    /**
     * 告警类型：
     * 1-区间类型
     * 0-布尔类型
     * 2-上下线
     */
    private int alarmType;
    /**
     * 告警详情
     */
    private String alarmMessage;

    public AlarmInfo(){
    }

    public AlarmInfo(String attribute, int alarmType, String alarmMessage){
        this.attribute = attribute;
        this.alarmType = alarmType;
        this.alarmMessage = alarmMessage;
    }

    public static AlarmInfo of(String attribute, int alarmType, String alarmMessage){
        return new AlarmInfo(attribute,alarmType,alarmMessage);
    }



}
