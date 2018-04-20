package ru.hyndo.signmenu.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        makeJitOptimizeIt();
        long populateStarted = System.currentTimeMillis();
        Set<Long> many = new HashSet<>();
        long prev = 0;
        for (int i = 0; i < 10000000; i++) {
            many.add(prev++);
        }
        System.out.println("populate end " + (System.currentTimeMillis() - populateStarted));
        long forEachStarted = System.currentTimeMillis();

        long finalInt = many.parallelStream().mapToLong(a -> a).sum();
//        long finalInt = 0;
//        for(Long a : many) {
//            finalInt += a;
//        }
        System.out.println(finalInt);
        long endTime = System.currentTimeMillis() - forEachStarted;
        System.out.println("iterating time " + endTime);
    }

    private static void makeJitOptimizeIt() {
        ArrayList<Object> objects = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            objects.add(new Object());
        }
        Object toCompare = new Object();
        boolean b = objects.stream().allMatch(obj -> toCompare == obj);
        System.out.println(b);
    }

}
