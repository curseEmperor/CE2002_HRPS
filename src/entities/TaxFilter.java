package entities;

import Enums.PriceFilterType;
import Enums.TaxFilterType;

public class TaxFilter implements IPriceFilter {

    private double amount;

    private PriceFilterType type;

    private TaxFilterType name;

    public TaxFilter(PriceFilterType type, TaxFilterType name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public double execute(double rawPrice) {

        switch (this.name) {
            case GST:
                amount = 7;
                break;
            case SERVICE:
                amount = 10;
                break;
        }

        switch (this.type) {
            case ADDER:
                return rawPrice - this.amount;
            case MULTIPLIER:
                return rawPrice * amount / 100;
            default:
                return 0;
        }
    }

    @Override
    public String getDescription() {
        String description = "";
        switch (this.name) {
            case SERVICE:
                description += " Service Charge ";
                break;
            case GST:
                description += " GST ";
                break;

        }

        return description;
    }
}
