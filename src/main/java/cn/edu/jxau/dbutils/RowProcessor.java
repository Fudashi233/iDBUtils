package cn.edu.jxau.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/9
 * Time:下午8:47
 */
public interface RowProcessor {

    Object[] toArray(ResultSet resultSet) throws SQLException;

    <T> T toBean(ResultSet resultSet) throws SQLException;

    <T> List<T> toBeanList(ResultSet resultSet) throws SQLException;

    Map<String, Object> toMap(ResultSet resultSet) throws SQLException;
}
