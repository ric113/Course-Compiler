// Semantic target : in-fix -> post-fix

import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;

public class main {

	public static String[][] num_token_table = {
			{ "X", "D", "E", ".", "+", "-" },
			{ "aa", "bb", "X", "X", "X", "X" },
			{ "bb", "bb", "ee", "cc", "X", "X" },
			{ "cc", "dd", "X", "X", "X", "X" },
			{ "dd", "dd", "ee", "X", "X", "X" },
			{ "ee", "hh", "X", "X", "ff", "gg" },
			{ "ff", "hh", "X", "X", "X", "X" },
			{ "gg", "hh", "X", "X", "X", "X" },
			{ "hh", "hh", "X", "X", "X", "X" } };

	public static String[][] parser_table = {
			{ "X", "+", "-", "*", "/", "(", ")", "n", "$" },
			{ "S", "X", "X", "X", "X", "TR", "X", "TR", "X" },
			{ "R", "+TAR", "-TBR", "X", "X", "X", "!", "X", "!" },
			{ "T", "X", "X", "X", "X", "FY", "X", "FY", "X" },
			{ "Y", "!", "!", "*FCY", "/FDY", "X", "!", "X", "!" },
			{ "F", "X", "X", "X", "X", "(S)", "X", "nI", "X" } };

	public static String post_fix = "";
	public static Stack temp_target = new Stack();
	public static Stack target = new Stack();
	public static Stack test = new Stack();
	public static ArrayList value = new ArrayList();

	public static boolean lexicalerror = false;
	public static boolean syntaxerror = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Please Give an input :");
		while (keyboard.hasNextLine()) {

			String input = keyboard.nextLine();
			input = input.trim();

			String temp_s = "";

			for (int i = 0; i < input.length(); i++) {
				if (String.valueOf(input.charAt(i)).equals(" "))
					;
				else if ((48 <= input.charAt(i) && input.charAt(i) <= 57)
						|| input.charAt(i) == '.' || input.charAt(i) == 'E') {
					temp_s += String.valueOf(input.charAt(i));
					if (input.charAt(i) == 'E'
							&& (input.charAt(i + 1) == '+' || input
									.charAt(i + 1) == '-')) {
						temp_s += String.valueOf(input.charAt(i + 1));

						i += 1;
					} else if (i == input.length() - 1) {
						if (is_num(temp_s)) {
							temp_target.push("n");
							value.add(temp_s);
							temp_s = "";
						} else {
							lexicalerror = true;
							break;
						}
					}

				} else if (is_other_terminal(String.valueOf(input.charAt(i)))) {
					if (!temp_s.equals("")) {
						if (is_num(temp_s)) {
							temp_target.push("n");
							value.add(temp_s);
							temp_target.push(String.valueOf(input.charAt(i)));
							temp_s = "";
						} else {
							lexicalerror = true;
							break;
						}
					} else
						temp_target.push(String.valueOf(input.charAt(i)));

				} else {
					lexicalerror = true;
				}
			}

			temp_target.push("$");

			while (!temp_target.empty()) {
				target.push(temp_target.peek());
				temp_target.pop();
			}

			// while (!target.empty()) { System.out.println(target.peek());
			// target.pop(); }
			/*
			 * for(int i = 0 ; i < value.size() ; i++){
			 * System.out.println(value.get(i)); }
			 */

			test.push("$");
			test.push("S");
			temp_s = "";

			while (!target.empty() && !lexicalerror) {
				if (test.peek().toString().equals(target.peek().toString())) {
					test.pop();
					target.pop();
					continue;
				}
				if (is_action(test.peek().toString())) {
					if (test.peek().toString().equals("A"))
						post_fix += "+ ";
					else if (test.peek().toString().equals("B"))
						post_fix += "- ";
					else if (test.peek().toString().equals("C"))
						post_fix += "* ";
					else if (test.peek().toString().equals("D"))
						post_fix += "/ ";
					else if (test.peek().toString().equals("I")) {
						post_fix += String.valueOf(value.get(0)) + " ";
						value.remove(0);
					}
					test.pop();
					continue;
				}

				/*
				 * System.out.println("row = "
				 * +parser_get_index(test.peek().toString()) );
				 * System.out.println("col  = "
				 * +parser_get_index(target.peek().toString()));
				 */

				try {
					temp_s = parser_table[parser_get_index(test.peek()
							.toString())][parser_get_index(target.peek()
							.toString())];
				} catch (Exception e) {
					syntaxerror = true;
					break;
				}

				// System.out.println("temp_S ="+temp_s);
				if (temp_s.equals("X")) {
					syntaxerror = true;
					break;
				}

				test.pop();

				for (int i = temp_s.length() - 1; i >= 0; i--) {
					test.push(String.valueOf(temp_s.charAt(i)));
				}

				if (test.peek().toString().equals("!"))
					test.pop();

			}

			if (lexicalerror == true)
				System.out.println("lexical error!");
			else if (syntaxerror == true)
				System.out.println("syntax error!");
			else
				System.out.println("post_fix = " + post_fix);
			System.out.print("Please Give an input :");
			syntaxerror = false;
			lexicalerror = false;
			for(int i = 0 ; i < value.size() ; i ++){
				value.remove(i);
			}
			post_fix = "";

			while (!target.empty()) {
				target.pop();
			}

		}

	}

	public static boolean is_num(String s) {

		String current_state = "aa";
		for (int i = 0; i < s.length(); i++) {
			if (get_index(String.valueOf(s.charAt(i))) == -1
					|| (65 <= s.charAt(i) && s.charAt(i) <= 68)
					|| (70 <= s.charAt(i) && s.charAt(i) <= 90))
				break;
			current_state = num_token_table[get_index(current_state)][get_index(String
					.valueOf(s.charAt(i)))];
			if (current_state.equals("X"))
				break;
		}

		if (current_state.equals("bb") || current_state.equals("dd")
				|| current_state.equals("hh"))
			return true;
		else
			return false;
	}

	public static int get_index(String s) {
		if (s.equals("aa") || (48 <= s.charAt(0) && s.charAt(0) <= 57))
			return 1;
		else if (s.equals("bb") || s.equals("E"))
			return 2;
		else if (s.equals("cc") || s.equals("."))
			return 3;
		else if (s.equals("dd") || s.equals("+"))
			return 4;
		else if (s.equals("ee") || s.equals("-"))
			return 5;
		else if (s.equals("ff"))
			return 6;
		else if (s.equals("gg"))
			return 7;
		else if (s.equals("hh"))
			return 8;
		else
			return -1;

	}

	public static boolean is_other_terminal(String s) {
		if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")
				|| s.equals("(") || s.equals(")"))
			return true;
		return false;
	}

	public static int parser_get_index(String s) {
		if (s.equals("+") || s.equals("S"))
			return 1;
		else if (s.equals("-") || s.equals("R"))
			return 2;
		else if (s.equals("*") || s.equals("T"))
			return 3;
		else if (s.equals("/") || s.equals("Y"))
			return 4;
		else if (s.equals("(") || s.equals("F"))
			return 5;
		else if (s.equals(")"))
			return 6;
		else if (s.equals("n"))
			return 7;
		else if (s.equals("$"))
			return 8;
		else
			return -1;
	}

	public static boolean is_action(String s) {
		if (s.equals("A") || s.equals("B") || s.equals("C") || s.equals("D")
				|| s.equals("I"))
			return true;
		return false;
	}
}
