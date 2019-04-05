package by.itacademi.menu.entiti;

public class Menu {
	private int index;
	private String title="";
	private Menu parent;
	private Menu[] subMenu;
	
	
	
	
	public Menu(String title) {
		this.title = title;
		this.parent=null;
		this.index=0;
	}
	
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public Menu(String title, Menu parent) {
		super();
		this.title = title;
		this.parent = parent;
	}
	
	public Menu(int index, String title, Menu parent) {
		super();
		this.index = index;
		this.title = title;
		this.parent = parent;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Menu[] getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(Menu[] subMenu) {
		this.subMenu = subMenu;
	}

	public String toString() {
		return this.index+" "+this.title;
		
	}
	
	
}
