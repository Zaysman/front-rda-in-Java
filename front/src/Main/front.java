package Main;
//imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;


public class front {

	/* Global Declarations */

	/* Variables */
	static int charClass = 0;
	static char[] lexeme = new char[100];
	static LinkedList<Character> lexemes = new LinkedList<Character>();
	static int lexemesIndex = 0;
	static LinkedList<String> lexStrings = new LinkedList<String>();
	static char nextChar = '\0';
	static int lexLen = 0;
	static int token = 0;
	static int nextToken = 0;

	/* Character classes */
	final static int LETTER = 0;
	final static int DIGIT = 1;
	final static int UNKNOWN = 99;

	/* Token Codes */
	final static int INT_LIT = 10;
	final static int IDENT = 11;
	final static int ASSIGN_OP = 20;
	final static int ADD_OP = 21;
	final static int SUB_OP = 22;
	final static int MULT_OP = 23;
	final static int DIV_OP = 24;
	final static int LEFT_PAREN = 25;
	final static int RIGHT_PAREN = 26;
	final static int MOD_OP = 27;


	public static void main(String[] args) {
		for(int i = 0; i < lexeme.length; i++) {
			lexeme[i] = '\0';
		}


		// TODO Auto-generated method stub
		try {
			File in_fp = new File("front");
			Scanner input = new Scanner(in_fp);

			while(input.hasNext()) { 
				String str = input.next();
				lexStrings.add(str);
			}

			stringListtoCharList(lexStrings);
			/*
			for(int i = 0; i < lexemes.size(); i++) { //after converting lexemes from strings to chars, we add the chars from the charlist to the char array.
				lexeme[i] = lexemes.get(i);
			}*/

		} catch (FileNotFoundException f) {
			System.out.println("Error - cannot open front.in");
			System.exit(0);
		} catch (IOException i) {
			System.out.println("An IO error has occurred");
			System.exit(0);
		}

	}

	public static int lex() {
		lexLen = 0;
		getNonBlank();

		switch(charClass) {

		case LETTER:
			addChar();
			getChar();
			while(charClass == LETTER || charClass == DIGIT) {
				addChar();
				getChar();
			}
			nextToken = IDENT;
			break;

		case DIGIT:
			addChar();
			getChar();
			while (charClass == DIGIT) {
				addChar();
				getChar();
			}
			nextToken = INT_LIT;
			break;

		case UNKNOWN:
			lookup(nextChar);
			getChar();
			break;

		}

		System.out.println("Next token is: " + nextToken + ", Next Lexeme is " + lexeme.toString());

		return nextToken;
	}


	public static void getNonBlank() {
		while(isspace(nextChar)) {
			getChar();
		}
	}

	public static boolean isspace(char ch) {
		if(ch == ' ') {
			return true;
		} else {
			return false;
		}

	}

	public static void addChar() {
		if (lexLen <= 98) {
			lexeme[lexLen++] = nextChar;
			lexeme[lexLen] = '\0';
		} else {
			System.out.println("Error - lexeme is too long");
		}
	}

	public static void getChar() {

		if(lexemes.get(lexemesIndex++) != null) {

			if(isLetter(nextChar)) {
				charClass = LETTER;
			} else if (isDigit(nextChar)) {
				charClass = DIGIT;
			} else {
				charClass = UNKNOWN;
			}
			
		}

	}


	public static int lookup(char ch) {
		switch(ch) {
		case '(':
			addChar();
			nextToken = LEFT_PAREN;
			break;
		case ')':
			addChar();
			nextToken = RIGHT_PAREN;
			break;
		case '+':
			addChar();
			nextToken = ADD_OP;
			break;
		case '-':
			addChar();
			nextToken = SUB_OP;
			break;
		case '*':
			addChar();
			nextToken = MULT_OP;
			break;
		case '/':
			addChar();
			nextToken = DIV_OP;
			break;
		case '%':
			addChar();
			nextToken = MOD_OP;
			break;
		default:
			addChar();
			nextToken = '\0'; //empty char, we'll use this to represent EOF in C.
			break;
		}
		return nextToken;
	}


	public static boolean isDigit(char ch) {

		/*
		if(ch < '0' || ch > '9') {
			return false;
		} else {
			return true;
		}*/

		return Character.isDigit(ch);
	}

	public static boolean isLetter(char ch) {

		return Character.isLetter(ch);
	}





	public static void stringListtoCharList(LinkedList list) {
		for(int i = 0; i < list.size(); i++) {
			String str = (String) list.get(i); //grabs string from list of strings

			for(int j = 0; j < str.length(); j++) { //goes character by character in string and adds to list of characters
				char ch = str.charAt(j);
				lexemes.add(ch);
			}
		}
	}
}
