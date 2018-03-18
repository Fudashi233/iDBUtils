package cn.edu.jxau.dbutils.handlers;

import cn.edu.jxau.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/18
 * Time:下午3:27
 */
public abstract class AbstractKeyedHandler<K, V> implements ResultSetHandler<Map<K, V>> {

    @Override
    public Map<K, V> handle(ResultSet resultSet) throws SQLException {

        Map<K,V> map = createMap();
        while(resultSet.next()) {
            map.put(createKey(resultSet),createValue(resultSet));
        }
        return map;
    }

    protected Map<K, V> createMap() {
        return new HashMap<K, V>();
    }

    protected abstract K createKey(ResultSet resultSet) throws SQLException;

    protected abstract V createValue(ResultSet resultSet) throws SQLException;
}
