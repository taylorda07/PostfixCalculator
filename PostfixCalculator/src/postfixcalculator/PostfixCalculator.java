
package postfixcalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

public class PostfixCalculator {

    // Evaluate a space-separated postfix expression.
    public int evaluatePostfix(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid postfix expression");
        }

        Stack<Integer> stack = new Stack<>();

        for (String token : expression.trim().split("\\s+")) {

            if (token.matches("-?\\d+")) {
                try {
                    stack.push(Integer.parseInt(token));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number: " + token);
                }
            } else {
                if (!token.matches("[+*/%-]") || stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid postfix expression");
                }

                int b = stack.pop();
                int a = stack.pop();
                int result;

                switch (token) {
                    case "+":
                        result = Math.addExact(a, b);
                        break;
                    case "-":
                        result = Math.subtractExact(a, b);
                        break;
                    case "*":
                        result = Math.multiplyExact(a, b);
                        break;
                    case "/":
                        if (b == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        result = a / b;
                        break;
                    case "%":
                        if (b == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        result = a % b;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator");
                }

                stack.push(result);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid postfix expression");
        }

        return stack.pop();
    }

    // Display the result or an error message.
    public void testExpression(String expression) {
        System.out.println("Expression: " + expression);

        try {
            int result = evaluatePostfix(expression);
            System.out.println("Result: " + result);
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("--------------------------------");
    }

    // Read and evaluate expressions from a text file.
    public void readExpressionsFromFile(String filename) {
        System.out.println("\nTEXT FILE TESTING");
        System.out.println("--------------------------------");

        try (BufferedReader reader =
                Files.newBufferedReader(Paths.get(filename))) {

            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    testExpression(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        PostfixCalculator calculator = new PostfixCalculator();

        System.out.println("POSTFIX CALCULATOR");
        System.out.println("CSU Global CSC 400");
        System.out.println("================================");

        System.out.println("\nSINGLE-DIGIT TESTS");
        calculator.testExpression("4 2 3 * +");
        calculator.testExpression("5 3 7 * +");

        System.out.println("\nMULTI-DIGIT TESTS");
        calculator.testExpression("12 8 +");
        calculator.testExpression("100 25 -");
        calculator.testExpression("12 5 *");

        System.out.println("\nDIVISION AND MODULUS TESTS");
        calculator.testExpression("20 4 /");
        calculator.testExpression("17 5 %");

        System.out.println("\nINVALID EXPRESSION TESTS");
        calculator.testExpression("4 2 * +");
        calculator.testExpression("10 0 /");
        calculator.testExpression("5 3 &");
        calculator.testExpression("4 2 3");

        calculator.readExpressionsFromFile("expressions.txt");

        System.out.println("\nALL TESTS COMPLETED");
    }
}


