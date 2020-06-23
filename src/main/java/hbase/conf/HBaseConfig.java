package hbase.conf;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

public class HBaseConfig {
    private static Configuration conf = null;
    private static Connection connection = null;

    public HBaseConfig() {
    }

    public Configuration getConf() {
        if(conf == null) {
            conf = HBaseConfiguration.create();
            conf.set("hbase.master", "192.168.0.131");
            conf.set("hbase.zookeeper.quorum", "192.168.0.131,192.168.0.132,192.168.0.133");
            conf.set("hbase.zookeeper.property.clientPort", "2181");
        }

        return conf;
    }

    public Connection getConnection() throws IOException {
        if(connection == null || connection.isClosed()) {
            synchronized(this) {
                if(connection == null || connection.isClosed()) {
                    connection = ConnectionFactory.createConnection(conf);
                }
            }
        }

        return connection;
    }

    public Table getTableByName(String tabelName) {
        try {
            return this.getConnection().getTable(TableName.valueOf(tabelName));
        } catch (IOException var3) {
            var3.printStackTrace();
            System.out.println("getTableByNam()方法报错，没能获取到表");
            return null;
        }
    }

    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.master", "192.168.0.131");
        conf.set("hbase.zookeeper.quorum", "192.168.0.131,192.168.0.132,192.168.0.133");
        conf.set("hbase.zookeeper.property.clientPort", "2181");

    }
}
