package com.knowdata.framework.study.designpattern.visitorpattern;

import org.junit.Test;

public class VisitorPatternTest {
    @Test
    public void testVisitorPattern() {
        ComputerPart computer = new Computer();
        computer.accept(new ComputerPartDisplayVisitor());
    }
}
/**
Displaying Mouse.
Displaying Keyboard.
Displaying Monitor.
Displaying Computer.
**/