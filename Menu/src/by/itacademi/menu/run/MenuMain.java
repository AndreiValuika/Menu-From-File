package by.itacademi.menu.run;

import by.itacademi.menu.entiti.Menu;
import by.itacademi.menu.entiti.MenuEdit;

public class MenuMain {

	public static void main(String[] args) {
		System.out.println("Start!");
		Menu menu = MenuEdit.loadFromFile("menu.txt");
		
		String choiceLine = MenuEdit.runMenuTitle(menu);
		System.out.println("Your choice: " + choiceLine);
	}

}
