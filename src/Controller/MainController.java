package Controller;

import java.text.ParseException;
import java.util.Scanner;

import UI.GuestUI;
import UI.ReservationUI;

public class MainController {

    // private GuestController guestController;
    // private ReservationController reservationController;
    // private RoomController roomController;
    // private MenuController menuController;

    public MainController() {
    }

    public void decideUI(int choice) throws ParseException {

        switch (choice) {
            case 1:
                GuestUI.getInstance().mainMenu();
                break;
            case 2:
                ReservationUI.getInstance().mainMenu();
                break;
        }
    }
}
