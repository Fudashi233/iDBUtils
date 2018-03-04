package cn.edu.jxau.lang;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
    public void test() throws Exception {

        Customer customer = new Customer();
        Field field = Customer.class.getDeclaredField("id");
        field.setAccessible(true);
        field.setInt(customer, 1);
        //field.set(customer,1);
        System.out.println(customer.getId0());

    }

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

    @Test
    public void getAll() throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "SELECT * FROM t_customer";
        List list = (List) qr.query(sql, new BeanListHandler(Customer.class));
        System.out.println(list.size());
    }

    @Test
    public void testBatch() throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "INSERT INTO t_customer values(NULL, ?, ?, ?, ?, ?)";

        Object params[][] = new Object[10][];
        for (int i = 0; i < 10; i++) {
            params[i] = new Object[]{"Fudashi0" + i, "0" + i + "Fudashi", "150123456789", "123@qq.com", "no"};
        }
        qr.batch(sql, params);
    }

    @Test
    public void testColumnListHandler() throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from t_customer";
        List list = (List) qr.query(sql, new ColumnListHandler("id"));
        System.out.println(list);
    }

    @Test
    public void testMapHandler() throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from t_customer";

        List<Map> list = (List) qr.query(sql, new MapListHandler());
        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> me : map.entrySet()) {
                System.out.println(me.getKey() + "=" + me.getValue());
            }
        }
    }
}
