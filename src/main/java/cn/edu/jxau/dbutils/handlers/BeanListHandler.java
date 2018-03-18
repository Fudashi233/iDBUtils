package cn.edu.jxau.dbutils.handlers;

import cn.edu.jxau.dbutils.BasicRowProcessor;
import cn.edu.jxau.dbutils.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/18
 * Time:下午4:14
 */
public class BeanListHandler<T> extends AbstractListHandler<T> {

    private List<T> list;
    private RowProcessor rowProcessor;
    private Class<T> clazz;

    public BeanListHandler(Class clazz) {
        this(new BasicRowProcessor(), clazz);
    }

    public BeanListHandler(RowProcessor rowProcessor, Class clazz) {
        list = new ArrayList<>();
        this.rowProcessor = rowProcessor;
        this.clazz = clazz;
    }

    @Override
    protected T handleRow(ResultSet resultSet) throws SQLException {
        return rowProcessor.toBean(resultSet, clazz);
    }
}