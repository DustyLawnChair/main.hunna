package com.PlayTrackerWebApp.playtracker.dao;

import java.sql.*;

public class MariaDbUtil
{
    private static String connectionUrl =
            "jdbc:mariadb://localhost:3306/mystats?user=root&password=@Hunter2004";

    public static Connection getConnection()
    {
        Connection connection = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(connectionUrl);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return connection;
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = MariaDbUtil.getConnection();
        if(null != connection)
        {
            System.out.println("A real connection");

            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables("mystats", null, "%", null);

            while(rs.next())
            {
                String tableName = rs.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);
            }
        } else {
            System.out.println("Naur.");
        }
    }
}
