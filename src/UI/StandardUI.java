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
                choice = n+1;
                sc.next();
            }
        } while (choice <= 0 || choice > n);

        return choice;
    }
    
    public String getUserString() {
        String input = sc.nextLine().toUpperCase();
        return input;
    }
    
    public String getUserYN() {
    	String select = getUserString();
    	while (!(select.compareToIgnoreCase("Y")==0 || select.compareToIgnoreCase("N")==0)) {
        	System.out.println("Please enter only Y/N");
            System.out.println("Y/N? ");
            select = getUserString();
            System.out.println(select);
        }
    	return select;
    }
}

