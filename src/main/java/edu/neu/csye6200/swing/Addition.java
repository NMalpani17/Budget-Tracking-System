package edu.neu.csye6200.swing;

import edu.neu.csye6200.swing.Operation;

// Addition operation
class Addition implements Operation {
    @Override
    public double performOperation(double num1, double num2) {
        return num1 + num2;
    }
}