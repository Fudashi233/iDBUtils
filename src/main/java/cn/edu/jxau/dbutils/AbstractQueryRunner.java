package cn.edu.jxau.dbutils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/5
 * Time:下午2:19
 */
public abstract class AbstractQueryRunner {

    /**
     * 如果 ParameterMetaData.getParameterType() 方法会抛出异常，
     * 则 pmdKnownBroken 就应当为 true，比如 Oracle 数据库
     * 使pmdKnownBroken线程可见
     */
    private volatile boolean pmdKnownBroken;

    private DataSource dataSource;

    public AbstractQueryRunner() {

    }

    public AbstractQueryRunner(boolean pmdKnownBroken) {
        this.pmdKnownBroken = pmdKnownBroken;
    }

    public AbstractQueryRunner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public AbstractQueryRunner(DataSource dataSource, boolean pmdKnownBroken) {
        this.dataSource = dataSource;
        this.pmdKnownBroken = pmdKnownBroken;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public boolean isPmdKnownBroken() {
        return pmdKnownBroken;
    }

    protected PreparedStatement prepareStatement(Connection conn, String sql) throws SQLException {

        if (sql == null || "".equals(sql.trim())) {
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
    protected PreparedStatement prepareStatement(Connection conn, String sql, boolean returnPK) throws SQLException {

        if (returnPK) {
            return conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } else {
            return conn.prepareStatement(sql);
        }
    }

    protected Connection getConnection() throws SQLException {

        if (dataSource == null) {
            throw new NullPointerException("dataSource is null，无法获取 connection");
        }
        return dataSource.getConnection();
    }


}
