package redis;


import flink.dao.VariableRule;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    }
}
