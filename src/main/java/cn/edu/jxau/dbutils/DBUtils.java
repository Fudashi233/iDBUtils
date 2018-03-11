package cn.edu.jxau.dbutils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Arrays;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/5
 * Time:下午2:21
 */
public class DBUtils {

    private DBUtils() {
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

        if (connection != null) {
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

    public static void rollback(Connection connection) throws SQLException {

        if (connection != null) {
            connection.rollback();
        }
    }

    public static void rollbackQuietly(Connection connection) {

        try {
            rollback(connection);
        } catch (SQLException e) {
            // nothing to do
        }
    }

    public static void rollbackAndClose(Connection connection) throws SQLException {

        if (connection != null) {
            try {
                connection.rollback();
            } finally {
                connection.close();
            }
        }
    }

    public static void rollbackAndCloseQuietly(Connection connection) throws SQLException {

        try {
            rollbackAndClose(connection);
        } catch (SQLException e) {
            // nothing to do
        }
    }


    public static boolean loadDiver(String className) {

        try {
            Class.forName(className);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 完整的打印sql错误
     *
     * @param e
     */
    public static void printSQLException(SQLException e) {
        printSQLException(e, new PrintWriter(System.err));
    }

    public static void printSQLException(SQLException e, PrintWriter pw) {

        try {
            while (e != null) {
                e.printStackTrace(pw);
                e = e.getNextException();
                if (e != null) {
                    pw.println("NEXT SQLException");
                }
            }
        } finally {
            pw.close();
        }
    }

    /**
     * 完整的打印sql警告
     *
     * @param connection
     */
    public static void printSQLWarning(Connection connection) {
        printSQLWarning(connection, new PrintWriter(System.err));
    }

    public static void printSQLWarning(Connection connection, PrintWriter pw) {

        try {
            SQLWarning e = connection.getWarnings();
            while (e != null) {
                e.printStackTrace(pw);
                e = e.getNextWarning();
                if (e != null) {
                    pw.println("NEXT SQLWarning");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            pw.close();
        }
    }

    public static PreparedStatement prepareStatement(Connection conn, String sql) throws SQLException {

        if (sql == null || sql.isEmpty()) {
            throw new IllegalArgumentException("参数异常，sql is null or empty");
        }
        return conn.prepareStatement(sql);
    }

    /**
     * @param conn
     * @param sql
     * @param returnPK 是否返回主键
     * @return
     * @throws SQLException
     */
    public static PreparedStatement prepareStatement(Connection conn, String sql, boolean returnPK) throws SQLException {

        if (returnPK) {
            return conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } else {
            return conn.prepareStatement(sql);
        }
    }

    public static void rethrow(SQLException cause, String sql, Object... params) throws SQLException {

        StringBuilder message = new StringBuilder();
        message.append(cause == null ? "" : cause);
        message.append(" Query: ").append(sql).append("\n");
        message.append(" Parameters: ").
                append(params == null ? "[]" : Arrays.toString(params)).append("\n");

        SQLException e = new SQLException(message.toString(), cause.getSQLState(), cause.getErrorCode());
        e.setNextException(cause);
        throw e;
    }

    public static Connection getConnection(DataSource dataSource) throws SQLException {

        if (dataSource == null) {
            throw new IllegalArgumentException("dataSource is null，无法获取 connection");
        }
        return dataSource.getConnection();
    }
}
