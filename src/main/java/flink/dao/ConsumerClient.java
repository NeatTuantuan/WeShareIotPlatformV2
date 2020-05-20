package flink.dao;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ConsumerClient
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/16 上午10:51
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class ConsumerClient implements Serializable {
    /**
     * 客户端id
     */
    private String ConsumerClientId;
    /**
     * 客户端ip
     */
    private String ConsumerClientIP;
    /**
     * 客户端端口
     */
    private int port;
}
