package cn.cnlee.test.javamethod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/11/2
 * @Version 1.0
 */
public class TestStream {

    public static void main(String[] args) {
        System.out.println("=========");
        List<List<Integer>> all = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        List<Integer> list2 = new ArrayList<>();
        list2.add(3);
        list2.add(4);
        List<Integer> list3 = new ArrayList<>();
        list3.add(5);
        list3.add(6);
        all.add(list1);
        all.add(list2);
        all.add(list3);
        System.out.println(all);
        List<Integer> newList = all.stream().flatMap(Collection::stream).filter((item)->{
            System.out.println(item);
            return true;
        }).collect(Collectors.toList());
        System.out.println(newList);

    }
}
