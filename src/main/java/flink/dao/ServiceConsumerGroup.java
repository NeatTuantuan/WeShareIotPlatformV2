package flink.dao;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

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
    /**
     * 消费组名称
     */
    private String groupName;
    /**
     * 消费组ID
     */
    private int groupId;
    /**
     * 消费组客户端集合
     */
    private ArrayList<ConsumerClient> client;
    /**
     * 产品名称列表
     */
    private ArrayList<String> deviceList;
    /**
     * 推送类型链表：
     *上报消息、状态变化、生命周期变更，报警
     */
    private ArrayList<String> pushType;
}
