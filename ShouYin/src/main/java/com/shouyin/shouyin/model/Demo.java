package com.shouyin.shouyin.model;

import java.sql.Array;

import static ch.qos.logback.core.joran.action.ActionConst.NULL;

/**
 * @author ssddp
 * @ClassNameDemo
 * @Description: a
 * @date 2019/4/18 15:05
 * @Version 1.0
 **/
public class Demo {
    public static void main(String [] args){
        String q = "a3sf43resf";
        String w = q.replaceAll("[A-za-z]","");
       char [] e=w.toCharArray();
        int t=0;
        for (int i=0;i<e.length;i++) {
            t += Integer.parseInt(String.valueOf(e[i]));
        }
        System.out.println(t);
    }
}
