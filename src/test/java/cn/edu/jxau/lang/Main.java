package cn.edu.jxau.lang;

import cn.edu.jxau.dbutils.DBUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
 * | remark    | varcha) | NO   |     | NULL    |                |
 * +-----------+--------------+------+-----+---------+----------------+
 */
public class Main {


    @Test
    public void test() throws SQLException {

        boolean ret = DbUtils.loadDriver("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8", "root", "root");
        System.out.println(connection);
    }

    @Test
    public void loadFromXml() {
        Properties prop = new Properties();

        // add some properties
        prop.put("Height", "200");
        prop.put("Width", "15");

        try {

            // create a output and input as a xml file
            FileOutputStream fos = new FileOutputStream("properties.xml");
            FileInputStream fis = new FileInputStream("properties.xml");

            // store the properties in the specific xml
            prop.storeToXML(fos, null);

            // load from the xml that we saved earlier
            prop.loadFromXML(fis);

            // print the properties list
            prop.list(System.out);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void insert() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "INSERT INTO t_customer values(NULL, NULL, NULL, NULL, NULL, NULL)";
        Object[] params = {1, null, null, null, null};
        try {
            qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public void testBatch() {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "INSERT INTO t_customer values(NULL, ?, ?, ?, ?, ?)";

        Object params[][] = new Object[10][];
        for (int i = 0; i < 10; i++) {
            params[i] = new Object[]{null, "0" + i + "Fudashi", "150123456789", "123@qq.com", "no"};
        }
        try {
            qr.batch(sql, params);
        } catch (SQLException ex) {
            System.err.println();
            System.err.println();
            System.err.println();
            while(ex != null) {
                ex.printStackTrace();
                System.err.println("-----------------------------------");
                ex = ex.getNextException();
            }
        }
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

        Map<String, Object> map = (Map) qr.query(sql, new MapHandler());
        for (Map.Entry<String, Object> me : map.entrySet()) {
            System.out.println(me.getKey() + "=" + me.getValue());
        }
    }
}
