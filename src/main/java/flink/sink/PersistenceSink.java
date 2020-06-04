package flink.sink;

import flink.dao.AlarmInfo;
import flink.dao.ServiceConsumerGroup;
import netty.devicemessage.DeviceMessage;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.util.ArrayList;

/**
 * @ClassName PersistenceSink
 * @Description TODO 数据持久化sink，参考MysqlSink
 * @Author tuantuan
 * @Date 2020/5/13 下午8:18
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class PersistenceSink extends RichSinkFunction<Tuple3<DeviceMessage,ArrayList<AlarmInfo>,ServiceConsumerGroup>> {
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public void invoke(Tuple3<DeviceMessage, ArrayList<AlarmInfo>, ServiceConsumerGroup> value, Context context) throws Exception {

    }

    @Override
    public void close() throws Exception {
        super.close();
    }

}
