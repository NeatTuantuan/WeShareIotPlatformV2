//package edu.xidian.sselab.database.hbase.op;
//
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.Connection;
//import org.apache.hadoop.hbase.client.Put;
//import org.apache.hadoop.hbase.client.Table;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author qhy
// * @version 1.0.0
// * @ClassName HBaseInsertOP
// * @Description 数据库插入操作
// * @Date 2017/4/6 20:59
// */
//public class HBaseInsertOP {
//
//    /**
//     * @Description: 通过表名和Put对应关系插入数据
//     * @author: qhy
//     * @date: 2017/4/6 21:03
//     * @version 1.0.0
//     *
//     */
//    public void insertWithMap(Map<String,List<Put>> tableToPuts){
//        Connection connection = null;
//        try {
//            connection = HBaseUtil.getHBaseConfig().getConnection();
//        } catch (IOException e) {
//            System.out.println("insertWithMap插入操作创建连接失败");
//            e.printStackTrace();
//        }
//        for(String tableName : tableToPuts.keySet()){
//            //1.判断要插入的表是否存在
//            if(!HBaseUtil.getExistTables().isTableExist(tableName)){
//                //1.如果要插入的表不存在,先进行建表操作
//                String datePostfix = tableName.substring(tableName.length()-6);
//                Map<String,List<String>> tableDes = HBaseUtil.getHTableConfig().makeHTablesMap(datePostfix);
//                for(String tableNameDes : tableDes.keySet()){
//                    try {
//                        HBaseUtil.getHBaseCreateOP().CreateTable(tableNameDes , tableDes.get(tableNameDes));
//                    } catch (IOException e) {
//                        System.out.println("增加表 " + tableNameDes + " 操作失败");
//                        e.printStackTrace();
//                    }
//                    HBaseUtil.getExistTables().insertExistingTable(tableNameDes);
//                }
//            }
//            //2.执行插入操作
//            try {
//                Table table = connection.getTable(TableName.valueOf(tableName));
//                table.put(tableToPuts.get(tableName));
//                table.close();
//            } catch (IOException e) {
//                System.out.println("向 " + tableName + " 表插入数据失败");
//                e.printStackTrace();
//            }
//        }
//    }
//}


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hbase.op;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

public class HBaseInsertOP {
    public HBaseInsertOP() {
    }

    public void insertWithput(String tablename, Put tableToPut) {
        // Iterator var2 = tableToPuts.keySet().iterator();
        if(!HBaseUtil.getExistTables().isTableExist(tablename))
        {
            try {
                HBaseUtil.getHBaseCreateOP().CreateTable(tablename);
                System.out.println("增加表 " + tablename + " 操作成功");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("增加表 " + tablename + " 操作失败");
            }

        }


        try {
            Table table = HBaseUtil.getHBaseConfig().getTableByName(tablename);
            table.put(tableToPut);
            table.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}