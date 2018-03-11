package cn.edu.jxau.dbutils;

import cn.edu.jxau.dbutils.handlers.BeanHandler;
import cn.edu.jxau.lang.Customer;
import cn.edu.jxau.lang.JDBCUtils;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/9
 * Time:下午7:43
 */
public class QueryRunnerTest {

    @Test
    public void update() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "INSERT INTO t_manuscript values(NULL,NULL,NULL,NULL,NULL,?,NULL,NULL)";
        Object[] params = {new Date()};
        try {
            qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void query() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "SELECT * FROM t_customer WHERE id = 1";
        try {
            Customer customer = qr.query(sql, new BeanHandler<Customer>(Customer.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void foo() throws SQLException {

        String str = query(new BeanHandler<String>());
    }

    public <T> T query(ResultSetHandler<T> handler) throws SQLException {
        return null;
    }

}
