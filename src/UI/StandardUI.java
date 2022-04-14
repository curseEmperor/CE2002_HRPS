package UI;
import java.util.Scanner;

public abstract class StandardUI {
	int choice;
	int qSize;
	Scanner sc;
	
	public StandardUI() {
		sc = new Scanner(System.in);
	}
	
    public abstract int showSelection();

    public abstract void mainMenu();

    public int getUserChoice(int n) {
        do {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice <= 0 || choice > n)
                    System.out.println("Please input values between 1 to " + n + " only!");
                else
                    break;
            } else {
                System.out.println("Please input only integers!");
                sc.next();
            }
        } while (choice <= 0 || choice > n);

        return choice;
    }
    
    public String getUserString() {
        String input = sc.nextLine().toUpperCase();
        return input;
    }
}

