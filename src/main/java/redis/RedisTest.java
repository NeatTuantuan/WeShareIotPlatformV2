package redis;

import flink.dao.BoolTypeRule;
import flink.dao.DigitTypeRule;
import flink.dao.VariableRule;

/**
 * @author lr
 * @Package redis
 * @date 2020/4/7 11:08
 * @description  Redis测试
 */
public class RedisTest {

    public static void main(String[] args) {
        VariableRule variableRule = new VariableRule();
        DigitTypeRule digitTypeRule = new DigitTypeRule();
        digitTypeRule.setSTART_SECTION(2);
        digitTypeRule.setEND_SECTION(3);
        digitTypeRule.setTriggerCondition(3);
        BoolTypeRule boolTypeRule = new BoolTypeRule();
        boolTypeRule.setTriggerCondition(1);
        variableRule.setDeviceID("1");
        variableRule.setAttribute("test");
        variableRule.setVariableFlag(1);
        variableRule.setBoolTypeRule(boolTypeRule);
        variableRule.setDigitTypeRule(digitTypeRule);
        RedisOps.setObject("test:3", variableRule);//序列化
        VariableRule variableRule1 = (VariableRule) RedisOps.getObject("test:3");//反序列化
        System.out.println(variableRule1);
        System.out.println(variableRule1.getDeviceID());
    }


}
