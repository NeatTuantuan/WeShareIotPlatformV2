package hbase.op;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ll
 * @version 1.0.0
 * @ClassName ExistTables
 * @Description  数据库中已经存在的HBase表
 * @Date 2019/9/17
 */
public class ExistTables {

    private Set<String> existTables = new HashSet<>();

    public ExistTables(){
        try {
            //系统开始加载时，初始化操作，存入HBase中已经存在的表
            Admin admin = HBaseUtil.getHBaseConfig().getConnection().getAdmin();
            TableName[] tableNames = admin.listTableNames();
            for(TableName tableName : tableNames) {
                System.out.println("数据库中现有表 " + tableName);
               existTables.add(tableName.getNameAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("初始化数据库表失败");
        }
    }
    /**
     * @Description: 通过表名查看该表是否存在
     * @author: ll
     * @date: 2019/9/17
     * @version 1.0.0
     * @param tableName
     * @return boolean
     */
    public boolean isTableExist(String tableName){
        return existTables.contains(tableName);
    }


    /**
     * @Description: 插入新建立（或者在系统初始化时存在）的表，更新数据
     * @author: ll
     * @date: 2019/9/17
     * @version 1.0.0
     * @param tableName
     * @return
     */
    public void insertExistingTable(String tableName){
        synchronized (ExistTables.class){
            if(isTableExist(tableName)) return;
            existTables.add(tableName);
        }
    }


}
