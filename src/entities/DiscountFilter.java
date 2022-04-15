package entities;

import Enums.PriceFilterType;
import Enums.CreditcardType;

public class DiscountFilter implements IPriceFilter {

    private double amount;

    private PriceFilterType type;

    private CreditcardType card;

    public DiscountFilter(PriceFilterType type, CreditcardType card) {
        this.type = type;
        this.card = card;
    }

    @Override
    public double execute(double rawPrice) {

        switch (this.card) {
            case VISA:
                amount = 10;
                break;
            case MASTERCARD:
                amount = 15;
                break;
        }

        switch (type) {
            case ADDER:
                return -amount;
            case MULTIPLIER:
                return -rawPrice * amount / 100;
            default:
                return 0;
        }
    }

    @Override
    public String getDescription() {
        String description = "";
        switch (this.card) {
            case VISA:
                description += " Discount From VISA ";
                break;
            case MASTERCARD:
                description += " Discount From MASTERCARD ";
                break;
        }

        return description;
    }

}
