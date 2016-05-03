package calculator;

import java.util.EmptyStackException;
import java.util.Stack;

public class Postfix {
	
	public Postfix(){
		
	}
	
	private static String convert(String s){
		String isf = s;
		StringBuffer rsf = new StringBuffer();
		Stack<Character> stk = new Stack<Character>();
		stk.push('#');
		
		for (int i = 0; i < isf.length(); ++i){
			if ((isf.charAt(i) >= '0' && isf.charAt(i) <= '9') || isf.charAt(i) == '.')
				rsf.append(isf.charAt(i));
			else if (isf.charAt(i) == '(')
				stk.push(isf.charAt(i));
			else if (isf.charAt(i) == ')'){
				while (stk.peek() != '('){
					rsf.append(stk.pop());
				}
				stk.pop();
			}
			else if (isOperator(isf.charAt(i))){
				rsf.append(' ');
				
				if (isf.charAt(i) == '-' && (i == 0 || isOperator(isf.charAt(i-1)))){
					StringBuffer tmp = new StringBuffer(isf);
					tmp.setCharAt(i, '$');
					isf = tmp.toString();
				}
				if (isf.charAt(i) == '+' && (i == 0 || isOperator(isf.charAt(i-1)))){
					StringBuffer tmp = new StringBuffer(isf);
					tmp.setCharAt(i, '@');
					isf = tmp.toString();
				}
				while (getPriority(isf.charAt(i)) <= getPriority(stk.peek())){
					rsf.append(stk.pop());
				}
				stk.push(isf.charAt(i));
			}
		}
		
		while (!stk.empty()){
			rsf.append(stk.pop());
		}
		
		return rsf.toString();
	}
	
	private static double calculate(String rsf) throws IllegalArgumentException{
		int i = 0;
		Stack<Double> num = new Stack<Double>();
		double x1, x2;
		
		while (rsf.charAt(i) != '#'){
			if (rsf.charAt(i) >= '0' && rsf.charAt(i) <= '9'){
				i = readNum(rsf, num, i);
				continue;
			}
			else if (rsf.charAt(i) == '$'){
				num.push( -num.pop());
			}
			else if (rsf.charAt(i) == '@'){
				num.push( num.pop());
			}
			else if (rsf.charAt(i) != ' '){
				x1 = num.pop();
				x2 = num.pop();
				switch (rsf.charAt(i)){
				case '+':
					num.push(x2 + x1);
					break;
				case '-':
					num.push(x2 - x1);
					break;
				case '*':
					num.push(x2 * x1);
					break;
				case '/':
					num.push(x2 / x1);
					break;
				}
			}
			i++;
		}
		return num.peek();
	}

	
	private static boolean isOperator(char ch){
	    switch(ch){
	        case '+':
	        case '-':
	        case '*':
	        case '/':
	        case '(':
	            return true;
	        default:
	            return false;
	    }
	}
	
	private static int getPriority(char ch){
	    switch (ch) {
	        case '(':
	            return 0;
	        case '+':
	        case '-':
	        case '$':
	        case '@':
	            return 1;
	        case '*':
	        case '/':
	            return 2;
	        default:
	            return -1;
	    }
	}
	
	private static int readNum(String rsf,Stack<Double> numStack, int i) throws IllegalArgumentException{
	    double result = 0;
	    double num = 10;
	    boolean flag = true;

	    for (; i < rsf.length(); ++i){
	        if  (rsf.charAt(i) == '.' && !flag){
        		throw new IllegalArgumentException("Êý×ÖÊäÈëÓÐÎó");
	        }
	        
	        if (rsf.charAt(i) == '.'){
	            flag = false;
	        }
	        else if (rsf.charAt(i) >= '0' && rsf.charAt(i) <= '9' && flag){
	            result = result * 10 + (rsf.charAt(i) - '0');
	        }
	        else if (rsf.charAt(i) >= '0' && rsf.charAt(i) <= '9' && !flag){
	            result = result + (rsf.charAt(i) - '0') / num;
	            num *= 10;
	        }
	        else
	            break;
	    }
	    
	    System.out.println(result);
	    numStack.push(result);
	    return i;
	}
	
	public static Object Calculate(String str) throws EmptyStackException, IllegalArgumentException{
		double result = 0;
		
//		try{
		String rsf = convert(str);
		System.out.println((rsf));
		result = calculate(rsf);
		if (result == (int)result)
			return (int)result;
		else
			return result;
//		}catch (EmptyStackException e){
//			Surface.showErrorDialog();
//		}
//		
//		return (int)0;
	}
}
