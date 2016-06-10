package ua.epamcourses.natalia_markova.homework.problem08.task01;

import java.util.Comparator;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by natalia_markova on 10.06.2016.
 */
public class Calculator {

    private Calculator() {

    }

    public static int calculate(String expression) throws Exception {
        Node root = buildTree(expression);
        return root.calculate();
    }

    private static Node buildTree(String expression) throws Exception {
        Stack<Node> nodes = new Stack<>();
        Stack<Node.Operation> stack = new Stack<>();

        String income = expression.replaceAll(" +", "");
        Pattern pattern = Pattern.compile("\\(|\\)|\\+|-|\\*|/|[0-9]+");
        Matcher matcher = pattern.matcher(income);
        while (matcher.find()) {
            String token = matcher.group();
            if (token.matches("[0-9]+")) {
                Node node = new Node(Integer.valueOf(token));
                nodes.push(node);
            } else {
                Node.Operation operation = Node.Operation.getOperation(token);
                if (stack.size() == 0) {
                    stack.push(operation);
                } else {
                    if (operation == Node.Operation.LEFT_BRACE) {
                        stack.push(operation);
                    } else {
                        while (!stack.isEmpty() && (stack.peek().priority >= operation.priority)) {
                            Node.Operation op = stack.pop();
                            if (op != Node.Operation.LEFT_BRACE) {
                                Node node = new Node(op);
                                node.right = nodes.pop();
                                node.left = nodes.pop();
                                nodes.push(node);
                            } else {
                                break;
                            }
                        }
                        if (operation != Node.Operation.RIGHT_BRACE) {
                            stack.push(operation);
                        }
                    }
                }
            }
        }

        while (!stack.isEmpty()) {
            Node node = new Node(stack.pop());
            node.right = nodes.pop();
            node.left = nodes.pop();
            nodes.push(node);
        }
        return nodes.pop();
    }

    private static class Node {
        private Type type;
        private Object value;
        private Node left;
        private Node right;

        public Node(Object value) {
            this.value = value;
            setType();
        }

        private void setType() {
            if (value.getClass() == Operation.class) {
                this.type = Type.OPERATION;
            } else {
                this.type = Type.NUMBER;
            }
        }

        enum Type {
            OPERATION, NUMBER
        }

        enum Operation {
            PLUS(1), MINUS(1), MULT(2), DIV(2), LEFT_BRACE(0), RIGHT_BRACE(0);

            int priority;

            Operation(int priority) {
                this.priority = priority;
            }

            public static Operation getOperation(String token) {
                if (token.equals("+")) {
                    return Node.Operation.PLUS;
                } else if (token.equals("-")) {
                    return Node.Operation.MINUS;
                } else if (token.equals("*")) {
                    return Node.Operation.MULT;
                } else if (token.equals("/")) {
                    return Node.Operation.DIV;
                } else if (token.equals("(")) {
                    return Node.Operation.LEFT_BRACE;
                } else if (token.equals(")")) {
                    return Node.Operation.RIGHT_BRACE;
                }
                return null;
            }
        }

        public int calculate() {
            if (type == Type.NUMBER) {
                return (int) value;
            } else {
                Operation operation = (Operation) value;
                if (operation == Operation.PLUS) {
                    return left.calculate() + right.calculate();
                } else if (operation == Operation.MINUS) {
                    return left.calculate() - right.calculate();
                } else if (operation == Operation.MULT) {
                    return left.calculate() * right.calculate();
                } else { // division
                    return left.calculate() / right.calculate();
                }
            }
        }
    }

}
