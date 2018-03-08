package cn.edu.jxau.dbutils;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/5
 * Time:下午2:19
 */
public abstract class AbstractQueryRunner {

    /**
     * DBUtils中说明确说明当，有的驱动调用 ParameterMetaData.getParameterType() 方法
     * 时可能会抛异常，比如 Oracle 驱动。但是看到不只这么一个会出现异常的地方（https://community.oracle.com/thread/3871711），
     * 所以，就干脆点，如果不能正确使用 ParameterMetaData
     * 则 pmdKnownBroken 就为 true，
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

    /**
     * 填充 SQL 语句的参数
     *
     * @param preparedStatement
     * @param params
     */
    public void fillStatement(PreparedStatement preparedStatement, Object... params) throws SQLException {

        if (params == null) {
            return;
        }
        checkParamCount(preparedStatement, params);
        for (int i = 0; i < params.length; i++) {
            fillStatement(preparedStatement, i + 1, params[i]);
        }
    }

    private void fillStatement(PreparedStatement preparedStatement, int index, Object param) throws SQLException {

        if (param == null) {
            int sqlType = Types.VARCHAR;
            if(!pmdKnownBroken) {
                try {
                    ParameterMetaData pmd = preparedStatement.getParameterMetaData();
                    sqlType = pmd.getParameterType(index);
                } catch(SQLException e) {
                    pmdKnownBroken = true;
                }
                preparedStatement.setNull(index,sqlType);
            }
        } else {
            preparedStatement.setObject(index, param);
        }
    }


    /**
     * 校验参数数量是否与 SQL 语句需要的参数数量一致
     *
     * @param preparedStatement
     * @param params
     * @return
     */
    private void checkParamCount(PreparedStatement preparedStatement, Object... params) throws SQLException {

        if (pmdKnownBroken) { //不进行校验
            return;
        }
        ParameterMetaData pmd = null;
        try {
            pmd = preparedStatement.getParameterMetaData();
        } catch (SQLException e) {
            pmdKnownBroken =  true;
        }
        int statementCount = pmd.getParameterCount(); //sql 所需的参数个数
        int parameterCount = params.length; //实际的参数个数
        if (statementCount != parameterCount) {
            throw new SQLException(String.format("参数不一致，sql所需的参数为%d,实际的参数个数为%d",
                    statementCount, parameterCount));
        }
    }
}
