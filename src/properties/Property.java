package properties;
/**
 * 实现对Java配置文件Properties的读取、写入与更新操作
 */

import java.io.*;
import java.util.Properties;


public class Property {
    //属性文件的路径
    static String profilePath ="data.properties";
    /**
     * 采用静态方法
     */
    private static Properties props = new Properties();
    static {
        try {
            FileInputStream fileInputStream = new FileInputStream(profilePath);
            InputStreamReader reader = new InputStreamReader(fileInputStream,"Utf-8");
            props.load(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    public Property() throws FileNotFoundException {
    }

    public static String getKeyValue(String key) {
        return props.getProperty(key);
    }

    public static void writeProperties(String key,String value) {
        try {
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            OutputStream output = new FileOutputStream(profilePath);
            OutputStreamWriter writer = new OutputStreamWriter(output, "utf-8");
            props.setProperty(key, value);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            props.store(writer, "Update '" + key + "' value");
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }

    public static void updateProperties(String key, String value) {
        try {
            OutputStream output = new FileOutputStream(profilePath);
            OutputStreamWriter writer = new OutputStreamWriter(output, "utf-8");
            props.setProperty(key, value);
            props.store(writer, "Update '" + key + "' value");
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }
    //测试代码
    public static void main(String[] args) {
        System.out.println(getKeyValue("name"));
        updateProperties("ID", "100000");
        System.out.println("操作完成");
    }
}

