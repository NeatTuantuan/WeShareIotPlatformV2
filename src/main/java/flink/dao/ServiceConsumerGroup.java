package flink.dao;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @ClassName ServiceConsumerGroup
 * @Description TODO 消费组实体类
 * @Author tuantuan
 * @Date 2020/4/10 上午11:47
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class ServiceConsumerGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 消费者组ID
     */
    private String consumerGroupId;
    /**
     * 消费组客户端集合
     */
    private ArrayList<ConsumerClient> client;
    /**
     * 设备列表
     */
    private ArrayList<String> deviceList;
    /**
     * 推送类型链表：
     *1-设备上报消息
     * 2-设备状态变化
     * 3-设备生命周期变化
     * 4-设备报警
     * 5-平台远程控制
     */
    private HashSet<Integer> pushType;

    public ServiceConsumerGroup(){}

    public ServiceConsumerGroup(String consumerGroupId, ArrayList<ConsumerClient> client, ArrayList<String> deviceList, HashSet<Integer> pushType){
        this.consumerGroupId = consumerGroupId;
        this.client = client;
        this.deviceList = deviceList;
        this.pushType = pushType;
    }

    public ArrayList<String> getDeviceList() {
        return deviceList;
    }

    public String getConsumerGroupId() {
        return consumerGroupId;
    }

    public ArrayList<ConsumerClient> getClient() {
        return client;
    }

    public HashSet<Integer> getPushType() {
        return pushType;
    }

    public void setConsumerGroupId(String consumerGroupId) {
        this.consumerGroupId = consumerGroupId;
    }

    public void setClient(ArrayList<ConsumerClient> client) {
        this.client = client;
    }

    public void setDeviceList(ArrayList<String> deviceList) {
        this.deviceList = deviceList;
    }

    public void setPushType(HashSet<Integer> pushType) {
        this.pushType = pushType;
    }
}
