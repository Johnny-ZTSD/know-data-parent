package com.knowdata.framework.study.designpattern.iteratorpattern;

/**
 * 学生集合的聚合器: 抽象的聚合器（Aggregate）
 * 提供创建迭代器的接口，例如可以定义一个iterator()方法。
 */
public interface StudentAggregate {
    //用于创建具体的迭代器对象
    StudentCollectionIterator iterator();
    void add(Student student);
}