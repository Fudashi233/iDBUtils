package cn.edu.jxau.dbutils;

import java.util.HashMap;
import java.util.Map;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/9
 * Time:下午9:12
 */
public class BeanProcessor {

    protected static final int PROPERTY_NOT_FOUND = -1;
    private static final Map<Class<?>, Object> defaultVal = new HashMap<>();

    static {
        defaultVal.put(Short.TYPE, Short.valueOf((short) 0));
        defaultVal.put(Integer.TYPE, Integer.valueOf(0));
        defaultVal.put(Byte.TYPE, Byte.valueOf((byte) 0));
        defaultVal.put(Float.TYPE, Float.valueOf(0));
        defaultVal.put(Double.TYPE, Double.valueOf(0));
        defaultVal.put(Long.TYPE, Long.valueOf(0));
        defaultVal.put(Boolean.TYPE, Boolean.FALSE);
        defaultVal.put(Character.TYPE, Character.valueOf((char) 0));
    }
}
