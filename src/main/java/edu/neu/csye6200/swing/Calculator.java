package edu.neu.csye6200.swing;

import com.google.gson.Gson;
import edu.neu.csye6200.dto.CalculatorDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.port;

// Calculator GUI
public class Calculator extends JFrame {
    private JTextField display;

    private double currentNumber;
    private Operation currentOperation;
    public static Stack<CalculatorDto> operationStack = new Stack<>();

    public Calculator() {
        setTitle("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);

        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        setLayout(new BorderLayout());
        add(display, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        operationStack = new Stack<>();
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            if (buttonText.matches("[0-9]")) {
                // Append digits to the display
                display.setText(display.getText() + buttonText);
            } else if (buttonText.equals(".")) {
                // Handle decimal point
                if (!display.getText().contains(".")) {
                    display.setText(display.getText() + buttonText);
                }
            } else if (buttonText.equals("=")) {
                // Perform calculation
                try {
                    double result = currentOperation.performOperation(currentNumber, Double.parseDouble(display.getText()));
                    display.setText(String.valueOf(result));
                    currentNumber = result;
                } catch (NumberFormatException | ArithmeticException ex) {
                    display.setText("Error");
                }
            } else {
                // Handle operators
                try {
                    currentNumber = Double.parseDouble(display.getText());
                    currentOperation = OperationFactory.createOperation(buttonText);
                    display.setText("");

                } catch (NumberFormatException ex) {
                    display.setText("Error");
                }
            }
        }
    }

    public static void main(String[] args) {
        // Create a new thread for the Spark server
        Thread sparkThread = new Thread(() -> {
            port(8081);
            enableCORS("http://localhost:3000", "*", "*");

            // Addition
            get("/+/:a/:b", (req, res) -> {
                CalculatorDto calculatorDto = new CalculatorDto();
                calculatorDto.setOperand1(String.valueOf(Double.parseDouble(req.params(":a"))));
                calculatorDto.setOperand2(String.valueOf(Double.parseDouble(req.params(":b"))));
                calculatorDto.setOperation("+");
                operationStack.push(calculatorDto);
                double result = Double.parseDouble(req.params(":a")) + Double.parseDouble(req.params(":b"));
                return String.valueOf(result);
            });

            // Subtraction
            get("/-/:a/:b", (req, res) -> {
                CalculatorDto calculatorDto = new CalculatorDto();
                calculatorDto.setOperand1(String.valueOf(Double.parseDouble(req.params(":a"))));
                calculatorDto.setOperand2(String.valueOf(Double.parseDouble(req.params(":b"))));
                calculatorDto.setOperation("-");
                operationStack.push(calculatorDto);
                double result = Double.parseDouble(req.params(":a")) - Double.parseDouble(req.params(":b"));
                return String.valueOf(result);
            });

            // Multiplication
            get("/*/:a/:b", (req, res) -> {
                CalculatorDto calculatorDto = new CalculatorDto();
                calculatorDto.setOperand1(String.valueOf(Double.parseDouble(req.params(":a"))));
                calculatorDto.setOperand2(String.valueOf(Double.parseDouble(req.params(":b"))));
                calculatorDto.setOperation("*");
                operationStack.push(calculatorDto);
                double result = Double.parseDouble(req.params(":a")) * Double.parseDouble(req.params(":b"));
                return String.valueOf(result);
            });

            // Division
            get("///:a/:b", (req, res) -> {
                double num2 = Double.parseDouble(req.params(":b"));
                if (num2 != 0) {
                    CalculatorDto calculatorDto = new CalculatorDto();
                    calculatorDto.setOperand1(String.valueOf(Double.parseDouble(req.params(":a"))));
                    calculatorDto.setOperand2(String.valueOf(Double.parseDouble(req.params(":b"))));
                    calculatorDto.setOperation("/");
                    operationStack.push(calculatorDto);
                    double result = Double.parseDouble(req.params(":a")) / num2;
                    return String.valueOf(result);
                } else {
                    res.status(400);
                    return "Error: Division by zero";
                }
            });

            get("/getOperations", (req, res) -> {
                // Convert the operation stack to JSON
                String json = new Gson().toJson(operationStack);

                // Set the response type to JSON
                res.type("application/json");

                return json;
            });
        });

        // Start the Spark server thread
        sparkThread.start();
    }

    private static void enableCORS(String origin, String methods, String headers) {
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
        });
    }
}
