package Enums;

public enum RoomTypes {
    SINGLE(120),
    DOUBLE(250),
    DELUXE(400),
    SUITE(500);

    public final double price;

    private RoomTypes(double price) {
        this.price = price;
    }

}
