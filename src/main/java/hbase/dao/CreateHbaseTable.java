package hbase.dao;

import com.google.gson.JsonObject;
import hbase.op.HBaseUtil;
import netty.devicemessage.DeviceMessage;
import netty.devicemessage.DeviceShadow;
import netty.devicemessage.VariableReport;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Random;

public class CreateHbaseTable {

    public static void createhbase(DeviceMessage message) {

        DeviceShadow deviceShadow=message.getDeviceShadow();
        VariableReport variableReport = message.getVariableReport();

        //如果是设备影子
        if(message.getDeviceMessageType()==0)
        {
            String productID = message.getTopic().split("/")[5];//获取产品ID
            String deviceID = message.getTopic().split("/")[6];//获取设备ID
            String rowkey = getrowkey(deviceID);
//            System.out.println(rowkey);
            String tableNamePostfix = HBaseUtil.getTimeCalculateHelper().getDatePostfix( System.currentTimeMillis() );

            Put recordPut = new Put( Bytes.toBytes( rowkey ) );
            //recordPut.addColumn( Bytes.toBytes(  "cf"), Bytes.toBytes( "token" ),Bytes.toBytes(re.getTOKEN()  ));
            recordPut.addColumn( Bytes.toBytes( "cf" ), Bytes.toBytes( "id" ),Bytes.toBytes( deviceShadow.getId()));
            recordPut.addColumn( Bytes.toBytes( "cf" ), Bytes.toBytes( "method" ), Bytes.toBytes(deviceShadow.getMethod()));
            recordPut.addColumn( Bytes.toBytes( "cf" ), Bytes.toBytes( "version" ), Bytes.toBytes(deviceShadow.getVersion()));
            recordPut.addColumn( Bytes.toBytes( "cf" ), Bytes.toBytes( "params" ), Bytes.toBytes(String.valueOf(deviceShadow.getState())));


//            System.out.println(tableNamePostfix);
            // recordList.add(recordPut);
            HBaseUtil.getHBaseInsertOP().insertWithput( productID +"."+tableNamePostfix+"."+"0", recordPut );
            // HBaseUtil.getHBaseInsertOP().insertWithput( tableNamePostfix + "Personnelenteringthearea", recordPut );
        }

        //如果是变量上报
        else if(message.getDeviceMessageType()==1)
        {
            String productID = message.getTopic().split("/")[4];//获取产品ID
            String deviceID = message.getTopic().split("/")[5];//获取设备ID
            String rowkey = getrowkey(deviceID);
            String tableNamePostfix = HBaseUtil.getTimeCalculateHelper().getDatePostfix( System.currentTimeMillis() );
            Put recordPut = new Put( Bytes.toBytes( rowkey ) );
            //recordPut.addColumn( Bytes.toBytes(  "cf"), Bytes.toBytes( "token" ),Bytes.toBytes(re.getTOKEN()  ));
            recordPut.addColumn( Bytes.toBytes( "cf" ), Bytes.toBytes( "id" ),Bytes.toBytes( variableReport.getId()));
            recordPut.addColumn( Bytes.toBytes( "cf" ), Bytes.toBytes( "method" ), Bytes.toBytes(variableReport.getMethod()));
            recordPut.addColumn( Bytes.toBytes( "cf" ), Bytes.toBytes( "version" ), Bytes.toBytes(variableReport.getVersion()));
            recordPut.addColumn( Bytes.toBytes( "cf" ), Bytes.toBytes( "params" ), Bytes.toBytes(variableReport.getVariableJson().toJSONString()));

            // recordList.add(recordPut);
            HBaseUtil.getHBaseInsertOP().insertWithput( productID +"."+tableNamePostfix+"."+"1", recordPut );

        }

    }

    //生成行键
    public static String getrowkey(String s)
    {
        Long time=System.currentTimeMillis();

        return  s + "##" + time;
    }

    public static void main(String args[])
    {


    }
}

