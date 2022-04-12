import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import UI.MainUI;

public class App {
    public static void main(String[] args) throws Exception {
        MainUI mainUI = new MainUI();

        mainUI.run();

        // GuestController.getInstance().storeData();
        // GuestController.getInstance().loadData();

        // Guest guest = new Guest("1234", "test1", "singare", "91235", "masia", 'M',
        // "nationality");
        // System.out.println(guest);

        // Date test = new Date();
        // SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        // System.out.println(formatter.format(test));

        // String time1 = "16:00:00";
        // String time2 = "19:00:00";

        // SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        // Date date1 = format.parse(time1);
        // Date date2 = format.parse(time2);
        // long difference = date2.getTime() - date1.getTime();
        // System.out.println(difference);
    }
}
