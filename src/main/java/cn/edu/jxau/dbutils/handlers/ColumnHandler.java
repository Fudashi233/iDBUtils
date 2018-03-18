package cn.edu.jxau.dbutils.handlers;

import cn.edu.jxau.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/18
 * Time:上午10:43
 */
public class ColumnHandler<T> implements ResultSetHandler<T> {

    private int columnIndex;
    private String columnName;

    public ColumnHandler() {
        this(1);
    }

    public ColumnHandler(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public ColumnHandler(String columnName) {

        if(columnName == null || columnName.isEmpty()) {
            throw new IllegalArgumentException("columnName is null or empty");
        }
        this.columnName = columnName;
    }

    @Override
    public T handle(ResultSet resultSet) throws SQLException {

        while(resultSet.next()) {
            if(columnName != null) {
                return (T) resultSet.getObject(columnName);
            } else {
                return (T) resultSet.getObject(columnIndex);
            }
        }
        return null;
    }
}
