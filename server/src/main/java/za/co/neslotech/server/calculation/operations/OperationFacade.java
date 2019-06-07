package za.co.neslotech.server.calculation.operations;

import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OperationFacade {

    private List<Class<? extends IOperation>> operationClasses;

    public OperationFacade() {
        Reflections reflections = new Reflections("za.co.neslotech.server.calculation.operations.impl");
        operationClasses = new ArrayList<>(reflections.getSubTypesOf(IOperation.class));
    }

    public double calculate(double firstNumber, double secondNumber, String operationType) throws Exception {
        for (Class<? extends IOperation> operationClass : operationClasses) {
            IOperation operation = operationClass.newInstance();
            if (operationType.equals(operation.getType())) {
                return operation.execute(firstNumber, secondNumber);
            }
        }

        throw new Exception("Operation not found");
    }

    public IOperation getOperation(String operationType) throws InstantiationException, IllegalAccessException {
        for (Class<? extends IOperation> operationClass : operationClasses) {
            IOperation operation = operationClass.newInstance();
            if (operationType.equals(operation.getType())) {
                return operation;
            }
        }

        throw new RuntimeException("Operation not found");
    }

    public List<String> getOperationTypes() {
        List<String> types = operationClasses.stream().map(x -> {
            String type = null;
            try {
                type = x.newInstance().getType();
            } catch (InstantiationException | IllegalAccessException e) {
                System.err.println("Operation type cannot be found: " + e.getMessage());
            }
            return type;
        }).collect(Collectors.toList());
        types.removeAll(Collections.singletonList(null));

        return types;
    }

    public int getMaxPriority() throws InstantiationException, IllegalAccessException {
        int maxPriority = 0;
        for (Class<? extends IOperation> operationClass : operationClasses) {
            IOperation operation = operationClass.newInstance();
            if (operation.getPriority() > maxPriority) {
                maxPriority = operation.getPriority();
            }
        }

        return maxPriority;
    }

}
