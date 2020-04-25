package Main;

import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class rda {
	
	final static String ADD_OP = "+";
	final static String SUB_OP = "-";
	final static String MULT_OP = "*";
	final static String DIV_OP = "/";
	final static String MOD_OP = "%";
	final static String LEFT_PAREN = "(";
	final static String RIGHT_PAREN = ")";
	
	
	final static String IDENT = "0";
	final static String INT_LIT = "1";
	
	
	static LinkedList<String> lexemes = new LinkedList<String>();
	static int lexemesIndex = 0;
	
	static String nextToken = new String();
	static File file = new File("expression");
	

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		Scanner fileInput = new Scanner(file);
		String input = new String();
		
		while(fileInput.hasNext()) {
			input = fileInput.next();
			handleString(input);
		}
		
		fileInput.close();
		clearEmptySpaces(lexemes);
		
		printList(lexemes);
		System.out.println("\n");
		
		//System.out.println("isId(sum) = " + isId("sum"));
		//System.out.println("isId(total) = " + isId("total"));
		//System.out.println("isInt(47) = " + isInt("47"));
		
		
		expr();
		
		
	}
	
	
	
	public static void expr() {
		
		System.out.println("Enter <expr>");
		
		term();
		
		
		while(nextToken.compareTo(ADD_OP) == 0 || nextToken.compareTo(SUB_OP) == 0) {
			System.out.println("Lexeme is: " + nextToken);
			lex();
			term();
		}
		
		System.out.println("Exit <expr>");
	}
	
	public static void term() {
		System.out.println("Enter <term>");
		
		factor();
		
		while(nextToken.compareTo(MULT_OP) == 0 || nextToken.compareTo(DIV_OP) == 0 || nextToken.compareTo(MOD_OP) == 0) {
			System.out.println("Lexeme is: " + nextToken);
			lex();
			factor();
		}
		System.out.println("Exit <term>");
		
	}
	
	public static void factor() {
		System.out.println("Enter <factor>");
		
		if(isId(nextToken) == true || isInt(nextToken) == true) {
			System.out.println("Lexeme is: " + nextToken);
			lex();
		} else {
			if (nextToken.compareTo(LEFT_PAREN) == 0) {
				System.out.println("Lexeme is: " + nextToken);
				lex();
				expr();
				if(nextToken.compareTo(RIGHT_PAREN) == 0) {
					System.out.println("Lexeme is: " + nextToken);
					lex();
				} else {
					error();
				}
			}
		}
	}
	
	public static void lex() {
		if(lexemesIndex >= lexemes.size()) {
			System.out.println("We have processed all lexemes");
			return;
		} else {
			nextToken = lexemes.get(lexemesIndex++);
			System.out.println("nextToken = " + nextToken);
			return;
		}
	}
	
	public static void error() {
		System.out.println("An error has occurred.\n"
				+ "Exiting Program");
	}
	
	public static boolean isInt(String str) {
		 if (str == null) {
		        return false;
		    }
		    int length = str.length();
		    if (length == 0) {
		        return false;
		    }
		    int i = 0;
		    if (str.charAt(0) == '-') {
		        if (length == 1) {
		            return false;
		        }
		        i = 1;
		    }
		    for (; i < length; i++) {
		        char c = str.charAt(i);
		        if (c < '0' || c > '9') {
		            return false;
		        }
		    }
		    return true;
	}
	
	public static boolean isId(String str) {
		
		for(int i = 0; i < str.length(); i++) {
			if(isNumber(str.charAt(i))  == true) {
				return false;
			}
		}
		
		return true;
	}
	
	
	public static boolean isNumber(String str) {
		
		for(int i = 0; i < str.length(); i++) {
			switch(str.charAt(i)) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				return true;
			default:
				// do nothing
			}	
		}
		
		return false;
	}
	
	public static boolean isNumber(char ch) {
		
		switch(ch) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return true;
		default:
			// do nothing
		}
		
		return false;
		
	}
	

	public static void clearEmptySpaces(List<String> list) {
		for(int i = 0; i < list.size(); i++) {
			String str = (String) list.get(i);
			if(str.isEmpty() == true) {
				list.remove(i);
				i--;
			}
		}
	}

	public static void printList(List<String> list) {
		for(int i = 0; i < list.size(); i++) {
			System.out.println("list(" + i + ")" + list.get(i));
		}
	}

	public static void handleString(String str) {
		if(hasSpecialCharacter(str) == false) {
			lexemes.add(str);
		} else {
			spliceString(str);
		}
	}

	public static void spliceString(String str) {
		int special = specialCharacterAt(str);
		String substr = str.substring(0, special);
		lexemes.add(substr);

		lexemes.add(Character.toString(str.charAt(special)));
		substr = str.substring(special + 1, str.length());
		handleString(substr);
	}
	
	
	

	/*
	 * The purpose of this method is to determine if a special character exists within a string. If so, returns true. else returns false
	 * 
	 * str - String parameter
	 */

	public static boolean hasSpecialCharacter(String str) {

		for(int i = 0; i < str.length(); i++) {
			if(checkSpecialCharacter(str.charAt(i)) == true) {
				return true;
			}
		}

		return false;
	}

	public static int specialCharacterAt(String str) {

		for(int i = 0; i < str.length(); i++) {
			if(checkSpecialCharacter(str.charAt(i)) == true) {
				return i;
			}
		}

		return -1;
	}

	public static boolean checkSpecialCharacter(char ch) {

		switch(ch) {
		case ';' :
		case '!' :
		case '#' :
		case '"' :
		case '$' :
		case '%' :
		case '&' :
		case '(' :
		case ')' :
		case '*' :
		case '+' :
		case ',' :
		case '-' :
		case '.' :
		case '/' :
		case ':' :
		case '<' :
		case '=' :
		case '>' :
		case '?' :
		case '@' :
		case '[' :
		case '\\' :
		case ']' :
		case '^' :
		case '_' :
		case '`' :
		case '{' :
		case '}' :
		case '|' :
		case '~' :	
			return true;
		default:
			return false;
		}
	}
	
	
}
