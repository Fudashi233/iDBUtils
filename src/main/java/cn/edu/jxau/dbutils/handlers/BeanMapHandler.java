package cn.edu.jxau.dbutils.handlers;

import cn.edu.jxau.dbutils.BasicRowProcessor;
import cn.edu.jxau.dbutils.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/18
 * Time:下午4:07
 */
public class BeanMapHandler<K, V> extends AbstractKeyedHandler<K, V> {

    private RowProcessor rowProcessor;

    private int columnIndex;

    private String columnName;

    private Class<V> clazz;

    public BeanMapHandler(Class clazz) {
        this(new BasicRowProcessor(), clazz, 1, null);
    }

    public BeanMapHandler(RowProcessor rowProcessor, Class clazz, int columnIndex, String columnName) {

        this.rowProcessor = rowProcessor;
        this.clazz = clazz;
        this.columnIndex = columnIndex;
        this.columnName = columnName;
    }

    @Override
    protected K createKey(ResultSet resultSet) throws SQLException {

        if (columnName != null) {
            return (K) resultSet.getObject(columnName);
        } else {
            return (K) resultSet.getObject(columnIndex);
        }
    }

    @Override
    protected V createValue(ResultSet resultSet) throws SQLException {

        return rowProcessor.toBean(resultSet,clazz);
    }
}
