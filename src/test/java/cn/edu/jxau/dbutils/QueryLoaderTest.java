package cn.edu.jxau.dbutils;

import org.junit.Test;

import java.io.IOException;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/18
 * Time:下午3:09
 */
public class QueryLoaderTest {

    @Test
    public void load() throws IOException {

        QueryLoader.load("/sql.properties");
        System.out.println(QueryLoader.get("insertCustomer"));
    }
}
