package com.knowdata.framework.study.designpattern.visitorpattern;

//访问者接口 : 声明一系列访问方法，一个访问方法对应数据结构中的一个元素类。
public interface ComputerPartVisitor {
    public void visit(Computer computer);
    public void visit(Mouse mouse);
    public void visit(Keyboard keyboard);
    public void visit(Monitor monitor);
}
