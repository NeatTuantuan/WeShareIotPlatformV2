package redis;


import flink.dao.VariableRule;

import java.util.HashMap;

/**
 * @author lr
 * @Package redis
 * @date 2020/4/7 11:08
 * @description  Redis测试
 */
public class RedisTest {

    /**
     Redis当前 key="1001"中存了两条规则，分别是：
     VariableRule(variableFlag=0, boolTypeRule=BoolTypeRule(triggerCondition=1), digitTypeRule=null, attribute=kaiguan)
     VariableRule(variableFlag=1, boolTypeRule=null, digitTypeRule=DigitTypeRule(START_SECTION=1.0, END_SECTION=5.0, triggerCondition=2), attribute=temperature)
     */
    public static void main(String[] args) {
//        VariableRule variableRule = new VariableRule();
//        DigitTypeRule digitTypeRule = new DigitTypeRule();
//        digitTypeRule.setSTART_SECTION(1);
//        digitTypeRule.setEND_SECTION(5);
////        digitTypeRule.setTriggerCondition(3);
////        BoolTypeRule boolTypeRule = new BoolTypeRule();
////        boolTypeRule.setTriggerCondition(1);
//        variableRule.setDeviceID("1001");
//        variableRule.setAttribute("temperature");
//        variableRule.setVariableFlag(1);
//        variableRule.setDigitTypeRule(digitTypeRule);

//        variableRule.setDigitTypeRule(digitTypeRule);
//        RedisOps.setObjectHash("1001",2,variableRule);
        HashMap<Integer, VariableRule> map = RedisOps.getObjectHashAll("1001");
        for (HashMap.Entry<Integer, VariableRule> entry : map.entrySet()){
//            int i= (int)SerializeUtils.deserialize(entry.getKey());
//            VariableRule rule =(VariableRule)SerializeUtils.deserialize(entry.getValue());
            int i = entry.getKey();
            VariableRule rule = entry.getValue();
            System.out.println(i);
            System.out.println(rule);
//        VariableRule variableRule1 =(VariableRule) RedisOps.getObjectHash("1001", 2);
//        System.out.println(variableRule1);

//
//        RedisOps.setObject("test:4", variableRule);//序列化
//        VariableRule variableRule1 = (VariableRule) RedisOps.getObject("test:4");//反序列化
//
//        System.out.println(variableRule1);
//        System.out.println(variableRule1.getDeviceID());
        }
    }
}
