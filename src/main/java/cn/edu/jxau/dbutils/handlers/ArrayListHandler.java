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
 * Time:下午4:18
 */
public class ArrayListHandler extends AbstractListHandler<Object[]> {

    private RowProcessor rowProcessor;

    public ArrayListHandler() {
        this(new BasicRowProcessor());
    }

    public ArrayListHandler(RowProcessor rowProcessor) {
        this.rowProcessor = rowProcessor;
    }

    @Override
    protected Object[] handleRow(ResultSet resultSet) throws SQLException {
        return rowProcessor.toArray(resultSet);
    }
}
