package flink.dao;

import lombok.Data;

import java.util.HashSet;

/**
 * @ClassName ConsumerGroupInfo
 * @Description TODO 封装服务端订阅的相关信息
 * @Author tuantuan
 * @Date 2020/5/12 下午8:04
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class ConsumerGroupInfo {
    /**
     * 推送的目标IP
     */
    private String IP;
    /**
     * 推送的目标端口
     */
    private int port;
    /**
     * 推送类型链表：
     *1-设备上报消息
     * 2-设备状态变化
     * 3-设备生命周期变化
     * 4-设备报警
     * 5-平台远程控制
     */
    private HashSet<Integer> pushType;

    public ConsumerGroupInfo(){}
    public ConsumerGroupInfo(String ip, int port, HashSet<Integer> pushType){
        this.IP = ip;
        this.port = port;
        this.pushType = pushType;
    }

    public static ConsumerGroupInfo of(String ip, int port, HashSet<Integer> pushType){
        return new ConsumerGroupInfo(ip,port,pushType);
    }
}
