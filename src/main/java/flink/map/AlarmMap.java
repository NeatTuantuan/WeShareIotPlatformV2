package flink.map;

import flink.dao.ServiceSubscriptionObject;
import flink.dao.VariableRule;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @ClassName AlarmMap
 * @Description TODO 告警map
 * @Author tuantuan
 * @Date 2020/4/10 上午11:25
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class AlarmMap extends RichMapFunction<String,String> {
    //RedisConnection里redis链接最好单例模式，节省资源
    @Override
    public void open(Configuration parameters) throws Exception {
        //获取redis链接
        super.open(parameters);
    }

    //这里的参数s应该是从kafka里反序列化拿出来的json串
    @Override
    public String map(String s) throws Exception {
        //从redis链接里拿出产品id对应的规则然后反序列化成对象
        //redis里告警规则应该为hash类型，一类产品对应一个hash表（表名为设备id），里面key对应一个自增编号，value对应规则的序列化字符串（因为一个设备可以有多个规则）
        //判断逻辑应该是：每次先看redis里有没有设备id对应的hash表，有的话从hash表中取出来一个规则Map
        //下面是一些简单思路

        HashMap<Integer,VariableRule> map = new HashMap<>();
        Iterator iter = map.keySet().iterator();
        //遍历规则
        while (iter.hasNext()) {
            int key = (int) iter.next();
            VariableRule val = map.get(key);
            //判断
            boolean flag = val.Judge(s);
            //如果flag是true，说明满足告警条件，需要向web端发送一个http告警请求，提交给线程池异步处理
        }
        return s;
    }

    @Override
    public void close() throws Exception {
        super.close();
    }
}
