package cn.edu.jxau.dbutils;

import cn.edu.jxau.lang.Customer;
import cn.edu.jxau.lang.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class BeanProcessorTest {


    @Test
    public void toBean() throws Exception {

        BeanProcessor beanProcessor = new BeanProcessor();
        Connection connection = JDBCUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM t_customer WHERE id = 17");
        resultSet.next();
        Customer customer = beanProcessor.toBean(resultSet, Customer.class);
        System.out.println(customer);
    }

    @Test
    public void toBeanList() throws Exception {

        BeanProcessor beanProcessor = new BeanProcessor();
        Connection connection = JDBCUtils.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM t_customer");
        List<Customer> list = beanProcessor.toBeanList(resultSet, Customer.class);
        System.out.println(list);
    }
}