package com.knowdata.framework.study.designpattern.iteratorpattern;

import java.util.Iterator;
/**
 * 抽象的集合迭代器（Iterator）：学生集合的迭代器
 * 实现Iterator接口
 * 负责定义访问和遍历元素的接口，例如提供hasNext()和next()方法。
 */
public interface StudentCollectionIterator extends Iterator<Student> {

}
