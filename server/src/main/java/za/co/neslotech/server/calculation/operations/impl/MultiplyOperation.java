package za.co.neslotech.server.calculation.operations.impl;

import za.co.neslotech.server.calculation.operations.IOperation;

public class MultiplyOperation implements IOperation {

    @Override
    public double execute(double firstNumber, double secondNumber) {
        return firstNumber * secondNumber;
    }

    @Override
    public String getType() {
        return "*";
    }

    @Override
    public int getPriority() {
        return 1;
    }

}
