package com.elwyn.modules.bigdata.dao.impala;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 使用该类管理数据库连接
 * @author adminitartor
 *
 */
public class DBUtil {
    //DBCP的连接池实例
    private static BasicDataSource ds;
    static{
        try {
            //初始化连接池
            ds = new BasicDataSource();
            //设置驱动名,原Class.forName()中的内容
            ds.setDriverClassName("com.cloudera.impala.jdbc41.Driver");
            ds.setUrl("jdbc:impala://192.168.30.217:21050/yuncai");
            ds.setUsername("");
            ds.setPassword("");
            //连接池中的最大连接数
            ds.setMaxActive(5);
            //最大等待时间
            ds.setMaxWait(500);
            System.out.println("初始化完毕!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取一个数据库连接
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        return ds.getConnection();
    }
    /**
     * 关闭给定的连接
     * @param conn
     */
    public static void closeConnection(Connection conn){
        try {
            if(conn != null){
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

