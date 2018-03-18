package cn.edu.jxau.dbutils.handlers;

import cn.edu.jxau.dbutils.BasicRowProcessor;
import cn.edu.jxau.dbutils.ResultSetHandler;
import cn.edu.jxau.dbutils.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/18
 * Time:下午3:29
 */
public class MapHandler implements ResultSetHandler<Map<String, Object>> {

    private RowProcessor rowProcessor;

    public MapHandler() {
        this(new BasicRowProcessor());
    }

    public MapHandler(RowProcessor rowProcessor) {
        this.rowProcessor = rowProcessor;
    }

    @Override
    public Map<String, Object> handle(ResultSet resultSet) throws SQLException {

        return resultSet.next() ? rowProcessor.toMap(resultSet) : null;
    }
}
