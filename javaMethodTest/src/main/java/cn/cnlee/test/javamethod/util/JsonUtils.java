package cn.cnlee.test.javamethod.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


/**
 * @Description JsonUtils
 * @Author cnlee
 * @Date 2021/6/9
 * @Version 1.0
 */
public class JsonUtils {
    public static void main(String[] args) {
//        String txt = "{\"key\":\"value\",\"xxx\":\"34343\"}";
        String txt = "零小束";
        System.err.println(txt + "|====|" + isJson(txt));
    }

    /**
     * judge txt is not json.
     *
     * @param txt txt
     * @return true if txt is json
     */
    public static boolean isJson(String txt) {
        boolean isJson = false;
        if (!StringUtils.isEmpty(txt)) {
            JsonElement jsonElement = JsonParser.parseString(txt);
            if (jsonElement != null) {
                isJson = jsonElement.isJsonObject();
            }
        }
        return isJson;
    }

}