package flink.dao;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName VariableRule
 * @Description TODO 变量类型规则
 * @Author tuantuan
 * @Date 2020/4/5 下午5:02
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class VariableRule implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 规则对应设备id
     */
    private String DeviceID;
    /**
     * 规则对应产品id
     */
    private String productID;
    /**
     * 通知频率标志，0为单次通知，1为重复通知
     */
    private int flag;
    /**
     * 规则flag：
     * 0-bool类型规则
     * 1-digit类型规则
     */
    private int variableFlag;
    /**
     * 规则字段
     */
    private String attribute;
    /**
     * 区间起始值
     */
    private double START_SECTION;
    /**
     * 区间结束值
     */
    private double END_SECTION;
    /**
     * 触发条件：0-大于
     * 1-小与
     * 2-区间
     * 3-小于或大于
     * 默认为区间
     */
    private int varTriggerCondition;
    /**
     * 触发条件：0或者1
     */
    private int boolTriggerCondition;
}
