package cn.edu.jxau.dbutils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        return this.update(connection, false, sql, null);
    }

    public int update(Connection connection, String sql, Object... params) throws SQLException {
        return this.update(connection, false, sql, params);
    }

    public int update(String sql) throws SQLException {
        return this.update(DBUtils.getConnection(super.getDataSource()), true, sql, null);
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

        check(connection, closeConn, sql);
        try {
            PreparedStatement preStatement = DBUtils.prepareStatement(connection, sql);
            super.fillStatement(preStatement, params);
            return preStatement.executeUpdate();
        } catch (SQLException e) {
            DBUtils.rethrow(e, sql, params);
        } finally {
            if (closeConn) {
                DBUtils.close(connection);
            }
        }
        return -1; //DBUtils.rethrow() 会抛出个异常，不可能会走到这
    }

    private void check(Connection connection, boolean closeConn, String sql) throws SQLException {

        if (connection == null) {
            throw new IllegalArgumentException("connection is null");
        }
        if (sql == null || "".equals(sql.trim())) {
            if (closeConn) {
                DBUtils.close(connection);
            }
            throw new IllegalArgumentException("sql is null or empty");
        }
    }

    //--------------------------------------
    // query
    //--------------------------------------

    //--------------------------------------
    // batch
    //--------------------------------------

    //--------------------------------------
    // insert
    //--------------------------------------

}
