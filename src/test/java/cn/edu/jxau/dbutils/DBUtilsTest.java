package cn.edu.jxau.dbutils;

import cn.edu.jxau.lang.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import java.sql.SQLException;

public class DBUtilsTest {
    @Test
    public void printStackTrace() throws Exception {

        org.apache.commons.dbutils.QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "INSERT INTO t_customer values(NULL, ?, ?, ?, ?, ?)";

        Object params[][] = new Object[10][];
        for (int i = 0; i < 10; i++) {
            params[i] = new Object[]{null, "0" + i + "Fudashi", "150123456789", "123@qq.com", "no"};
        }
        try {
            qr.batch(sql, params);
        } catch (SQLException ex) {
            DBUtils.printSQLException(ex);
        }
    }
}