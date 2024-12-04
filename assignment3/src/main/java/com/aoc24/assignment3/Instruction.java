package com.aoc24.assignment3;

public class Instruction {
    public Boolean enabled;
    public String method;
    public int operand1;
    public int operand2;

    public Instruction(String pMethod, int pOperand1, int pOperand2, Boolean pEnabled) {
        enabled = pEnabled;
        method = pMethod;
        operand1 = pOperand1;
        operand2 = pOperand2;
    }
}
