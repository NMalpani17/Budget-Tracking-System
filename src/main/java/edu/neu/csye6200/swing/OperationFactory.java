package edu.neu.csye6200.swing;
class OperationFactory {
    public static Operation createOperation(String operator) {
        switch (operator) {
            case "+":
                return new Addition();
            case "-":
                return new Subtraction();
            case "*":
                return new Multiplication();
            case "/":
                return new Division();
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
