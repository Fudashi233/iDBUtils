package cn.edu.jxau.dbutils;

import cn.edu.jxau.dbutils.handlers.ColumnHandler;
import cn.edu.jxau.dbutils.handlers.ResultSetHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/5
 * Time:下午2:18
 */
public class QueryRunner extends AbstractQueryRunner {

    public QueryRunner() {
        super();
    }

    public QueryRunner(boolean pmdKnownBroken) {
        super(pmdKnownBroken);
    }

    public QueryRunner(DataSource dataSource) {
        super(dataSource);
    }

    public QueryRunner(DataSource dataSource, boolean pmdKnownBroken) {
        super(dataSource, pmdKnownBroken);
    }

    //--------------------------------------
    // update
    //--------------------------------------
    public int update(Connection connection, String sql) throws SQLException {
        return this.update(connection, false, sql);
    }

    public int update(Connection connection, String sql, Object... params) throws SQLException {
        return this.update(connection, false, sql, params);
    }

    public int update(String sql) throws SQLException {
        return this.update(DBUtils.getConnection(super.getDataSource()), true, sql);
    }

    public int update(String sql, Object... params) throws SQLException {
        return this.update(DBUtils.getConnection(super.getDataSource()), true, sql, params);
    }


    /**
     * 如果未使用线程池或者使用了事务，建议 closeConn 置为 false
     *
     * @param connection
     * @param closeConn
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    private int update(Connection connection, boolean closeConn, String sql, Object... params) throws SQLException {

        params = params == null ? new Object[0] : params;
        check(connection, closeConn, sql);
        int result = 0;
        try {
            PreparedStatement preStatement = DBUtils.prepareStatement(connection, sql);
            super.fillStatement(preStatement, params);
            result = preStatement.executeUpdate();
        } catch (SQLException e) {
            DBUtils.rethrow(e, sql, params);
        } finally {
            if (closeConn) {
                DBUtils.close(connection);
            }
        }
        return result;
    }

    private void check(Connection connection, boolean closeConn, String sql) throws SQLException {

        if (connection == null) {
            throw new IllegalArgumentException("connection is null");
        }
        if (sql == null || sql.isEmpty()) {
            if (closeConn) {
                DBUtils.close(connection);
            }
            throw new IllegalArgumentException("sql is null or empty");
        }
    }

    //--------------------------------------
    // query
    //--------------------------------------

    public <T> T query(Connection connection, String sql, ResultSetHandler<T> handler) throws SQLException {
        return this.query(connection, false, sql, handler);
    }

    public <T> T query(Connection connection, String sql, ResultSetHandler<T> handler, Object... params) throws SQLException {
        return this.query(connection, false, sql, handler, params);
    }

    public <T> T query(String sql, ResultSetHandler<T> handler) throws SQLException {
        return this.query(DBUtils.getConnection(super.getDataSource()), true, sql, handler);
    }

    public <T> T query(String sql, ResultSetHandler<T> handler, Object... params) throws SQLException {
        return this.query(DBUtils.getConnection(super.getDataSource()), true, sql, handler, params);
    }

    private <T> T query(Connection connection, boolean closeConn, String sql, ResultSetHandler<T> handler, Object... params) throws SQLException {

        params = params == null ? new Object[0] : params;
        check(connection, closeConn, sql);
        if (handler == null) {
            if (closeConn) {
                DBUtils.close(connection);
            }
            throw new IllegalArgumentException("handler is null");
        }
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        T result = null;
        try {
            preStatement = DBUtils.prepareStatement(connection, sql);
            super.fillStatement(preStatement, params);
            resultSet = this.wrap(preStatement.executeQuery());
            return handler.handle(resultSet);
        } catch (SQLException e) {
            DBUtils.rethrow(e, sql, params);
        } finally {
            try {
                DBUtils.close(resultSet);
            } finally {
                try {
                    DBUtils.close(preStatement);
                } finally {
                    if (closeConn) {
                        DBUtils.close(connection);
                    }
                }
            }
        }
        return result;
    }

    //--------------------------------------
    // batch
    //--------------------------------------
    public int[] batch(Connection connection, String sql, Object[][] params) throws SQLException {
        return batch(connection, false, sql, params);
    }

    public int[] batch(String sql, Object[][] params) throws SQLException {
        return batch(DBUtils.getConnection(super.getDataSource()), true, sql, params);
    }

    private int[] batch(Connection connection, boolean closeConn, String sql, Object[][] params) throws SQLException {

        check(connection, closeConn, sql);
        if (params == null) {
            if (closeConn) {
                DBUtils.close(connection);
            }
            throw new SQLException("params is null");
        }
        int[] rows = null;
        PreparedStatement statement = null;
        try {
            statement = DBUtils.prepareStatement(connection, sql);
            for (int i = 0; i < params.length; i++) {
                fillStatement(statement, params[i]);
                statement.addBatch();
                ;
            }
            rows = statement.executeBatch();
        } catch (SQLException e) {
            DBUtils.rethrow(e, sql, params);
        } finally {
            DBUtils.close(statement);
            if (closeConn) {
                DBUtils.close(connection);
            }
        }
        return rows;
    }

    //--------------------------------------
    // insert
    //--------------------------------------
    public <T> T insert(Connection connection, String sql, ColumnHandler<T> handler) throws SQLException {
        return insert(connection, false, sql, handler, null);
    }

    public <T> T insert(Connection connection, String sql, ColumnHandler<T> handler, Object... params) throws SQLException {
        return insert(connection, false, sql, handler, params);
    }

    public <T> T insert(String sql, ColumnHandler<T> handler) throws SQLException {
        return insert(DBUtils.getConnection(super.getDataSource()), true, sql, handler, null);
    }

    public <T> T insert(String sql, ColumnHandler<T> handler, Object... params) throws SQLException {
        return insert(DBUtils.getConnection(super.getDataSource()), true, sql, handler, params);
    }

    private <T> T insert(Connection connection, boolean closeConn, String sql, ResultSetHandler<T> handler, Object... params) throws SQLException {

        params = params == null ? new Object[0] : params;
        check(connection, closeConn, sql);
        if (handler == null) {
            if (closeConn) {
                DBUtils.close(connection);
            }
            throw new IllegalArgumentException("handler is null");
        }
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        T result = null;
        try {
            preStatement = DBUtils.prepareStatement(connection, sql, true);
            super.fillStatement(preStatement, params);
            preStatement.execute();
            resultSet = preStatement.getGeneratedKeys();
            result = handler.handle(resultSet);
        } catch (SQLException e) {
            DBUtils.rethrow(e, sql, params);
        } finally {
            try {
                DBUtils.close(resultSet);
            } finally {
                try {
                    DBUtils.close(preStatement);
                } finally {
                    if (closeConn) {
                        DBUtils.close(connection);
                    }
                }
            }
        }
        return result;
    }

    //--------------------------------------
    // insertBatch
    //--------------------------------------

}
