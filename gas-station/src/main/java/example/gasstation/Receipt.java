package example.gasstation;

import example.valueobject.DeciliterVolume;
import example.valueobject.Money;

import java.util.Objects;

/**
 * Represents a receipt for volume of liquid and associated cost
 */
public class Receipt {
    private final DeciliterVolume volume;
    private final Money cost;

    public Receipt(DeciliterVolume volume, Money cost) {
        Objects.requireNonNull(volume);
        Objects.requireNonNull(cost);
        this.volume = volume;
        this.cost = cost;
    }

    public DeciliterVolume volume() {
        return volume;
    }

    public Money cost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "volume=" + volume +
                ", cost=" + cost +
                '}';
    }
}
