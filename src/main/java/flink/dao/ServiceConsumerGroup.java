package flink.dao;

import lombok.Data;

/**
 * @ClassName ServiceConsumerGroup
 * @Description TODO 消费组实体类
 * @Author tuantuan
 * @Date 2020/4/10 上午11:47
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class ServiceConsumerGroup {
    /**
     * 消费组名称
     */
    private String groupName;
    /**
     * 消费组IP
     */
    private String groupIp;
    /**
     * 消费组端口
     */
    private int port;
}
