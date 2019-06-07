package za.co.neslotech.server.calculation.operations.impl;

import za.co.neslotech.server.calculation.operations.IOperation;
import za.co.neslotech.server.exceptions.DivideByZeroException;

public class DivideOperation implements IOperation {

    @Override
    public double execute(double firstNumber, double secondNumber) throws DivideByZeroException {
        if (secondNumber == 0) {
            throw new DivideByZeroException("It is not possible to divide by zero.");
        }
        return firstNumber / secondNumber;
    }

    @Override
    public String getType() {
        return "/";
    }

    @Override
    public int getPriority() {
        return 1;
    }

}
