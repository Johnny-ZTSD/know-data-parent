package com.knowdata.framework.study.designpattern.visitorpattern;

public interface ComputerPart {
    public void accept(ComputerPartVisitor computerPartVisitor);
}