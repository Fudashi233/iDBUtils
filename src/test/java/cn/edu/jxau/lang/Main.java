package cn.edu.jxau.lang;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/4
 * Time:上午10:05
 * <p>
 * 测试表：t_customer
 * +-----------+--------------+------+-----+---------+----------------+
 * | Field     | Type         | Null | Key | Default | Extra          |
 * +-----------+--------------+------+-----+---------+----------------+
 * | id        | int(11)      | NO   | PRI | NULL    | auto_increment |
 * | name      | varchar(128) | NO   |     | NULL    |                |
 * | contact   | varchar(128) | NO   |     | NULL    |                |
 * | telephone | varchar(128) | NO   |     | NULL    |                |
 * | email     | varchar(128) | NO   |     | NULL    |                |
 * | remark    | varchar(256) | NO   |     | NULL    |                |
 * +-----------+--------------+------+-----+---------+----------------+
 */
public class Main {

    @Test
    public void insert() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "INSERT INTO t_customer values(NULL, NULL, NULL, NULL, NULL, NULL)";
        Object[] params = {null, null, null, null, null};
        qr.update(sql, params);
    }

    @Test
    public void delete() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "DELETE FROM t_customer WHERE id = ?";
        qr.update(sql, 12);
    }

    @Test
    public void select() throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "SELECT * FROM t_customer WHERE id = ?";
        Object params[] = {2};
        Customer customer = (Customer) qr.query(sql, params, new BeanHandler(Customer.class));
        System.out.println(customer);
    }
}
