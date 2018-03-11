package cn.edu.jxau.dbutils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/9
 * Time:下午9:12
 */
public class BeanProcessor {

    protected static final int PROPERTY_NOT_FOUND = -1;

    private final Map<String, String> columnToPropertyOverrides;

    public BeanProcessor() {
        this(Collections.EMPTY_MAP);
    }

    public BeanProcessor(Map<String, String> columnToPropertyOverrides) {
        if (columnToPropertyOverrides == null) {
            throw new IllegalArgumentException("columnToPropertyOverrides is null");
        }
        this.columnToPropertyOverrides = columnToPropertyOverrides;
    }

    /**
     * @param resultSet
     * @param clazz
     * @param <T>
     * @return
     * @throws SQLException
     */
    public <T> T toBean(ResultSet resultSet, Class<T> clazz) throws SQLException {

        PropertyDescriptor[] propDescArr = getPropDescArr(clazz);
        int[] columnToProperty = getColumnToProperty(resultSet.getMetaData(), propDescArr);
        return createBean(resultSet, clazz, propDescArr, columnToProperty);
    }

    /**
     * @param resultSet
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> toBeanList(ResultSet resultSet, Class<T> clazz) throws SQLException {

        List<T> list = new ArrayList<>();
        PropertyDescriptor[] propDescArr = getPropDescArr(clazz);
        int[] columnToProperty = getColumnToProperty(resultSet.getMetaData(), propDescArr);
        while (resultSet.next()) {
            list.add(createBean(resultSet, clazz, propDescArr, columnToProperty));
        }
        return list;
    }

    private PropertyDescriptor[] getPropDescArr(Class<?> clazz) throws SQLException {

        try {
            return Introspector.getBeanInfo(clazz).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new SQLException("内省失败", e);
        }
    }

    /**
     * 获取【数据库表属性】和【Java 类属性】的映射
     *
     * @return
     */
    private int[] getColumnToProperty(ResultSetMetaData resultSetMD, PropertyDescriptor[] propDescArr) throws SQLException {

        int[] columnToProperty = new int[resultSetMD.getColumnCount() + 1];
        Arrays.fill(columnToProperty, PROPERTY_NOT_FOUND);
        for (int i = 1; i < columnToProperty.length; i++) {
            String propName = getPropertyName(resultSetMD, i);
            for (int j = 0; j < propDescArr.length; j++) {
                if (propName.equalsIgnoreCase(propDescArr[j].getName())) {
                    columnToProperty[i] = j; // 映射
                    break;
                }
            }
        }
        return columnToProperty;
    }

    /**
     * 获取属性名
     *
     * @param resultSetMD
     * @param index
     * @return
     * @throws SQLException
     */
    private String getPropertyName(ResultSetMetaData resultSetMD, int index) throws SQLException {

        String columnName = resultSetMD.getColumnLabel(index);
        if (columnName == null || columnName.isEmpty()) {
            columnName = resultSetMD.getColumnName(index);
        }
        if (columnName == null || columnName.isEmpty()) {
            throw new RuntimeException("无法正确获取column name");
        }
        String propertyName = columnToPropertyOverrides.get(columnName);
        return propertyName == null ? columnName : propertyName;
    }

    private <T> T createBean(ResultSet resultSet, Class<T> type,
                             PropertyDescriptor[] propDescArr, int[] columnToProperty) throws SQLException {

        T target = newInstance(type);
        for (int i = 1; i < columnToProperty.length; i++) {
            if (columnToProperty[i] == PROPERTY_NOT_FOUND) {
                continue;
            }
            PropertyDescriptor propDesc = propDescArr[columnToProperty[i]];
            Object value = resultSet.getObject(i);
            set(target, propDesc, value);
        }
        return target;
    }

    private void set(Object target, PropertyDescriptor propDesc, Object value) throws SQLException {

        if (value == null) {
            return;
        }
        Method setter = propDesc.getWriteMethod();
        if (setter == null) {
            return;
        }
        Class<?>[] paramClassArr = setter.getParameterTypes();
        if (paramClassArr == null || paramClassArr.length > 1) {
            throw new RuntimeException("不符合Javabean规范，setter应当有且仅有一个参数");
        }
        Class<?> paramClass = paramClassArr[0];
        String paramClassName = paramClass.getName();
        if (value instanceof java.util.Date) { // 日期类型特殊处理，java.sql.Time，java.sql.Timestamp，java.sql.Date 都是 java.util.Date 的子类
            if ("java.sql.Time".equals(paramClassName)) {
                value = new java.sql.Time(((java.util.Date) value).getTime());
            } else if ("java.sql.Timestamp".equals(paramClassName)) {
                value = new java.sql.Timestamp(((java.util.Date) value).getTime());
            } else if ("java.sql.Date".equals(paramClassName)) {
                value = new java.sql.Date(((java.util.Date) value).getTime());
            }
        } else if (value instanceof String && paramClass.isEnum()) { //枚举类型特殊处理
            value = getEnumValue(paramClass, value);
        }
        try {
            setter.invoke(target, value);
        } catch (Exception e) {
            throw new SQLException("属性设置失败", e);
        }
    }

    private Object getDateValue(Object value) {

        String className = value.getClass().getName();
        if ("java.sql.Date".equals(className)) {
            java.sql.Date date = (java.sql.Date) value;
            return new Date(date.getTime());
        } else if ("java.sql.Time".equals(className)) {
            java.sql.Time date = (java.sql.Time) value;
            return new Date(date.getTime());
        } else if ("java.sql.Timestamp".equals(className)) {
            java.sql.Timestamp date = (java.sql.Timestamp) value;
            return new Date(date.getTime());
        } else {
            throw new IllegalArgumentException("不明确的数据库时间类型 " + className);
        }
    }

    private Object getEnumValue(Class<?> paramClass, Object value) {
        return Enum.valueOf(paramClass.asSubclass(Enum.class), (String) value);
    }

    private <T> T newInstance(Class<T> clazz) throws SQLException {

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new SQLException("cannot create " + clazz.getSimpleName(), e);
        }
    }

}
