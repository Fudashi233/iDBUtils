package cn.edu.jxau.dbutils;

import cn.edu.jxau.dbutils.handlers.BeanHandler;
import cn.edu.jxau.dbutils.handlers.ColumnHandler;
import cn.edu.jxau.lang.Customer;
import cn.edu.jxau.lang.JDBCUtils;
import org.junit.Test;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

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
            System.out.println(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void batch() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "INSERT INTO t_customer VALUES(NULL,?,?,?,?,?,?)";
        Object[][] params = {{"name","contact","tel","email","remark","1996-09-20"},
                {"name","contact","tel","email","remark","1996-09-21"},
                {"name","contact","tel","email","remark","1996-09-22"}};
        int[] rows = qr.batch(sql,params);
        System.out.println(Arrays.toString(rows));
    }

    @Test
    public void insert() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "INSERT INTO t_customer VALUES(NULL,?,?,?,?,?,?)";
        Object[] params = {"name","contact","tel","email","remark","1996-09-20"};
        BigInteger i  = qr.insert(sql,new ColumnHandler<BigInteger>(), params);
        System.out.println(i);
    }
}
