package gas;

import example.valueobject.DeciliterVolume;
import example.valueobject.Money;

import java.util.Objects;

/**
 * Tank is a holder of a liquid product
 */
public class Tank {
    private final DeciliterVolume capacity;
    private DeciliterVolume currentVolume; //Volume can be changed
    private Product product; //Product and/or price might change

    public Tank(DeciliterVolume capacity, Product product) {
        Objects.requireNonNull(capacity);
        this.capacity = capacity;
        this.product = product;
        this.currentVolume = DeciliterVolume.ZERO;
    }

    /**
     * Adds liquid to tank up to maximum capacity.
     * @param volume
     * @return Volume added
     */
    public DeciliterVolume add(DeciliterVolume volume) {
        DeciliterVolume capacityAvailable = capacity.subtract(currentVolume);
        DeciliterVolume added = capacityAvailable.isMoreOrEqualThan(volume) ? volume : capacityAvailable;
        currentVolume = currentVolume.add(added);
        return added;
    }

    /**
     * Removes liquid from tank
     * @param volume Requested amount of liquid
     * @return Removed amount of liquid
     */
    public DeciliterVolume remove(DeciliterVolume volume) {
        DeciliterVolume removed = currentVolume.isMoreOrEqualThan(volume) ? volume : currentVolume;
        currentVolume = currentVolume.subtract(removed);
        return removed;
    }

    /**
     * Fills tank up to capacity
     * @return Amount of liquid added
     */
    public DeciliterVolume fill() {
        return add(capacity);
    }

    /**
     * @return How much can be added to the tank
     */
    public DeciliterVolume capacityAvailable() {
        return capacity.subtract(currentVolume);
    }

    /**
     * @return How much product is there in the tank
     */
    public DeciliterVolume productAvailable() {
        return currentVolume;
    }

    /**
     * @return True if the tank is empty
     */
    public boolean isEmpty() {
        return DeciliterVolume.ZERO.equals(currentVolume);
    }

    /**
     * Calculates the total cost of taking some amount of product
     * @param deciliterVolume Amount of product for which the cost is calculated
     * @return Cost for the product
     */
    public Money calculateCost(DeciliterVolume deciliterVolume) {
        return product.calculateCost(deciliterVolume);
    }

    /**
     * Calculates the amount of product that can be obtained with the given amount of money
     * @param money
     * @return Amount of product that can be obtained with the given money
     */
    public DeciliterVolume calculateVolume(Money money) {
        return product.calculateUnits(money);
    }

    public ProductType type() {
        return product.type();
    }

}
