package cn.edu.jxau.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/18
 * Time:上午11:36
 */
public class ColumnListHandler<T> extends AbstractListHandler<T> {

    private int columnIndex;
    private String columnName;

    public ColumnListHandler() {
        this(1);
    }

    public ColumnListHandler(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public ColumnListHandler(String columnName) {

        if (columnName == null || columnName.isEmpty()) {
            throw new IllegalArgumentException("columnName is null or empty");
        }
        this.columnName = columnName;
    }


    @Override
    protected T handleRow(ResultSet resultSet) throws SQLException {

        if (columnName != null) {
            return (T) resultSet.getObject(columnName);
        } else {
            return (T) resultSet.getObject(columnIndex);
        }
    }
}
