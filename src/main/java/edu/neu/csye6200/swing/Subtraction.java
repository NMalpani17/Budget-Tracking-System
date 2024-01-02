package edu.neu.csye6200.swing;

import edu.neu.csye6200.swing.Operation;

// Subtraction operation
class Subtraction implements Operation {
    @Override
    public double performOperation(double num1, double num2) {
        return num1 - num2;
    }
}
