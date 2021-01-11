package example.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

import static example.valueobject.VolumeUnit.*;

/**
 * Describes unit price for something.
 * Responsible for performing calculations based on the price.
 */
public class UnitPrice {
    private final VolumeUnit unit;
    private final BigDecimal price;
    private final Currency currency;

    public UnitPrice(VolumeUnit unit, BigDecimal price, Currency currency) {
        Objects.requireNonNull(unit);
        Objects.requireNonNull(price);
        Objects.requireNonNull(currency);

        this.unit = unit;
        this.price = price.setScale(4, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    /**
     * Total cost for the requested units
     *
     * @param units
     * @return
     */
    public Money grandTotal(BigDecimal units) {
        return Money.from(units.multiply(price), currency);
    }

    /**
     * @param money
     * @param roundingMode
     * @return Number of units than can be purchased with the given amount of money
     */
    public BigDecimal units(Money money, RoundingMode roundingMode) {
        if (money.hasDifferentCurrency(currency)) {
            throw new IllegalArgumentException("Incorrect currency!");
        }

        return money.amount().divide(price, 1, roundingMode);
    }

    /**
     * Converts unit price into unit price in another unit
     * @param unit The target unit
     * @return Unit price in requested unit
     */
    public UnitPrice convert(VolumeUnit unit) {
        switch (this.unit) {
            case DECILITER: return fromDeciliterTo(unit);
            case LITER: return fromLiterTo(unit);
            default: throw new IllegalArgumentException(String.format("Cannot convert from %s to %s", this.unit, unit));
        }
  }

    private UnitPrice fromDeciliterTo(VolumeUnit unit) {
        switch (unit) {
            case DECILITER: return this;
            case LITER: return new UnitPrice(LITER, this.price.multiply(LITER.deciliterMultiplier), this.currency);
            case GALLON: return new UnitPrice(GALLON, this.price.multiply(GALLON.deciliterMultiplier), this.currency);
            default: throw new IllegalArgumentException("Cannot convert into " + unit);
        }
    }

    private UnitPrice fromLiterTo(VolumeUnit unit) {
        switch (unit) {
            case DECILITER: return new UnitPrice(DECILITER, this.price.divide(LITER.deciliterMultiplier, RoundingMode.HALF_UP), this.currency);
            case LITER: return this;
            case GALLON: return new UnitPrice(GALLON, this.price.divide(LITER.deciliterMultiplier, RoundingMode.HALF_UP).multiply(GALLON.deciliterMultiplier), this.currency);
            default: throw new IllegalArgumentException("Cannot convert into " + unit);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitPrice unitPrice = (UnitPrice) o;
        return unit == unitPrice.unit && price.equals(unitPrice.price) && currency.equals(unitPrice.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit, price, currency);
    }

    @Override
    public String toString() {
        return "UnitPrice{" +
                "unit=" + unit +
                ", price=" + price +
                ", currency=" + currency +
                '}';
    }
}
