import java.util.Stack;

public class ExpressionReader {

	String expression;
	Matrix a, b;
	Object solution;

	public ExpressionReader(String expression, Matrix a, Matrix b) {
		if(a == null || b == null) return;
		expression = expression.replaceAll("\\s+", "");
		for(Character c : expression.trim().toCharArray())
			if(!(isOperator(c.toString()) || isNumber(c.toString()) || isMatrix(c.toString()) || c == '(' || c == ')')) {
				throw new IllegalArgumentException(INVALID_CHARACTER_MESSAGE);
			}
		if(characterCount(expression, '(') > characterCount(expression, ')'))
			throw new IllegalArgumentException("Expecting ')'");
		else if(characterCount(expression, '(') < characterCount(expression, ')'))
			throw new IllegalArgumentException("Expecting '('");
		this.expression = expression;
		this.a = a;
		this.b = b;
		
		read(  toReversePolishNotation( addSpaces(expression) )  );
	}

	public Object getSolution() {
		return solution;
	}

	private String addSpaces(String expr) {
		String output = "";
		String prev = " ";

		for(Character c : expr.trim().toCharArray()) {
			if(!areSameType(prev, c.toString()) || isOperator(prev) || 
					(isMatrix(c.toString()) && isMatrix(prev))) 
				output += " ";
			output += c;
			prev = c.toString();
		}

		return output;
	}

	private String toReversePolishNotation(String expr) {
		String output = "";
		Stack<String> operatorStack = new Stack<String>();

		for(String token : expr.split("\\s+")) {
			if(isNumber(token)) {
				output += token + " ";
			}
			else if(isMatrix(token)) {
				output += token + " ";
			}
			else if(isOperator(token)) {
				int precidenceToken = getPrecidence(token);
				int precidenceNext;
				while(!operatorStack.isEmpty()) {
					precidenceNext = getPrecidence(operatorStack.peek());
					if(precidenceNext >= precidenceToken) output += operatorStack.pop() + " ";
					else break;
				}
				operatorStack.push(token);
			}
			else if(token.equals("(")) {
				operatorStack.push(token);
			}
			else if(token.equals(")")) {
				while(!operatorStack.peek().equals("(")) output += operatorStack.pop() + " ";
				operatorStack.pop();
			}
			else throw new IllegalArgumentException(INVALID_CHARACTER_MESSAGE);
		}
		while(!operatorStack.isEmpty()) output += operatorStack.pop() + " ";

		return output;
	}

	private void read(String rpn) {
		Stack<Object> stack = new Stack<Object>();
		Object val1, val2;

		for(String token : rpn.split("\\s+")) {
			if(isOperator(token)) {
				val2 = stack.pop();
				val1 = stack.pop();
				if(token.equals("+")) {
					if(isMatrix(val1) && isMatrix(val2)) {
						stack.push(Matrix.add(toMatrix(val1), toMatrix(val2)));
					}
					else if(isNumber(val1) && isNumber(val2)) {
						stack.push(Double.parseDouble((String) val1) + Double.parseDouble((String) val2));
					}
					else {
						throw new IllegalArgumentException("Invalid Argument: numbers may\nnot be added to matrices");
					}
				}
				else if(token.equals("-")) {
					if(isMatrix(val1) && isMatrix(val2)) {
						stack.push(Matrix.subtract(toMatrix(val1), toMatrix(val2)));
					}
					else if(isNumber(val1) && isNumber(val2)) {
						stack.push(Double.parseDouble((String) val1) - Double.parseDouble((String) val2));
					}
					else throw new IllegalArgumentException("Invalid Argument: numbers may not be\nsubtracted to or from matrices");
				}
				else if(token.equals("*")) {
					if(isMatrix(val1) && isMatrix(val2)) {
						if(Matrix.multiply(toMatrix(val1), toMatrix(val2)) == null) throw new IllegalArgumentException("The number of columns in A must be\n"
								+ "equal to the number of rows in B");
						stack.push(Matrix.multiply(toMatrix(val1), toMatrix(val2)));
					}
					else if(isMatrix(val1) && isNumber(val2)) {
						stack.push(Matrix.multiply(toMatrix(val1), Double.parseDouble((String) val2)));
					}
					else if(isNumber(val1) && isMatrix(val2)) {
						stack.push(Matrix.multiply(toMatrix(val2), Double.parseDouble((String) val1)));
					}
					else if(isNumber(val1) && isNumber(val2)) {
						stack.push(Double.parseDouble((String) val1) * Double.parseDouble((String) val2));
					}
				}
				else if(token.equals("/")) {
					if(isNumber(val1) && isNumber(val2)) {
						stack.push(Double.parseDouble((String) val1) / Double.parseDouble((String) val2));
					}
					else if(isMatrix(val1) && isNumber(val2)) {
						stack.push(Matrix.divide(toMatrix(val1), Double.parseDouble((String) val2)));
					}
					else throw new IllegalArgumentException("Invalid Argument: cannot\ndivide by a matrix");
				}
				else if(token.equals("^")) {
					if(isMatrix(val1) && isNumber(val2)) {
						stack.push(Matrix.pow(toMatrix(val1), Integer.parseInt((String) val2)));
					}
					else if(isNumber(val1) && isNumber(val2)) {
						stack.push(Math.pow(Double.parseDouble((String) val1), Double.parseDouble((String) val2))); 
					}
					else throw new IllegalArgumentException("Invalid Argument: exponents\nmay not be matrices");
				}
			}
			else stack.push(token);
		}
		solution = stack.pop();
	}

	private boolean areSameType(String s1, String s2) {
		return((isMatrix(s1) && isMatrix(s2)) || (isOperator(s1) && isOperator(s2)) || (isNumber(s1) && isNumber(s2)));
	}

	private int getPrecidence(String s) {
		if(s.equals("+") || s.equals("-")) return 1;
		else if(s.equals("*") || s.equals("/")) return 2;
		else if(s.equals("^")) return 3;
		else return 0;
	}

	private boolean isOperator(String s) {
		return (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("^"));
	}

	private boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	//TODO: this method is pretty shitty
	private boolean isNumber(Object o) {
		if(o instanceof String) return isNumber((String) o);
		else if(o instanceof Double) return true;
		else return false;
	}

	private boolean isMatrix(String s) {
		return (s.equals("A") || s.equals("B"));
	}

	private boolean isMatrix(Object o) {
		if(o instanceof String) return isMatrix((String) o);
		else if(o instanceof Matrix) return true;
		else return false;
	}

	private Matrix toMatrix(Object o) {
		if(o instanceof String) {
			if(!isMatrix((String) o)) return null;
			else if(o.equals("A")) return a;
			else return b;
		}
		else if(o instanceof Matrix) return (Matrix) o;
		else return null;
	}

	private static int characterCount(String expr, Character c) {
		int count = 0;
		for(Character ch : expr.toCharArray()) if(ch == c) count ++;
		return count;
	}

	private static final String INVALID_CHARACTER_MESSAGE = "Please input a valid expression";
}
