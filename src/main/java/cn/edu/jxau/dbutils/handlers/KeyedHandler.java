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
 * Time:下午3:38
 */
public class KeyedHandler<K> extends AbstractKeyedHandler<K, Map<String,Object>> {

    private RowProcessor rowProcessor;

    private int columnIndex;

    private String columnName;


    public KeyedHandler() {
        this(new BasicRowProcessor(),1,null);
    }

    public KeyedHandler(RowProcessor rowProcessor,int columnIndex,String columnName) {

        this.rowProcessor = rowProcessor;
        this.columnIndex = columnIndex;
        this.columnName = columnName;
    }

    @Override
    protected K createKey(ResultSet resultSet) throws SQLException {

        if(columnName != null) {
            return (K) resultSet.getObject(columnName);
        } else {
            return (K) resultSet.getObject(columnIndex);
        }
    }

    @Override
    protected Map<String, Object> createValue(ResultSet resultSet) throws SQLException {

        return rowProcessor.toMap(resultSet);
    }
}
