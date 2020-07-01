package com.amon.javacore.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/2/1.
 */
public class TestMain {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Student student = new Student();
        student.setName("Bruce");
        student.setAge(29);

        Field[] allFields = new Student().getClass().getDeclaredFields();

        Map<String,Object> outputMap = new HashMap<>(2);

        for (Field field : allFields) {

            if (field.isAnnotationPresent(FiledName.class)){
                FiledName fieldNameAnno = field.getAnnotation(FiledName.class);
                String value = fieldNameAnno.value();
                Method m = Student.class.getMethod("get" +upperCase1th(field.getName()));
                Object insValue = m.invoke(student);
                outputMap.put(value,insValue);
            }

        }

        for (Map.Entry<String, Object> entry : outputMap.entrySet()) {
            System.out.println( entry.getKey()+" / "+entry.getValue());
        }

    }

    public static String upperCase1th(String str){
             return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
