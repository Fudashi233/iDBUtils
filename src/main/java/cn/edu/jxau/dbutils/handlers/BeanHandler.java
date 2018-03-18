package cn.edu.jxau.dbutils.handlers;

import cn.edu.jxau.dbutils.BasicRowProcessor;
import cn.edu.jxau.dbutils.RowProcessor;

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

    private Class<T> clazz;
    private RowProcessor rowProcessor;

    public BeanHandler(Class<T> clazz) {
        this(clazz,new BasicRowProcessor());
    }

    public BeanHandler(Class<T> clazz, RowProcessor rowProcessor) {

        this.clazz = clazz;
        this.rowProcessor = rowProcessor;
    }

    @Override
    public T handle(ResultSet resultSet) throws SQLException {
        return resultSet.next() ? rowProcessor.toBean(resultSet,clazz) : null;
    }
}
