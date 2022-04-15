package Enums;

public enum RoomView {
    CITY(80),
    POOL(50),
    NIL(0);

    public final double price;

    private RoomView(double price) {
        this.price = price;
    }
}
