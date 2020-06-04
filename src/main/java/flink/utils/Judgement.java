package flink.utils;

import com.alibaba.fastjson.JSONObject;
import flink.dao.VariableRule;

/**
 * @ClassName Judgement
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/5/13 下午7:22
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class Judgement {
    /**
     *
     * @param rule
     * @param formatData
     * @return
     */
    public static boolean judge(VariableRule rule, JSONObject formatData){
        switch (rule.getVariableFlag()){
            //这里要注意实体类里true/false对应的是0/1
            case 0:
                return formatData.getString(rule.getAttribute())
                        .equals(String.valueOf(rule.getBoolTriggerCondition()));
            case 1:
                //将告警字段转换为double类型
                Double doubleAttributeValue = Double.parseDouble(formatData.getString(rule.getAttribute()));

                switch (rule.getVarTriggerCondition()){
                    case 0:
                        return doubleAttributeValue > rule.getStartSection() ? true : false;
                    case 1:
                        return doubleAttributeValue < rule.getStartSection() ? true : false;
                    case 2:
                        return (doubleAttributeValue <= rule.getEndSection() && doubleAttributeValue >= rule.getStartSection()) ? true : false;
                    case 3:
                        return(doubleAttributeValue > rule.getEndSection() || doubleAttributeValue < rule.getStartSection()) ? true : false;
                    default :
                        return false;

                }
            default:
                return false;
        }
    }
}
