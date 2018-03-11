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

    /**
     * 将一条数据库记录转换成数组
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    Object[] toArray(ResultSet resultSet) throws SQLException;

    /**
     * 将一条数据库记录转换为Bean
     *
     * @param resultSet
     * @param clazz
     * @param <T>
     * @return
     * @throws SQLException
     */
    <T> T toBean(ResultSet resultSet, Class<T> clazz) throws SQLException;

    /**
     * 将多条数据库记录转换为List
     *
     * @param resultSet
     * @param clazz
     * @param <T>
     * @return
     * @throws SQLException
     */
    <T> List<T> toBeanList(ResultSet resultSet, Class<T> clazz) throws SQLException;

    /**
     * 将一条数据库记录转换为Map
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    Map<String, Object> toMap(ResultSet resultSet) throws SQLException;
}
