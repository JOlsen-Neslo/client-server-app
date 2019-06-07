package za.co.neslotech.server.calculation.operations;

import za.co.neslotech.server.exceptions.CalculationException;

public interface IOperation {

    double execute(double firstNumber, double secondNumber) throws CalculationException;

    String getType();

    int getPriority();

}
