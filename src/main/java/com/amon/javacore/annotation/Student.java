package com.amon.javacore.annotation;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/2/1.
 */
public class Student {

    @FiledName(value = "StudentName")
    private String name;
    @FiledName(value = "StudentAge")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
