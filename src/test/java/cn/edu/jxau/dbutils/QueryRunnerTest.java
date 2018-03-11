package cn.edu.jxau.dbutils;

import cn.edu.jxau.lang.JDBCUtils;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/9
 * Time:下午7:43
 */
public class QueryRunnerTest {

    @Test
    public void insert() throws SQLException {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "INSERT INTO t_manuscript values(NULL,NULL,NULL,NULL,NULL,?,NULL,NULL)";
        Object[] params = {new Date()};
        try {
            qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
