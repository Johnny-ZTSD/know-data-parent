package com.knowdata.framework.study.designpattern.iteratorpattern;

import lombok.Data;
import lombok.ToString;

/**
 * 学生实体类
 */
@Data
@ToString
public class Student {
    private String name;
    private Integer age;

    public Student(String name,Integer age){
        this.age=age;
        this.name=name;
    }
}