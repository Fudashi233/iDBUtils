package cn.edu.jxau.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/18
 * Time:上午11:39
 */
public abstract class AbstractListHandler<T> implements ResultSetHandler<List<T>> {

    @Override
    public List<T> handle(ResultSet resultSet) throws SQLException {

        List<T> rows = new ArrayList<T>();
        while (resultSet.next()) {
            rows.add(this.handleRow(resultSet));
        }
        return rows;
    }

    protected abstract T handleRow(ResultSet resultSet) throws SQLException;
}
