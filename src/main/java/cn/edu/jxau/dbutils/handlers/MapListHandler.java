package cn.edu.jxau.dbutils.handlers;

import cn.edu.jxau.dbutils.BasicRowProcessor;
import cn.edu.jxau.dbutils.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/18
 * Time:下午3:34
 */
public class MapListHandler extends AbstractListHandler<Map<String,Object>> {


    private RowProcessor rowProcessor;

    public MapListHandler() {
        this(new BasicRowProcessor());
    }

    public MapListHandler(RowProcessor rowProcessor) {
        this.rowProcessor = rowProcessor;
    }

    @Override
    protected Map<String, Object> handleRow(ResultSet resultSet) throws SQLException {
        return rowProcessor.toMap(resultSet);
    }
}
