package cn.edu.jxau.dbutils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/9
 * Time:下午8:50
 */
public class BasicRowProcessor implements RowProcessor {

    private BeanProcessor beanProcessor;

    public BasicRowProcessor() {
        this(new BeanProcessor());
    }

    public BasicRowProcessor(BeanProcessor beanProcessor) {
        this.beanProcessor = beanProcessor;
    }


    @Override
    public Object[] toArray(ResultSet resultSet) throws SQLException {

        int len = resultSet.getMetaData().getColumnCount();
        Object[] arr = new Object[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = resultSet.getObject(i + 1);
        }
        return arr;
    }

    @Override
    public <T> T toBean(ResultSet resultSet, Class<T> clazz) throws SQLException {
        return beanProcessor.toBean(resultSet, clazz);
    }

    @Override
    public <T> List<T> toBeanList(ResultSet resultSet, Class<T> clazz) throws SQLException {
        return beanProcessor.toBeanList(resultSet, clazz);
    }

    @Override
    public Map<String, Object> toMap(ResultSet resultSet) throws SQLException {

        Map<String, Object> map = new CaseInsensitiveHashMap();
        ResultSetMetaData resultSetMD = resultSet.getMetaData();
        int len = resultSetMD.getColumnCount();
        for (int i = 0; i < len; i++) {
            String columnName = resultSetMD.getColumnLabel(i+1);
            if(columnName == null || columnName.isEmpty()) {
                columnName = resultSetMD.getColumnName(i+1);
            }
            map.put(columnName,resultSet.getObject(i+1));
        }
        return map;
    }

    private static class CaseInsensitiveHashMap extends HashMap<String, Object> {

        @Override
        public boolean containsKey(Object key) {
            return super.containsKey(key.toString().toLowerCase(Locale.ENGLISH));
        }

        @Override
        public Object put(String key, Object value) {
            return super.put(key.toLowerCase(Locale.ENGLISH), value);
        }

        @Override
        public Object remove(Object key) {
            return super.remove(key.toString().toLowerCase(Locale.ENGLISH));
        }
    }
}
