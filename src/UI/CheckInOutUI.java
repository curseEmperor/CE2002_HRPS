package UI;

import java.util.Scanner;

public class CheckInOutUI {
	private static CheckInOutUI instance = null;
    Scanner sc;
    int choice;

    private CheckInOutUI() {
        sc = new Scanner(System.in);
    }

    public static CheckInOutUI getInstance() {
        if (instance == null) instance = new CheckInOutUI();
        return instance;
    }
}