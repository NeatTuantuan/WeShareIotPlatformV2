package flink.sink;

import flink.dao.AlarmInfo;
import flink.dao.ConsumerGroupInfo;
import netty.deviceMessage.DeviceMessage;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.util.ArrayList;

/**
 * @ClassName PersistenceSink
 * @Description TODO 数据持久化sink
 * @Author tuantuan
 * @Date 2020/5/13 下午8:18
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class PersistenceSink extends RichSinkFunction<Tuple3<DeviceMessage,ArrayList<AlarmInfo>,ConsumerGroupInfo>> {
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public void invoke(Tuple3<DeviceMessage, ArrayList<AlarmInfo>, ConsumerGroupInfo> value, Context context) throws Exception {

    }

    @Override
    public void close() throws Exception {
        super.close();
    }

}
