package test;

import hbase.dao.CreateHbaseTable;
import hbase.op.HBaseUtil;
import netty.devicemessage.DeviceMessage;
import netty.devicemessage.DeviceShadow;

public class hbasetest {

    public static void main(String[] args)
    {
//        try {
////            Connection connection = HBaseUtil.getHBaseConfig().getConnection();
////            System.out.println( connection );
////            Admin admin = connection.getAdmin();
////            TableName name = TableName.valueOf("ll");
////
////          HTableDescriptor  tableDescriptor = new HTableDescriptor(name);
////            HColumnDescriptor cf1 = new HColumnDescriptor("cf");
////            tableDescriptor.addFamily( cf1 );
////            admin.createTable( tableDescriptor );
////
////        } catch (IOException e) {
////            e.printStackTrace();
////        }

//        PhotoUpload a=new PhotoUpload( Bytes.toBytes("L:hahahh") );
//        System.out.println( a );
//       HBaseUtil.getINSERT().insertToHBase( a );
//        Date date = new Date();//获取当前时间
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Date c= null;
//        try {
//            c = df.parse("2019-01-12 15:08:18"  );
//            Date b=df.parse( "2019-09-12 15:08:18" );
//            System.out.println( b.getTime() );
//            HBaseUtil.getTimeCalculateHelper().getDatePostfixListFromTwoDate( c.getTime(),b.getTime() );
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


        DeviceMessage meaasge=new DeviceMessage();
        DeviceShadow shaowdow=new DeviceShadow();
        shaowdow.setId("23");
        shaowdow.setMethod("method");
        shaowdow.setState(0);
        shaowdow.setVersion("89");
        meaasge.setDeviceShadow(shaowdow);
        meaasge.setDeviceMessageType(0);
        meaasge.setTopic("90897");

        CreateHbaseTable.createhbase(meaasge);

//        Record a=new Record( Bytes.toBytes( "llo" ) );
//       a.setOPEN_TYPE( 1 );
//        a.setCARD_ID( null );
//
//        HBaseUtil.getINSERT().insertToHBase( a );
//
//
//        Record b=new Record( Bytes.toBytes( "ll1" ) );
//        a.setOPEN_TYPE( 0 );
//
//        HBaseUtil.getINSERT().insertToHBase( b );
//
//
//        Record c=new Record( Bytes.toBytes( "ll2" ) );
//        c.setOPEN_TYPE( 1 );
//        c.setCARD_ID( null );
//        //c.setCARD_MATCH_USER( null );
//        HBaseUtil.getINSERT().insertToHBase( c );
//
//        Record d=new Record( Bytes.toBytes( "ll3" ) );
//        a.setOPEN_TYPE( 0 );
//
//        HBaseUtil.getINSERT().insertToHBase( d );

//
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//            Date c= null;
//
//        try {
//            c = df.parse("2019-01-12 15:08:18"  );
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//
//        Record b=new Record( Bytes.toBytes( "llo" ) );
//        b.setOPEN_TIME( c.getTime() );
//        HBaseUtil.getINSERT().insertToHBase( b );
//
//        Record d=new Record( Bytes.toBytes( "llo" ) );
//
//        HBaseUtil.getINSERT().insertToHBase( d);



//List<Record> a=HBaseUtil.getFind().FindopendoorRecord(1568869373012L, 1568869378798L , 10,"9527","190273" ,"770fed4ca2aabd20ae9a5dd774711de2");
//for(Record b:a)
//{
//    System.out.println( b.getOPEN_TIME() );
//    System.out.println(Bytes.toString( b.getPHOTO() )  );
//}



//        c.setOPEN_TYPE( 1 );

//System.out.println( a );
    }
}
