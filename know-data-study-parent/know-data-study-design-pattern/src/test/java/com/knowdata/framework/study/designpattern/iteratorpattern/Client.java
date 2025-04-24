package com.knowdata.framework.study.designpattern.iteratorpattern;

//import junit.framework.TestCase;

import org.junit.Test;

//@SpringBootTest
public class Client {
    @Test
    public void testIterator(){
        SchoolClass schoolClass = new SchoolClass();
        // 添加学生信息
        schoolClass.add(new Student("张三", 18));
        schoolClass.add(new Student("李四", 19));
        schoolClass.add(new Student("王五", 20));

        // 获取迭代器，遍历学生信息
        StudentCollectionIterator iterator = schoolClass.iterator();

        while(iterator.hasNext()) {
            Student student = iterator.next();
            System.out.println("学生姓名：" + student.getName() + "，学生年龄：" + student.getAge());
        }
    }
}
/**
//output:
学生姓名：张三，学生年龄：18
学生姓名：李四，学生年龄：19
学生姓名：王五，学生年龄：20
**/