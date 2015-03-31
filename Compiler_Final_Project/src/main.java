/*
 * 兩種Set的型態:char,int 
 * 輸入格式如 : char A={a,b,c}  
 * 集合的名稱只可以一個大寫英文字母,char 型態內只可以放小寫英文字母.
 * 功能 :
 * 	Print() : 印出括號內集合所有元素.
 * 	聯集 : A+B , 交集 : AB , 差集 : A-B , 乘集 : A*B.
 * 	Add(A,a) : 在A集合內加入a元素 ( 若型態不符,將印出Error!) 
 *		Remove(A,a) : 在A集合內加入a元素 (若A中沒a,甚麼事都不會作).
 * 	Search(A,a) : 在A集合內搜尋a元素,並印出其位置(若A中沒a,印出Not Found).
 *		|A| : 直接印出A集合內元素個數.
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

public class main {

	public static Stack temp_target = new Stack();
	public static Stack target = new Stack();
	public static Stack test = new Stack();

	public static boolean lexicalerror = false;
	public static boolean syntaxerror = false;
	public static boolean undefine = false;
	public static boolean reduplicate_set = false;
	public static boolean wrong_type = false;

	public static ArrayList value = new ArrayList(); // for scanner
	public static ArrayList data = new ArrayList(); // global
	public static ArrayList data_temp = new ArrayList();// global temp
	public static ArrayList temp_list = new ArrayList();
	public static ArrayList op_temp_list = new ArrayList();
	
	public static String type = "";
	public static String target_type="";

	public static String output = "";

	public static String[][] parser_table = {
			{ "X", "c", "i", "n", "p", "a", "s", "r", "h", "g", ",", "+", "-",
					"*", ")", "}", "|" },
			{ "A", "B$", "X", "B$", "B$", "B$", "B$", "B$", "X", "X", "X", "X",
					"X", "X", "X", "X", "B$" },
			{ "B", "cR iH={C}I", "X", "nR iH={D}I", "p(E)L", "a(iV,G)Q",
					"s(i,G)M", "r(i,G)N", "X", "X", "X", "X", "X", "X", "X",
					"X", "|i|O" },
			{ "C", "X", "X", "X", "X", "X", "X", "X", "hHC", "X", ",hHC", "X",
					"X", "X", "X", "!", "X" },
			{ "D", "X", "X", "X", "X", "X", "X", "X", "X", "gHD", ",gHD", "X",
					"X", "X", "X", "!", "X" },
			{ "E", "X", "iJF", "X", "X", "X", "X", "X", "X", "X", "X", "X",
					"X", "X", "X", "X", "X" },
			{ "F", "X", "iTF", "X", "X", "X", "X", "X", "X", "X", "!", "+iKF",
					"-iSF", "*iUF", "!", "X", "!" },
			{ "G", "X", "X", "X", "X", "X", "X", "X", "Ph", "Pg", "X", "X", "X",
					"X", "X", "X", "X" }

	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Please Give a command :");
		while (keyboard.hasNextLine()) {
			String input = keyboard.nextLine();
			// ----------------------------Scanner-----------------------------------
			String temp_int = "";
			try {
				for (int i = 0; i < input.length(); i++) {
					// char
					if (input.charAt(i) == 'c' && input.charAt(i + 1) == 'h'
							&& input.charAt(i + 2) == 'a'
							&& input.charAt(i + 3) == 'r'
							&& input.charAt(i + 4) == ' ') {
						temp_target.push("c");
						value.add("h");
						i += 4;
					}
					// int
					else if (input.charAt(i) == 'i'
							&& input.charAt(i + 1) == 'n'
							&& input.charAt(i + 2) == 't'
							&& input.charAt(i + 3) == ' ') {
						temp_target.push("n");
						value.add("g");
						i += 3;
					}
					// add
					else if (input.charAt(i) == 'A'
							&& input.charAt(i + 1) == 'd'
							&& input.charAt(i + 2) == 'd') {
						temp_target.push("a");
						i += 2;
					}
					// search
					else if (input.charAt(i) == 'S'
							&& input.charAt(i + 1) == 'e'
							&& input.charAt(i + 2) == 'a'
							&& input.charAt(i + 3) == 'r'
							&& input.charAt(i + 4) == 'c'
							&& input.charAt(i + 5) == 'h') {
						temp_target.push("s");
						i += 5;
					}
					// remove
					else if (input.charAt(i) == 'R'
							&& input.charAt(i + 1) == 'e'
							&& input.charAt(i + 2) == 'm'
							&& input.charAt(i + 3) == 'o'
							&& input.charAt(i + 4) == 'v'
							&& input.charAt(i + 5) == 'e') {
						temp_target.push("r");
						i += 5;
					}
					// Print
					else if (input.charAt(i) == 'P'
							&& input.charAt(i + 1) == 'r'
							&& input.charAt(i + 2) == 'i'
							&& input.charAt(i + 3) == 'n'
							&& input.charAt(i + 4) == 't') {
						temp_target.push("p");
						i += 4;
					}
					// id
					else if (65 <= input.charAt(i) && input.charAt(i) <= 90) {
						temp_target.push("i");
						value.add(String.valueOf(input.charAt(i)));
					}
					// letter
					else if (97 <= input.charAt(i) && input.charAt(i) <= 122) {
						temp_target.push("h");
						value.add(String.valueOf(input.charAt(i)));
					}
					// Integer
					else if (48 <= input.charAt(i) && input.charAt(i) <= 57) {
						temp_int += String.valueOf(input.charAt(i));
					}
					// other terminal
					else if (is_other_terminal(String.valueOf(input.charAt(i)))) {
						if (!temp_int.equals("")) {
							temp_target.push("g");
							value.add(String.valueOf(temp_int));
							temp_int = "";
						}
						temp_target.push(String.valueOf(input.charAt(i)));
					}
					// space
					else if (input.charAt(i) == ' ') {
						if (!temp_int.equals("")) {
							temp_target.push("g");
							value.add(String.valueOf(temp_int));

							temp_int = "";
						}
					} else {
						lexicalerror = true;
						break;
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

			// while (!target.empty()) { System.out.println(target.peek());
			// target.pop(); }

			// -------------------------------- Parser + SDT ------------------------------
			test.push("A");
			String temp_s = "";
			int count = 0; // count number of element.

			while (!test.empty() && !lexicalerror && !reduplicate_set) {

				if (test.peek().toString().equals(target.peek().toString())) {

					test.pop();
					target.pop();
					continue;
				}

				// SDT
				if (is_action(test.peek().toString())) {
					if (test.peek().toString().equals("R")){
						data_temp.add(value.get(0));
						value.remove(0);
					}
					// H : (1)檢查是否重複宣告(2)加入data_temp list.
					else if (test.peek().toString().equals("H")) {
						if (data.contains(value.get(0).toString())
								&& (65 <= value.get(0).toString().charAt(0) && value
										.get(0).toString().charAt(0) <= 90)) {
							reduplicate_set = true;
							output = "Reduplicate Set !!";
							break;
						}
						data_temp.add(value.get(0).toString());
						value.remove(0);
					// I : 將data_temp list的資料移至data list,並以"|"在list中隔開每組SET.
					} else if (test.peek().toString().equals("I")) {
						data.addAll(data_temp);
						data.add("|");
						/*while(!data.isEmpty()){
							System.out.println(data.get(0).toString());
							data.remove(0);
						}*/
					// J : 將準備要做運算的SET存至temp_list.
					} else if (test.peek().toString().equals("J")) {
						if (data.contains(value.get(0))) {
							int index = data.indexOf(value.get(0)) + 1;
							value.remove(0);
							while (!data.get(index).toString().equals("|")) {
								temp_list.add(data.get(index));
								index++;
							}
						} else {
							undefine = true;
							break;
						}
					// K : 聯集運算.
					} else if (test.peek().toString().equals("K")) {
						get_set();
						if (!undefine) {
							temp_list = union(temp_list, op_temp_list);
							op_temp_list.clear();
						} else
							break;
					// S : 差集運算.
					} else if (test.peek().toString().equals("S")) {
						get_set();
						if (!undefine) {
							temp_list = diff(temp_list, op_temp_list);
							op_temp_list.clear();
						} else
							break;
					// T : 交集運算.
					} else if (test.peek().toString().equals("T")) {
						get_set();
						if (!undefine) {
							temp_list = intersect(temp_list, op_temp_list);
							op_temp_list.clear();
						} else
							break;
					// U : 乘集運算.
					} else if (test.peek().toString().equals("U")) {
						get_set();
						if (!undefine) {
							temp_list = mul(temp_list, op_temp_list);
							op_temp_list.clear();
						} else
							break;
					// L : Print.
					} else if (test.peek().toString().equals("L")) {
						for (int i = 0; i < temp_list.size(); i++) {
							System.out.printf("%s ", temp_list.get(i)
									.toString());
						}
						System.out.println();
					// M : Search
					} else if (test.peek().toString().equals("M")) {
						if (data.contains(value.get(0))) {
							int index = data.indexOf(value.get(0)) + 1;
							int start = data.indexOf(value.get(0));
							value.remove(0);
							output = "Not Found!";
							while (!data.get(index).toString().equals("|")) {
								if (data.get(index).toString()
										.equals(value.get(0).toString())) {
									output = "Find! At position :"
											+ String.valueOf(index - start);
									break;
								}
								index++;
							}

						} else {
							undefine = true;
							break;
						}
					// N : Remove
					} else if (test.peek().toString().equals("N")) {
						if (data.contains(value.get(0))) {
							int index = data.indexOf(value.get(0)) + 1;
							value.remove(0);
							while (!data.get(index).toString().equals("|")) {
								if (data.get(index).toString()
										.equals(value.get(0).toString())) {
									data.remove(index);
									break;
								}
								index++;
							}

						} else {
							undefine = true;
							break;
						}
					// O : Count.
					} else if (test.peek().toString().equals("O")) {
						if (data.contains(value.get(0))) {
							int index = data.indexOf(value.get(0)) + 1;
							int c = 0;
							while (!data.get(index).toString().equals("|")) {
								c += 1;
								index++;
							}
							output = "Number of element = " + String.valueOf(c);

						} else {
							undefine = true;
							break;
						}
					// Q : add.
					} else if (test.peek().toString().equals("Q")) {
						if(!target_type.equals(type)){
							wrong_type = true;
							break;
						}
						if (data.contains(value.get(0))) {
							int index = data.indexOf(value.get(0)) + 1;
							value.remove(0);
							data.add(index, value.get(0));

						} else {
							undefine = true;
							break;
						}
					// V : 存取型態.
					}else if (test.peek().toString().equals("V")) {
						if (data.contains(value.get(0))) {
							int index = data.indexOf(value.get(0)) -1;
							type = data.get(index).toString();
							//System.out.println(type);
						} else {
							undefine = true;
							break;
						}
					// P : 確認型態正確(將型態暫時丟到target_type以便等等使用).
					}else if (test.peek().toString().equals("P")) {
						test.pop();
						//System.out.println(test.peek().toString());
						target_type = test.peek().toString();
						test.push("P");
					}
					

					test.pop();
					continue;
				}

				if (test.peek().toString().equals(" ")) {
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

			if (undefine)
				output = "Error , Undefine !!";
			if (reduplicate_set)
				output = "Error , Reduplicate Set !!";
			if(wrong_type)
				output="Error , assign wrong type !!";

			if (lexicalerror == true)
				System.out.println("lexical error!");
			else if (syntaxerror == true)
				System.out.println("syntax error!");
			else if (!output.equals(""))
				System.out.println(output);
			System.out.print("Please Give a command :");
			syntaxerror = false;
			lexicalerror = false;

			value.clear();
			temp_list.clear();
			data_temp.clear();
			
			type ="";
			target_type="";

			while (!target.empty()) {
				target.pop();
			}
			while (!test.empty()) {
				test.pop();
			}
			output = "";
			undefine = false;
			reduplicate_set = false;
			wrong_type=false;
		}
	}

	public static boolean is_other_terminal(String s) {
		if (s.equals("=") || s.equals("{") || s.equals("}") || s.equals("(")
				|| s.equals(")") || s.equals("+") || s.equals("-")
				|| s.equals("*") || s.equals(",") || s.equals("|"))
			return true;
		return false;
	}

	public static int parser_get_index(String s) {
		if (s.equals("c") || s.equals("A"))
			return 1;
		else if (s.equals("i") || s.equals("B"))
			return 2;
		else if (s.equals("n") || s.equals("C"))
			return 3;
		else if (s.equals("p") || s.equals("D"))
			return 4;
		else if (s.equals("a") || s.equals("E"))
			return 5;
		else if (s.equals("s") || s.equals("F"))
			return 6;
		else if (s.equals("r") || s.equals("G"))
			return 7;
		else if (s.equals("h"))
			return 8;
		else if (s.equals("g"))
			return 9;
		else if (s.equals(","))
			return 10;
		else if (s.equals("+"))
			return 11;
		else if (s.equals("-"))
			return 12;
		else if (s.equals("*"))
			return 13;
		else if (s.equals(")"))
			return 14;
		else if (s.equals("}"))
			return 15;
		else if (s.equals("|"))
			return 16;
		else
			return -1;
	}

	// 判斷是否為Translation action.
	public static boolean is_action(String s) {
		if (s.equals("H") || s.equals("I") || s.equals("J") || s.equals("K")
				|| s.equals("S") || s.equals("T") || s.equals("U")
				|| s.equals("L") || s.equals("M") || s.equals("N")
				|| s.equals("O") || s.equals("Q")|| s.equals("R")|| s.equals("V")|| s.equals("P"))
			return true;
		return false;
	}

	// 進行運算時使用,在data list中取得集合並暫存於op_temp_list.
	public static void get_set() {
		if (data.contains(value.get(0))) {
			int index = data.indexOf(value.get(0)) + 1;
			value.remove(0);
			while (!data.get(index).toString().equals("|")) {
				op_temp_list.add(data.get(index));
				index++;
			}
		} else
			undefine = true;
	}

	// 交集
	public static ArrayList intersect(ArrayList ls, ArrayList ls2) {
		ls.retainAll(ls2);
		return ls;
	}

	// 聯集
	public static ArrayList union(ArrayList ls, ArrayList ls2) {
		for (int i = 0; i < ls2.size(); i++) {
			if (!ls.contains(ls2.get(i)))
				ls.add(ls2.get(i));
		}
		return ls;
	}

	// 差集
	public static ArrayList diff(ArrayList ls, ArrayList ls2) {
		ls.removeAll(ls2);
		return ls;
	}

	// 乘集
	public static ArrayList mul(ArrayList ls, ArrayList ls2) {
		ArrayList list = new ArrayList();
		String temp = "";
		for (int i = 0; i < ls.size(); i++) {
			for (int j = 0; j < ls2.size(); j++) {
				temp = ls.get(i).toString() + ls2.get(j).toString();
				list.add(temp);
			}
		}
		return list;
	}

}
