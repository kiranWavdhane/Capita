package com.assignmentCapita;

import java.util.Scanner;
import java.util.Stack;

//Program to calculate arithmatic operation using expression

public class ScientificCalculator {
	public static void main(String[] args) {
		System.out.println("Enter Expression for Calculations");
		Scanner scan = new Scanner(System.in);
		String myLine = scan.nextLine();
		try {
			// 100 * (2 + 12 )
			System.out.println(ScientificCalculator.calculate(myLine));
		} catch (Exception e) {
			System.out.println("INVALID EXPRESSION");
		}
	}

	public static int calculate(String expression) {
		boolean flag = false;
		char[] tokens = expression.toCharArray();
		// Stack for numbers
		Stack<Integer> digits = new Stack<Integer>();
		// Stack for Operators
		Stack<Character> operators = new Stack<Character>();
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i] == ' ')
				continue;
			// check number for 0-9 digit
			if (tokens[i] >= '0' && tokens[i] <= '9' && !((tokens[i] == '(') || (tokens[i] == ')'))) {
				StringBuffer stringBuffer = new StringBuffer();
				// There may be more than one digits in number
				while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
					stringBuffer.append(tokens[i++]);
				digits.push(Integer.parseInt(stringBuffer.toString()));
			}
			// Current token is an opening brace, push it to 'ops'
			else if (tokens[i] == '(')
				operators.push(tokens[i]);

			// Closing brace encountered, solve entire brace
			else if (tokens[i] == ')') {
				while (operators.peek() != '(')
					digits.push(addOperator(operators.pop(), digits.pop(), digits.pop()));
				operators.pop();
			}

			// Current token is an operator.
			else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '^') {
				while (!operators.empty() && checkPrecedenceOfToken(tokens[i], operators.peek()))
					digits.push(addOperator(operators.pop(), digits.pop(), digits.pop()));

				operators.push(tokens[i]);
			}
		}

		while (!operators.empty())
			digits.push(addOperator(operators.pop(), digits.pop(), digits.pop()));

		// Top of 'values' contains result, return it
		return digits.pop();
	}

	// return precennce
	public static boolean checkPrecedenceOfToken(char op1, char op2) {
		if (op2 == '(' || op2 == ')')
			return false;
		if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-' || op2 == '^'))
			return false;
		else
			return true;
	}

	// switch case to return operartor
	public static int addOperator(char op, int b, int a) {
		switch (op) {
		case '+':
			return a + b;
		case '-':
			return a - b;
		case '*':
			return a * b;
		case '/':
			if (b == 0)
				throw new UnsupportedOperationException(" divide by zero error");
			return a / b;
		case '^':
			return a ^ b;
		}
		return 0;
	}
}
