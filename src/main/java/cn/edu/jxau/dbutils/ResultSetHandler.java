package cn.edu.jxau.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/9
 * Time:下午8:36
 */
public interface ResultSetHandler<T> {

    T handle(ResultSet resultSet) throws SQLException;
}
