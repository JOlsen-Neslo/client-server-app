package za.co.neslotech.server.calculation;

import za.co.neslotech.server.calculation.operations.IOperation;
import za.co.neslotech.server.calculation.operations.OperationFacade;
import za.co.neslotech.server.exceptions.BracketFoundException;
import za.co.neslotech.server.exceptions.CalculationException;
import za.co.neslotech.server.exceptions.NumberNotFoundException;
import za.co.neslotech.server.exceptions.OperandNotFoundException;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquationCalculator {

    private final Pattern numberPattern = Pattern.compile("\\d+(\\.\\d+)?");
    private final Pattern operatorPattern;

    private final OperationFacade operationFacade;

    public EquationCalculator() {
        this.operationFacade = new OperationFacade();
        this.operatorPattern = Pattern.compile("[" + String.join(",", operationFacade.getOperationTypes()) + "]");
    }

    public String execute(String equation) throws CalculationException, ReflectiveOperationException {
        List<Double> numbers = new ArrayList<>();
        List<IOperation> operations = new ArrayList<>();
        equation = equation.replaceAll("\\s", "");

        equation = performBrackets(equation);

        extractOperandFromEquation(equation, numbers, operations);
        int currentPriority = operationFacade.getMaxPriority();
        while (currentPriority >= 0) {
            for (int i = 0; i < operations.size(); i++) {
                if (operations.get(i).getPriority() == currentPriority) {
                    numbers.set(i, operations.get(i).execute(numbers.get(i), numbers.get(i + 1)));
                    operations.remove(i);
                    numbers.remove(i + 1);
                    i--;
                }
            }
            currentPriority--;
        }

        return String.valueOf(numbers.get(0));
    }

    private String performBrackets(String equation) throws CalculationException, ReflectiveOperationException {
        char[] characters = equation.toCharArray();

        Stack<Integer> indexes = new Stack<>();
        for (int i = 0; i < characters.length; i++) {
            char current = characters[i];
            if (current == '(') {
                indexes.push(i);
            } else if (current == ')') {
                try {
                    int openIndex = indexes.pop();
                    String bracket = equation.substring(openIndex + 1, i);
                    String result = execute(bracket);

                    StringBuilder buffer = new StringBuilder(equation);
                    equation = buffer.replace(openIndex, i + 1, result).toString();
                    characters = equation.toCharArray();
                    i = 0;
                } catch (EmptyStackException e) {
                    throw new BracketFoundException("There are an illegal number of brackets in your equation.");
                }
            }
        }

        if (!indexes.isEmpty()) {
            throw new BracketFoundException("There are an illegal number of brackets in your equation.");
        }

        return equation;
    }

    private void extractOperandFromEquation(String equation, List<Double> numbers, List<IOperation> operations) throws
            CalculationException, ReflectiveOperationException {

        Matcher numberMatcher = numberPattern.matcher(equation);
        equation = extractNextNumber(numberMatcher, numbers, equation);

        while (!equation.isEmpty()) {
            Matcher operatorMatcher = operatorPattern.matcher(equation);
            equation = extractNextOperand(operatorMatcher, operations, equation);

            numberMatcher = numberPattern.matcher(equation);
            equation = extractNextNumber(numberMatcher, numbers, equation);
        }
    }

    private String extractNextNumber(Matcher numberMatcher, List<Double> numbers, String equation)
            throws NumberNotFoundException {

        if (numberMatcher.find()) {
            String match = numberMatcher.group();
            if (match.equals(equation.substring(0, numberMatcher.end()))) {
                equation = equation.substring(numberMatcher.end());
                numbers.add(Double.valueOf(match));
            } else {
                throw new NumberNotFoundException("Invalid expression around " + equation + ", expected a number");
            }
        } else {
            throw new NumberNotFoundException("Invalid expression around " + equation + ", expected a number");
        }

        return equation;
    }

    private String extractNextOperand(Matcher operandMatcher, List<IOperation> operations, String equation)
            throws OperandNotFoundException, ReflectiveOperationException {

        if (operandMatcher.find()) {
            String match = operandMatcher.group();
            if (match.equals(equation.substring(0, operandMatcher.end()))) {
                equation = equation.substring(operandMatcher.end());
                operations.add(operationFacade.getOperation(match));
            } else {
                throw new OperandNotFoundException("Invalid expression around " + equation + ", expected an operand");
            }
        } else {
            throw new OperandNotFoundException("Invalid expression around " + equation + ", expected an operand");
        }

        return equation;
    }

}
