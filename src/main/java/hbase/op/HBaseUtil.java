package hbase.op;


import hbase.conf.HBaseConfig;


/**
 * @author ll
 * @version 1.0.0
 * @ClassName HBaseUtil
 * @Description  只需要一个对象的类的整体配置文件
 * @Date 2019/9/18
 */
public class HBaseUtil {



    //private static ReadPropertiesFile READPROPERTIESFILE = new ReadPropertiesFile();
    private static HBaseConfig HBASECONFIG = new HBaseConfig();
    private static TimeCalculateHelper TIMECALCULATEHELPER = new TimeCalculateHelper();

    private static ExistTables EXISTTABLES = new ExistTables();

    //private  static InsertToHbase INSERT=new InsertToHbase();
 //private  static hbase.op.HbaseScanOP HbaseScanOP=new HbaseScanOP();

    private static HBaseCreateOP HBASECREATEOP = new HBaseCreateOP();
    private static HBaseInsertOP HBASEINSERTOP = new HBaseInsertOP();
//private static hbase.dao.Find Find=new Find();
  //private static RecordMapper RECORDMAPPER = new RecordMapper();

//    public static ReadPropertiesFile getReadPropertiesFile() {
//        return READPROPERTIESFILE;
//    }
//
//   // public  static InsertToHbase getINSERT()
//    {
//        return INSERT;
//    }
    public static HBaseConfig getHBaseConfig() {
        return HBASECONFIG;
    }

    public static TimeCalculateHelper getTimeCalculateHelper() {
        return TIMECALCULATEHELPER;
    }

    public static ExistTables getExistTables(){
        return EXISTTABLES;
    }

    public static HBaseCreateOP getHBaseCreateOP(){
        return HBASECREATEOP;
    }

    public static HBaseInsertOP getHBaseInsertOP(){
        return HBASEINSERTOP;
    }


//   // public  static hbase.dao.Find getFind(){
//        return  Find;
//    }
//public  static hbase.op.HbaseScanOP getHbaseScanOP(){
//        return HbaseScanOP;
//}
//
//    public static RecordMapper getRecordMapper(){
//
//        return RECORDMAPPER;
//    }

}
