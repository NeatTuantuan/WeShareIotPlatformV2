package flink.dao;

import lombok.Data;

import java.util.ArrayList;

/**
 * @ClassName ServiceSubscriptionObject
 * @Description TODO 服务端订阅实体类
 * @Author tuantuan
 * @Date 2020/4/10 上午11:46
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class ServiceSubscriptionObject {
    /**
     * 消费者组
     */
    private ServiceConsumerGroup serviceConsumerGroup;
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
