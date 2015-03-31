import java.util.Scanner;
import java.util.Stack;

public class main {

	public static String[][] num_token_table = {
			{ "X", "D", "E", ".", "+", "-" },
			{ "aa", "bb", "X", "X", "X", "X" },
			{ "bb", "bb", "ee", "cc", "X", "X" },//
			{ "cc", "dd", "X", "X", "X", "X" },
			{ "dd", "dd", "ee", "X", "X", "X" },//
			{ "ee", "hh", "X", "X", "ff", "gg" },
			{ "ff", "hh", "X", "X", "X", "X" },
			{ "gg", "hh", "X", "X", "X", "X" },
			{ "hh", "hh", "X", "X", "X", "X" } };//

	public static String[][] id_token_table = { { "X", "D", "L" },
			{ "aa", "X", "bb" }, { "bb", "cc", "dd" },//
			{ "cc", "cc", "dd" },//
			{ "dd", "cc", "dd" } };//

	public static String[][] parser_table = {
			{ "X", "d", ">", "i", "t", "n", "f", "+", "-", "*", "/", "(", ")",
					"{", "}", "=", ";", "$" },
			{ "A", "B$", "X", "X", "X", "X", "B$", "X", "X", "X", "X", "X",
					"X", "X", "X", "X", "X", "X" },
			{ "B", "C", "X", "X", "X", "X", "D", "X", "X", "X", "X", "X", "X",
					"X", "X", "X", "X", "X" },
			{ "C", "ER", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X",
					"X", "X", "X", "X", "X" },
			{ "R", "C", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X",
					"X", "!", "X", "X", "!" },
			{ "D", "X", "X", "X", "X", "X", "f(i>t){C}", "X", "X", "X", "X",
					"X", "X", "X", "X", "X", "X", "X" },
			{ "E", "d i = F;", "X", "X", "X", "X", "X", "X", "X", "X", "X",
					"X", "X", "X", "X", "X", "X", "X" },
			{ "F", "X", "X", "GS", "X", "GS", "X", "X", "X", "X", "X", "GS",
					"X", "X", "X", "X", "X", "X" },
			{ "S", "X", "X", "X", "X", "X", "X", "+GS", "-GS", "X", "X", "X",
					"!", "X", "X", "X", "!", "X" },
			{ "G", "X", "X", "HT", "X", "HT", "Fb", "Fb", "X", "X", "X", "HT",
					"X", "X", "X", "X", "X", "X" },
			{ "T", "X", "X", "X", "X", "X", "X", "!", "!", "*GT", "/GT", "X",
					"!", "X", "X", "X", "!", "X" },
			{ "H", "X", "X", "i", "X", "n", "X", "X", "X", "X", "X", "(F)",
					"X", "X", "X", "X", "X", "X" }

	};

	public static Stack temp_target = new Stack();
	public static Stack target = new Stack();
	public static Stack test = new Stack();

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

			String temp_s = "";
			if (!String.valueOf(input.charAt(input.length() - 1)).equals("}")
					&& !String.valueOf(input.charAt(input.length() - 1))
							.equals(";"))
				syntaxerror = true;
			else {
				try {
					for (int i = 0; i < input.length(); i++) {
						if (temp_s.equals("")
								&& String.valueOf(input.charAt(i)).equals("d")) {
							if (String.valueOf(input.charAt(i + 1)).equals("e")
									&& String.valueOf(input.charAt(i + 2))
											.equals("c")
									&& String.valueOf(input.charAt(i + 3))
											.equals("l")
									&& String.valueOf(input.charAt(i + 4))
											.equals("a")
									&& String.valueOf(input.charAt(i + 5))
											.equals("r")
									&& String.valueOf(input.charAt(i + 6))
											.equals("e")
									&& String.valueOf(input.charAt(i + 7))
											.equals(" ")) {
								temp_target.push("d");
							}

							else {
								lexicalerror = true;
								break;
							}
							i += 6;
							continue;
						} else if (temp_s.equals("")
								&& String.valueOf(input.charAt(i)).equals("I")) {
							if (String.valueOf(input.charAt(i + 1)).equals("f")

							) {
								temp_target.push("f");
							}

							else {
								lexicalerror = true;
								break;
							}
							i += 1;
							continue;
						} else if (temp_s.equals("")
								&& (48 <= input.charAt(i) && input.charAt(i) <= 57)
								&& String.valueOf(input.charAt(i - 1)).equals(
										">")) {

							temp_target.push("t");

							continue;
						} else if (String.valueOf(input.charAt(i)).equals(" "))
							;
						else if ((48 <= input.charAt(i) && input.charAt(i) <= 57)
								|| (65 <= input.charAt(i) && input.charAt(i) <= 90)
								|| input.charAt(i) == '.'
								|| (input.charAt(i - 1) == 69 && (input
										.charAt(i) == '+' || input.charAt(i) == '-'))) {
							temp_s += String.valueOf(input.charAt(i));
							// System.out.println(temp_s);

						} else if (is_other_terminal(String.valueOf(input
								.charAt(i)))) {
							if (!temp_s.equals("")) {
								if (is_num(temp_s)) {
									temp_target.push("n");
									temp_target.push(String.valueOf(input
											.charAt(i)));
									temp_s = "";
								} else if (is_id(temp_s)) {
									temp_target.push("i");
									temp_target.push(String.valueOf(input
											.charAt(i)));
									temp_s = "";
								} else {
									// System.out.println("L");
									lexicalerror = true;
									break;
								}

							} else
								temp_target
										.push(String.valueOf(input.charAt(i)));
						} else {
							System.out.println("1212");
							lexicalerror = true;
						}
					}
				} catch (Exception e) {
					lexicalerror = true;
				}

				temp_target.push("$");

				while (!temp_target.empty()) {
					target.push(temp_target.peek());
					temp_target.pop();
				}
			}
			/*
			 * while (!target.empty()) { System.out.println(target.peek());
			 * target.pop(); }
			 */

			test.push("A");
			temp_s = "";

			while (!target.empty() && syntaxerror == false) {
				if (lexicalerror)
					break;

				if (test.peek().toString().equals(target.peek().toString())) {
					test.pop();
					target.pop();
					continue;
				}

				if (test.peek().toString().equals(" ")) {
					test.pop();
					continue;
				}

				// System.out.println("row = "
				// +parser_get_index(test.peek().toString()) );
				// System.out.println("col  = "
				// +parser_get_index(target.peek().toString()));

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
				System.out.println("Correct!");
			System.out.print("Please Give an input :");
			syntaxerror = false;
			lexicalerror = false;

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

	public static boolean is_id(String s) {

		String current_state = "aa";
		boolean illegal = false;
		for (int i = 0; i < s.length(); i++) {

			if (get_index(String.valueOf(s.charAt(i))) == -1) {
				illegal = true;
				break;
			}
			current_state = id_token_table[get_index(current_state)][get_index(String
					.valueOf(s.charAt(i)))];
			if (current_state.equals("X"))
				break;

		}

		if ((current_state.equals("bb") || current_state.equals("dd") || current_state
				.equals("cc")) && illegal == false)
			return true;
		else
			return false;
	}

	public static boolean is_other_terminal(String s) {
		if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")
				|| s.equals("(") || s.equals(")") || s.equals("=")
				|| s.equals(";") || s.equals("}") || s.equals("{")
				|| s.equals(">"))
			return true;
		return false;
	}

	public static int get_index(String s) {
		if (s.equals("aa") || (48 <= s.charAt(0) && s.charAt(0) <= 57))
			return 1;
		else if (s.equals("bb") || (65 <= s.charAt(0) && s.charAt(0) <= 90))
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

	public static int parser_get_index(String s) {
		if (s.equals("A") || s.equals("d"))
			return 1;
		else if (s.equals("B") || s.equals(">"))
			return 2;
		else if (s.equals("C") || s.equals("i"))
			return 3;
		else if (s.equals("R") || s.equals("t"))
			return 4;
		else if (s.equals("D") || s.equals("n"))
			return 5;
		else if (s.equals("E") || s.equals("f"))
			return 6;
		else if (s.equals("F") || s.equals("+"))
			return 7;
		else if (s.equals("S") || s.equals("-"))
			return 8;
		else if (s.equals("G") || s.equals("*"))
			return 9;
		else if (s.equals("T") || s.equals("/"))
			return 10;
		else if (s.equals("H") || s.equals("("))
			return 11;
		else if (s.equals(")"))
			return 12;
		else if (s.equals("{"))
			return 13;
		else if (s.equals("}"))
			return 14;
		else if (s.equals("="))
			return 15;
		else if (s.equals(";"))
			return 16;
		else if (s.equals("$"))
			return 17;
		else
			return -1;
	}
}
