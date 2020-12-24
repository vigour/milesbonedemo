package com.milesbone.test;

import java.lang.reflect.Method;
import java.util.List;

public class Runtimes {
    public static long invokeStaticMethod(String clsName, String methodName,
            Object[] args) throws Exception {
        long start = System.nanoTime();
        try {
            Class c = Class.forName(clsName);
            Class[] argsClass = new Class[] {List.class};
            Method method = c.getMethod(methodName, argsClass);
            method.invoke(c, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.nanoTime();
        return end - start;
    }
}
