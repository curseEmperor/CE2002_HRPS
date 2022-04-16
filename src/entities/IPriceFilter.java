package entities;

public interface IPriceFilter {
    public double execute(double rawPrice);

    public String getDescription();
}
