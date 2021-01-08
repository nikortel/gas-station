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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return volume.equals(receipt.volume) && cost.equals(receipt.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volume, cost);
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "volume=" + volume +
                ", cost=" + cost +
                '}';
    }
}
