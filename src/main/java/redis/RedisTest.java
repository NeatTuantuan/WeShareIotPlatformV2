package redis;


import flink.dao.ConsumerClient;
import flink.dao.ServiceConsumerGroup;
import flink.dao.VariableRule;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.*;

/**
 * @author lr
 * @Package redis
 * @date 2020/4/7 11:08
 * @description  Redis测试
 */
public class RedisTest {

    /**
     选库，告警信息放在数据库0中，服务端订阅放在数据库1中
     */

    /**
     Redis 数据库0中当前 key="device_id"中存了两条规则，分别是：
     id=1,value=VariableRule(DeviceID=null, productID=null, flag=0, variableFlag=0, attribute=switch_status, startSection=0.0, endSection=0.0, varTriggerCondition=0, boolTriggerCondition=1)
     id=2,value=VariableRule(DeviceID=null, productID=null, flag=0, variableFlag=1, attribute=temperature, startSection=30.0, endSection=40.0, varTriggerCondition=2, boolTriggerCondition=0)
     */


    /**
     * Redis 数据库1中当前 key="1"中存了一条订阅信息，是
     ServiceConsumerGroup(consumerGroupId=null, client=[ConsumerClient(ConsumerClientId=null, ConsumerClientIP=127.0.0.1, port=9998)], deviceList=[device_id], pushType=[4])
     */

    public static void main(String[] args) {

//        查询所有hash里面的数据
//        HashMap<Integer, VariableRule> map = RedisOps.getObjectHashAll("device_id",0);
//        for (HashMap.Entry<Integer, VariableRule> entry : map.entrySet()) {
//            int i = entry.getKey();
//            VariableRule rule = entry.getValue();
//            System.out.println(i);
//            System.out.println(rule);
//        }


        //查询Hash中所有的key
//        HashSet<String> keys = RedisOps.keys("*");
//        System.out.println(keys);

//        存booltype类
//        VariableRule variableRule = new VariableRule();
//        variableRule.setFlag(0);
//        variableRule.setVariableFlag(0);
//        variableRule.setAttribute("switch_status");
//        variableRule.setBoolTriggerCondition(1);
//        /**
//         选库，告警信息放在数据库0中
//         */
//        RedisOps.setObjectHash("device_id",1,variableRule,0);
//

        //存digittype类型
//        VariableRule variableRule2 = new VariableRule();
//        variableRule2.setVariableFlag(1);
//        variableRule2.setVarTriggerCondition(2);
//        variableRule2.setStartSection(30.0);
//        variableRule2.setEndSection(40.0);
//        variableRule2.setBoolTriggerCondition(0);
//        variableRule2.setAttribute("temperature");
//        RedisOps.setObjectHash("device_id",2,variableRule2,0);


//        /**
//         存服务端订阅信息
//         */
//        ServiceConsumerGroup serviceConsumerGroup = new ServiceConsumerGroup();
//        ArrayList<ConsumerClient> consumerClients = new ArrayList<>();
//        ConsumerClient consumerClient = new ConsumerClient();
//        consumerClient.setConsumerClientIP("127.0.0.1");
//        consumerClient.setPort(9998);
//        consumerClients.add(consumerClient);
//        serviceConsumerGroup.setClient(consumerClients);
//        ArrayList<String> devicelist = new ArrayList<>();
//        devicelist.add("device_id");
//        serviceConsumerGroup.setDeviceList(devicelist);
//        HashSet<Integer> pushtype =new HashSet<>();
//        pushtype.add(4);
//        serviceConsumerGroup.setPushType(pushtype);
//
//        /**
//         选库，服务端订阅信息放在数据库1中
//         */
//        RedisOps.setObject("1",serviceConsumerGroup,1);


//        /**
//         * 查询某库的所有key值
//         */
//        HashSet<String> keys = RedisOps.keys("*", 1);
//        for(String key : keys){
//            System.out.println(key);
//        }

        //查询库1里的id为“1”的数据
//        System.out.println(RedisOps.getObject("1",1));



    }


}
