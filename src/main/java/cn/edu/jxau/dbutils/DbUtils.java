package cn.edu.jxau.dbutils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/5
 * Time:下午2:21
 */
public class DbUtils {

    private DbUtils() {
        throw new UnsupportedOperationException("请勿实例化工具类");
    }

    public static void close(Connection connection) throws SQLException {

        if (connection != null) {
            connection.close();
        }
    }

    public static void closeQuietly(Connection connection) {

        try {
            close(connection);
        } catch (SQLException e) {
            //nothing to do
        }
    }

    public static void close(ResultSet resultSet) throws SQLException {

        if (resultSet != null) {
            resultSet.close();
        }
    }

    public static void closeQuietly(ResultSet resultSet) {

        try {
            close(resultSet);
        } catch (SQLException e) {
            //nothing to do
        }
    }

    public static void close(Statement statement) throws SQLException {

        if (statement != null) {
            statement.close();
        }
    }

    public static void closeQuietly(Statement statement) {

        try {
            close(statement);
        } catch (SQLException e) {
            //nothing to do
        }
    }

    public static void commitAndClose(Connection connection) throws SQLException {

        if(connection != null) {
            try {
                connection.commit();
            } finally {
                connection.close();
            }
        }
    }

    public static void commitAndCloseQuietly(Connection connection) {

        try {
            commitAndClose(connection);
        } catch (SQLException e) {
            // nothing to do
        }
    }


}
