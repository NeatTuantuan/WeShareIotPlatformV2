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
     * 是否告警：
     * true-告警
     * false-不告警
     */
    private boolean isAlarm;
    /**
     * 告警类型：
     * 1-区间类型
     * 2-布尔类型
     * 3-上下线
     */
    private int alarmType;
    /**
     * 告警详情
     */
    private String alarmMessage;

    public AlarmInfo(){
    }

    public AlarmInfo(boolean isAlarm, int alarmType, String alarmMessage){
        this.isAlarm = isAlarm;
        this.alarmType = alarmType;
        this.alarmMessage = alarmMessage;
    }

    public static AlarmInfo of(boolean isAlarm, int alarmType, String alarmMessage){
        return new AlarmInfo(isAlarm,alarmType,alarmMessage);
    }



}
