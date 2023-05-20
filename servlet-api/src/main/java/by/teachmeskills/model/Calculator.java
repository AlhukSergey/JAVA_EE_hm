package by.teachmeskills.model;

public class Calculator {
    private int operand1;
    private int operand2;

    public Calculator(int operand1, int operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public double sum() {
        return operand1 + operand2;
    }

    public double substr() {
        return operand1 - operand2;
    }

    public double multiply() {
        return operand1 * operand2;
    }

    public double div() {
        return (double) operand1 / operand2;
    }
}
