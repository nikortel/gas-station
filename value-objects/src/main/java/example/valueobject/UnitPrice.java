package example.valueobject;

import example.conversion.Converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

import static java.math.BigDecimal.ONE;

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
     * @param units
     * @return Total cost for the requested units
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
     *
     * @param toUnit The target unit
     * @return Unit price in requested unit
     */
    public UnitPrice convert(VolumeUnit toUnit) {
        BigDecimal unitInNewUnits = Converter.conversion(unit, toUnit).apply(ONE.setScale(10));

        if(unitInNewUnits.compareTo(ONE) >= 0) {
            return new UnitPrice(toUnit, price.divide(unitInNewUnits, RoundingMode.HALF_UP), currency);
        }

        BigDecimal priceMultiplier = price.divide(unitInNewUnits, RoundingMode.HALF_UP);
        return new UnitPrice(toUnit, price.multiply(priceMultiplier), currency);
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
