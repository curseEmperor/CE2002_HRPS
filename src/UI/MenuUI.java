package UI;

import java.util.Scanner;

public class MenuUI {
	private static MenuUI instance = null;
    Scanner sc;
    int choice;

    private MenuUI() {
        sc = new Scanner(System.in);
    }

    public static MenuUI getInstance() {
        if (instance == null) instance = new MenuUI();
        return instance;
    }
}
