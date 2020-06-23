//package edu.xidian.sselab.database.hbase.op;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.hadoop.hbase.HColumnDescriptor;
//import org.apache.hadoop.hbase.HTableDescriptor;
//import org.apache.hadoop.hbase.TableExistsException;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.Admin;
//import org.apache.hadoop.hbase.client.Connection;
//
//
//public class HBaseCreateOP {
//
//	/**
//	 * @Description 建立HBase表，或向HBase表中添加列族
//	 * @author qhy
//	 * @Date 2017年3月24日 下午1:20:23
//	 * @version 1.0.0
//	 * @param tableName
//	 * @param familys
//	 * @throws IOException
//	 */
//	public  void CreateTable(String tableName, List<String> familys)
//			throws IOException {
//		Connection connection = HBaseUtil.getHBaseConfig().getConnection();
//		Admin admin = connection.getAdmin();
//		TableName name = TableName.valueOf(tableName);
//		if (admin.tableExists(name)) {
//			// 1.如果需要建立的表已经存在，则将没有的列族插入其中
//			HTableDescriptor tableDescriptor = admin.getTableDescriptor(name);
//			HColumnDescriptor[] existFamilys = tableDescriptor
//					.getColumnFamilies();
//			List<String> existFamilyStrings = new ArrayList<String>();
//			for (HColumnDescriptor existFamily : existFamilys) {
//				existFamilyStrings.add(existFamily.getNameAsString());
//			}
//			for (String family : familys) {
//				if (!existFamilyStrings.contains(family)) {
//					// 1.1如果没有这个列族，则进行创建
//					admin.addColumn(name, new HColumnDescriptor(family));
//
//				} else {
//					// 1.2如果有这个列族，一次输出提醒
//					System.out.println(family + "------------------Exist");
//				}
//			}
//		} else {
//			// 2.如果需要建立的表不存在，新建表，插入列族
//			HTableDescriptor tableDescriptor = new HTableDescriptor(name);
//			for (String family : familys) {
//				tableDescriptor.addFamily(new HColumnDescriptor(family));
//			}
//			try {
//				admin.createTable(tableDescriptor);
//			} catch (TableExistsException e) {
//				System.out.println(tableName + "------------------is Exist");
//			}
//		}
//		admin.close();
//		connection.close();
//	}
//}


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hbase.op;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HBaseCreateOP {
	public HBaseCreateOP() {
	}

	public void CreateTable(String tableName) throws IOException {
		Connection connection = HBaseUtil.getHBaseConfig().getConnection();
		Admin admin = connection.getAdmin();
		TableName name = TableName.valueOf(tableName);
		//TableDescriptorBuilder tableDescriptor;



		if(admin.tableExists(name))
		{
			//System.out.println( "表"+name+"已经存在啦");
			return;
		}

		else
		{
			TableDescriptorBuilder	tableDescriptor =  TableDescriptorBuilder.newBuilder(name);

			ColumnFamilyDescriptorBuilder cdb =  ColumnFamilyDescriptorBuilder.newBuilder( Bytes.toBytes("cf"));
			//获得列描述起
			ColumnFamilyDescriptor cf1 = cdb.build();


		//	HColumnDescriptor cf1 = new HColumnDescriptor("cf");
			//cf1.setTimeToLive(180 * 24 * 60 * 60);
			tableDescriptor.setColumnFamily(cf1);


			admin.createTable(tableDescriptor.build());
			admin.close();
			connection.close();
		}


			}



		}




