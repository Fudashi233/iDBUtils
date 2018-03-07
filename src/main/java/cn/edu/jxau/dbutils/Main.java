package cn.edu.jxau.dbutils;

import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.DriverManager.*;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/6
 * Time:上午9:08
 */
public class Main {

    public static void main(String[] args) throws SQLException {

        Connection connection = getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8","root","root");
        connection.close();
    }
}
