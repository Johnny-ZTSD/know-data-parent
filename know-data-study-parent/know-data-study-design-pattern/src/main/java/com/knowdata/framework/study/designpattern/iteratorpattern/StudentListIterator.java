package com.knowdata.framework.study.designpattern.iteratorpattern;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * 具体的集合迭代器（Concrete iterator）：StudentListIterator / StudentSetIterator / ...
 * 实现抽象迭代器定义的接口，负责实现对元素的访问和遍历。
 */
public class StudentListIterator implements StudentCollectionIterator {
    private List<Student> students;
    private int index;

    public StudentListIterator(List<Student> students) {
        this.students = students;
        this.index = 0;
    }

    //检查是否还有下一个元素
    @Override
    public boolean hasNext() {
        return (index < students.size());//数组的实现方式，与 链表的实现方式有所不同
    }

    //返回下一个元素
    @Override
    public Student next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Student student = students.get(index);
        index++;
        return student;
    }
}
