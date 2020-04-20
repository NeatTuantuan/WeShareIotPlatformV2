package flink.dao;

import flink.dao.BoolTypeRule;
import flink.dao.DigitTypeRule;
import flink.dao.Rule;
import lombok.Data;
import netty.deviceMessage.DeviceMessage;

import java.io.Serializable;

/**
 * @ClassName VariableRule
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/5 下午5:02
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class VariableRule extends Rule implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 规则flag：0-bool类型规则
     * 1-digit类型规则
     */
    private int variableFlag = 0;

    private BoolTypeRule boolTypeRule = null;

    private DigitTypeRule digitTypeRule = null;
    //规则字段
    private String attribute;

    public Boolean Judge(DeviceMessage deviceMessage) {
        return variableFlag == 0 ? boolTypeRule.Judge(deviceMessage,attribute) : digitTypeRule.Judge(deviceMessage,attribute);
    }

    public int getVariableFlag() {
        return variableFlag;
    }

    public void setVariableFlag(int variableFlag) {
        this.variableFlag = variableFlag;
    }

    public BoolTypeRule getBoolTypeRule() {
        return boolTypeRule;
    }

    public void setBoolTypeRule(BoolTypeRule boolTypeRule) {
        this.boolTypeRule = boolTypeRule;
    }

    public DigitTypeRule getDigitTypeRule() {
        return digitTypeRule;
    }

    public void setDigitTypeRule(DigitTypeRule digitTypeRule) {
        this.digitTypeRule = digitTypeRule;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
