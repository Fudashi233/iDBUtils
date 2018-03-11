package cn.edu.jxau.lang;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/4
 * Time:上午10:47
 */
@Data
public class Customer {

    private Integer id;
    private String name;
    private String contact;
    private String telephone;
    private String email;
    private String remark;
    private java.util.Date birthday;
    private java.util.Date birthday0;
}
