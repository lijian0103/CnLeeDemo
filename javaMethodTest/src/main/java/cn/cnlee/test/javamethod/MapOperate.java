package cn.cnlee.test.javamethod;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapOperate {

    public static void main(String[] args) {
        testArrayContains();
    }

    private static void testArrayContains() {
        int[] arr = new int[]{1,2,3,3};
        System.err.println(Arrays.asList(arr).contains(2));
        long xx = Arrays.stream(arr).filter(it -> it == 4).count();
        System.err.println(xx);
    }

    private static void testFIFOMap() {
        HashMap<Integer, Integer> map = new LinkedHashMap<Integer, Integer>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > 2;
            }
        };
        map.put(3, 33);
        map.put(1, 11);
        map.put(2, 22);
        System.err.println(map);
        map.put(5, 55);
        map.put(5, 88);
        System.err.println(map);

    }

    private static void testMapSetVal() {
        Map<Integer, Map<Integer, int[]>> map = new HashMap<>();
        Map<Integer, int[]> childrenMap = new HashMap<>();
        childrenMap.put(0, new int[]{1, 2, 3});
        childrenMap.put(1, new int[]{5, 6, 7});
        map.put(11, childrenMap);
        childrenMap = new HashMap<>();
        childrenMap.put(0, new int[]{4, 6, 8});
        childrenMap.put(1, new int[]{3, 5, 7});
        map.put(22, childrenMap);
        System.err.println(new Gson().toJson(map));
    }

    private static void testMapUpdateKey() {
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
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            tempMap.put(key.toUpperCase(), map.get(key));
        }

        map.clear();
        map.putAll(tempMap);

        System.err.println(new Gson().toJson(map));
    }
}