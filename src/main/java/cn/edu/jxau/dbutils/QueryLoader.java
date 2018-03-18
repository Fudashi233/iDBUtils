package cn.edu.jxau.dbutils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/3/18
 * Time:下午2:40
 */
public class QueryLoader {

    private static final Pattern DOT_XML = Pattern.compile(".+\\.[xX][mM][lL]$");

    private static final String DEFAULT_NAMESPACE = "DEFAULT";

    private static Map<String, Map<String, String>> queries = new HashMap<>();

    private QueryLoader() {
        throw new UnsupportedOperationException("请勿实例化工具类QueryLoader");
    }


    public static synchronized String get(String key) {
        return get(DEFAULT_NAMESPACE, key);
    }

    public static synchronized String get(String namespace, String key) {

        if (namespace == null || namespace.isEmpty()) {
            namespace = DEFAULT_NAMESPACE;
        }
        if (key == null || key.isEmpty()) {
            throw new IllegalStateException("参数异常，key is null or empty");
        }
        Map<String, String> query = queries.get(namespace);
        return query == null ? null : query.get(key);
    }

    public static synchronized void load(String path) throws IOException {
        load(null,path);
    }

    public static synchronized void load(String namespace, String path) throws IOException {

        if (namespace == null || namespace.isEmpty()) {
            namespace = DEFAULT_NAMESPACE;
        }
        if (path == null || path.isEmpty()) {
            throw new IllegalStateException("参数异常，path is null or empty");
        }
        queries.put(namespace, load0(path));
    }

    private static Map<String, String> load0(String path) throws IOException {

        InputStream in = QueryLoader.class.getResourceAsStream(path);
        System.out.println(QueryLoader.class.getResource(path));
        Properties prop = new Properties();
        try {
            if (DOT_XML.matcher(path).matches()) {
                prop.loadFromXML(in);
            } else {
                prop.load(in);
            }
        } catch (IOException e) {
            throw new IOException("读取文件失败，path=" + path, e);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return new HashMap<String, String>((Map) prop);
    }
}
