package edu.neu.csye6200.swing;

import edu.neu.csye6200.swing.Operation;

class Division implements Operation {
    @Override
    public double performOperation(double num1, double num2) {
        if (num2 != 0) {
            return num1 / num2;
        } else {
            throw new ArithmeticException("Cannot divide by zero");
        }
    }
}
