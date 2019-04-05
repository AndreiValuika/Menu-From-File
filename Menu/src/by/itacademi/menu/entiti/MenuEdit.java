package by.itacademi.menu.entiti;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuEdit {
	final static char MENU_SPLITTER = '|';
	final static char MENU_END_OF_INDEX = ')';
	final static char MENU_SUB = ' ';

	static public Menu loadFromFile(String path) {
		File file = new File(path);
		Menu menu = null;
		try (FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			String st = bufferedReader.readLine();
			StringBuilder sb = new StringBuilder();

			while (st != null) {
				sb.append(st);
				sb.append(MENU_SPLITTER);
				st = bufferedReader.readLine();
			}

			sb.append(MENU_SPLITTER);
			System.out.println(sb);
			menu = stringToMenu(sb, null);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return menu;
	}

	public static String runMenuIndex(Menu menu) {
		System.out.println("-------------");
		if (menu == null)
			return "0";
		if (menu.getSubMenu() == null)
			return (fullPathIndex(menu));

		System.out.println(subMenuToString(menu));
		System.out.println("Input number:");

		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		int choice = Integer.parseInt(in.nextLine()) - 1;

		if (menu.getSubMenu()[choice].getTitle().equals("Exit"))
			return runMenuIndex(menu.getParent());
		return runMenuIndex(menu.getSubMenu()[choice]);

	}

	public static String runMenuTitle(Menu menu) {
		String st = runMenuIndex(menu);

		if (st.equals("0"))
			return "Exit";

		int subIndex;
		StringBuilder sb = new StringBuilder("");

		for (String str : st.split(String.valueOf(MENU_SUB))) {
			subIndex = Integer.parseInt(str) - 1;
			sb.append(menu.getSubMenu()[subIndex].getTitle() + MENU_SPLITTER);
			menu = menu.getSubMenu()[subIndex];
		}
		return sb.toString();
	}

	public static String fullPathIndex(Menu menu) {

		StringBuilder stringBuilder = new StringBuilder("");

		while (menu.getParent() != null) {
			stringBuilder.append(menu.getIndex());
			stringBuilder.append(MENU_SUB);
			menu = menu.getParent();
		}
		stringBuilder.reverse();
		stringBuilder.deleteCharAt(0);

		return stringBuilder.toString();
	}

	public static String fullPathTitle(Menu menu) {
		StringBuilder stringBuilder = new StringBuilder("");

		while (menu.getParent() != null) {
			stringBuilder.append(menu.getTitle());
			stringBuilder.append(MENU_SUB);
			menu = menu.getParent();
		}
		stringBuilder.reverse();
		stringBuilder.deleteCharAt(0);

		return stringBuilder.toString();
	}

	public static String subMenuToString(Menu menu) {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append(menu.getTitle());
		stringBuilder.append('\n');

		for (Menu subMenu : menu.getSubMenu())
			stringBuilder.append(subMenu.toString() + '\n');

		return stringBuilder.toString();
	}

/*	public static String menuToString(Menu menu) 
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(String.valueOf(menu.getIndex()) + MENU_END_OF_INDEX + menu.getTitle() + '\n');
		System.out.println(stringBuilder);
		System.out.println("--------------------");

		if (menu.getSubMenu() != null) {
			for (Menu m : menu.getSubMenu()) {
				stringBuilder.append(MENU_SUB);
				stringBuilder.append(MENU_SPLITTER);
				stringBuilder.append(menuToString(m));
			}
		}
		
		return stringBuilder.toString();

	}
*/
	private static Menu stringToMenu(StringBuilder sb, Menu parent) {

		Pattern patternSub = Pattern.compile("\\" + MENU_SPLITTER + "\\S" + ".+?" + "\\" + MENU_SPLITTER + "\\S");
		Pattern patternCountSub = Pattern.compile("\\" + MENU_SPLITTER + "\\S");
		Pattern patternNoCountSub = Pattern.compile("\\" + MENU_SPLITTER + "\\s");

		Matcher matcherCountSub = patternCountSub.matcher(sb);
		Matcher matcherSub = patternSub.matcher(sb);

		int startOfItem = sb.indexOf(String.valueOf(MENU_END_OF_INDEX));
		int endOfItem = sb.indexOf(String.valueOf(MENU_SPLITTER));

		int countOfSub = 0;
		while (matcherCountSub.find()) {
			countOfSub++;
		}

		Menu generalMenu = new Menu(sb.substring(startOfItem + 1, endOfItem), parent);
		generalMenu.setIndex(Integer.parseInt(sb.substring(0, startOfItem).trim()));
		if (countOfSub == 1)
			return generalMenu;

		Menu[] subMenu = new Menu[countOfSub];
		int index = 0;
		String tempSt;
		int indexSearch = 0;
		while (matcherSub.find(indexSearch)) {
			tempSt = sb.substring(matcherSub.start() + 1, matcherSub.end() - 1) + MENU_SPLITTER;
			tempSt = tempSt.replaceAll(patternNoCountSub.toString(), String.valueOf(MENU_SPLITTER));
			indexSearch = matcherSub.end() - 2;
			subMenu[index++] = stringToMenu(new StringBuilder(tempSt), generalMenu);
		}
		subMenu[index] = new Menu(index + 1, "Exit", generalMenu);
		generalMenu.setSubMenu(subMenu);
		return generalMenu;

	}

}
