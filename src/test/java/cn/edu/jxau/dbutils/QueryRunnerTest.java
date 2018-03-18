package cn.edu.jxau.dbutils;

import cn.edu.jxau.dbutils.handlers.*;
import cn.edu.jxau.lang.Customer;
import cn.edu.jxau.lang.JDBCUtils;
import org.junit.Test;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        Object[][] params = {{"name", "contact", "tel", "email", "remark", "1996-09-20" },
                {"name", "contact", "tel", "email", "remark", "1996-09-21" },
                {"name", "contact", "tel", "email", "remark", "1996-09-22" }};
        int[] rows = qr.batch(sql, params);
        System.out.println(Arrays.toString(rows));
    }

    @Test
    public void insert() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "INSERT INTO t_customer VALUES(NULL,?,?,?,?,?,?)";
        Object[] params = {"name", "contact", "tel", "email", "remark", "1996-09-20" };
        BigInteger i = qr.insert(sql, new ColumnHandler<BigInteger>(), params);
        System.out.println(i);
    }

    @Test
    public void insertBatch() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "INSERT INTO t_customer VALUES(NULL,?,?,?,?,?,?)";
        Object[][] params = {{"name", "contact", "tel", "email", "remark", "1996-09-20" },
                {"name", "contact", "tel", "email", "remark", "1996-09-21" },
                {"name", "contact", "tel", "email", "remark", "1996-09-22" }};
        List<BigInteger> list = qr.insertBatch(sql, new ColumnListHandler<BigInteger>(), params);
        System.out.println(list);
    }

    @Test
    public void mapHandler() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from t_customer";

        Map<String, Object> map = qr.query(sql, new MapHandler());
        for (Map.Entry<String, Object> me : map.entrySet()) {
            System.out.println(me.getKey() + "=" + me.getValue());
        }
    }

    @Test
    public void mapListHandler() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from t_customer";

        List<Map<String, Object>> list = qr.query(sql, new MapListHandler());
        for (Map map : list) {
            System.out.println(map);
        }
    }

    @Test
    public void keyedHandler() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from t_customer";

        Map<Integer, Map<String, Object>> map = qr.query(sql, new KeyedHandler<Integer>());
        for (Integer key : map.keySet()) {
            System.out.println(key);
            System.out.println(map.get(key));
        }
    }

    @Test
    public void beanMapHandler() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from t_customer";

        Map<Integer, Customer> map = qr.query(sql, new BeanMapHandler<Integer,Customer>(Customer.class));
        for (Integer key : map.keySet()) {
            System.out.println(key);
            System.out.println(map.get(key));
        }
    }

    @Test
    public void beanListHandler() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from t_customer";

        List<Customer> list = qr.query(sql, new BeanListHandler<Customer>(Customer.class));
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    @Test
    public void arrayHandler() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from t_customer";

        Object[] arr = qr.query(sql, new ArrayHandler());
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void arrayListHandler() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from t_customer";

        List<Object[]> list = qr.query(sql, new ArrayListHandler());
        System.out.println(list);
    }
}
