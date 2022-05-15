package com.henu.eltfood.DataSystem;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

// An highlighted block
public class MySQLConnections {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://rm-bp1v5fj43hau0bjh18o.mysql.rds.aliyuncs.com:3306/android?useSSL=false";
    private static String username = "ladehenduo";
    private static String password = "Asdfghjkl123456";
    // 获取数据库的连接
    public static Connection getConnection(){
        Connection connection = null;
        try {
            // 加载驱动
            Class.forName(driver);
            // 连接数据库
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }


    // 释放资源
    public static boolean closeResource(Connection connection, ResultSet resultSet, PreparedStatement preparedStatement){
        boolean flag = true;
        if(resultSet != null)
        {
            try {
                resultSet.close();
                resultSet = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if(connection != null)
        {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if(preparedStatement != null)
        {
            try {
                preparedStatement.close();
                preparedStatement = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }


}

