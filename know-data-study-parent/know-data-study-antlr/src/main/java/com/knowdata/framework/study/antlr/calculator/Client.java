package com.knowdata.framework.study.antlr.calculator;

import com.knowdata.framework.study.antlr.calculator.autogen.CalculatorLexer;
import com.knowdata.framework.study.antlr.calculator.autogen.CalculatorParser;
import com.knowdata.framework.study.antlr.calculator.autogen.CalculatorVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Client {
    public static void main(String[] args) {
        String query = "3.1 * (6.3 - 4.51) + 5 * 4";

        //词法分析器
        CalculatorLexer lexer = new CalculatorLexer(new ANTLRInputStream(query));

        //语法解析器
        CalculatorParser parser = new CalculatorParser(new CommonTokenStream(lexer));

        //访问者
        CalculatorVisitor visitor = new MyCalculatorVisitor();

        CalculatorParser.ExprContext exprContext = parser.expr();

        System.out.println(visitor.visit( exprContext ));  // 25.549
    }
}
