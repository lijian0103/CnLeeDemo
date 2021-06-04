package cn.cnlee.test.javamethod;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapOperate {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");
        System.err.println(new Gson().toJson(map));
        Map<String, String> tempMap = new HashMap<>();

        //method one
        for (String key : map.keySet()) {
            tempMap.put(key.toUpperCase(), map.get(key));
        }

        //method two
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            tempMap.put(key.toUpperCase(), map.get(key));
        }

        //method three
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String,String> entry = iterator.next();
            String key = entry.getKey();
            tempMap.put(key.toUpperCase(), map.get(key));
        }

        map.clear();
        map.putAll(tempMap);

        System.err.println(new Gson().toJson(map));
    }

}