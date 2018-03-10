package cn.edu.jxau.dbutils.handlers;

import cn.edu.jxau.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/9
 * Time:下午8:45
 */
public class BeanHandler<T> implements ResultSetHandler<T> {

    @Override
    public T handle(ResultSet resultSet) throws SQLException {
        return null;
    }
}
