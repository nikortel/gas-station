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
    private final Product product;

    public Receipt(DeciliterVolume volume, Money cost, Product product) {
        Objects.requireNonNull(volume);
        Objects.requireNonNull(cost);
        this.volume = volume;
        this.cost = cost;
        this.product = product;
    }

    public DeciliterVolume volume() {
        return volume;
    }

    public Money cost() {
        return cost;
    }

    public Product product() {
        return product;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Receipt{");
        sb.append("volume=").append(volume);
        sb.append(", cost=").append(cost);
        sb.append(", product=").append(product);
        sb.append('}');
        return sb.toString();
    }
}
