package example.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

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
     * @param roundingMode
     * @return
     */
    public Money grandTotal(BigDecimal units, RoundingMode roundingMode) {
        var scaledGrandTotal = units
                .multiply(price)
                .setScale(currency.getDefaultFractionDigits(), roundingMode);
        return Money.from(scaledGrandTotal, currency);
    }

    /**
     * Number of units than can be purchased with the given amount of money
     *
     * @param money
     * @param roundingMode
     * @return
     */
    public BigDecimal units(Money money, RoundingMode roundingMode) {
        if (money.hasDifferentCurrency(currency)) {
            throw new IllegalArgumentException("Incorrect currency!");
        }

        return money.amount().divide(price, 1, roundingMode);
    }

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
            case LITER: return new UnitPrice(VolumeUnit.LITER, this.price.multiply(VolumeUnit.LITER.deciliterMultiplier), this.currency);
            case GALLON: return new UnitPrice(VolumeUnit.GALLON, this.price.multiply(VolumeUnit.GALLON.deciliterMultiplier), this.currency);
            default: throw new IllegalArgumentException("Cannot convert into " + unit);
        }
    }

    private UnitPrice fromLiterTo(VolumeUnit unit) {
        switch (unit) {
            case DECILITER: return new UnitPrice(VolumeUnit.DECILITER, this.price.divide(VolumeUnit.LITER.deciliterMultiplier, RoundingMode.HALF_UP), this.currency);
            case LITER: return this;
            case GALLON: return new UnitPrice(VolumeUnit.GALLON, this.price.divide(VolumeUnit.LITER.deciliterMultiplier, RoundingMode.HALF_UP).multiply(VolumeUnit.GALLON.deciliterMultiplier), this.currency);
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
