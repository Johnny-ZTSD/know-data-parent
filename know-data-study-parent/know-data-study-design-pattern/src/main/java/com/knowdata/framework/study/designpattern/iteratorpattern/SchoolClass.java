package com.knowdata.framework.study.designpattern.iteratorpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * 学校班级 : 具体的聚合器（ConcreteAggregate）
 * 实现抽象聚合器定义的接口，负责创建具体的迭代器对象，并返回该对象。
 */
public class SchoolClass implements StudentAggregate {
    //本班级的学生
    private List<Student> students = new ArrayList<>();

    //创建迭代器对象
    @Override
    public StudentCollectionIterator iterator() {
        return new StudentListIterator(students);
    }

    //向班级名单中添加学生信息
    @Override
    public void add(Student student) {
        students.add(student);
    }
}
