package cn.edu.jxau.dbutils.handlers;

import cn.edu.jxau.dbutils.BasicRowProcessor;
import cn.edu.jxau.dbutils.ResultSetHandler;
import cn.edu.jxau.dbutils.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/18
 * Time:下午4:18
 */
public class ArrayHandler implements ResultSetHandler<Object[]> {

    private static final Object[] EMPTY_ARRAY = new Object[0];

    private RowProcessor rowProcessor;

    public ArrayHandler() {
        this(new BasicRowProcessor());
    }

    public ArrayHandler(RowProcessor rowProcessor) {
        this.rowProcessor = rowProcessor;
    }

    @Override
    public Object[] handle(ResultSet resultSet) throws SQLException {
        return resultSet.next() ? rowProcessor.toArray(resultSet) : EMPTY_ARRAY;
    }
}
