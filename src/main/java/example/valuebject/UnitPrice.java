package example.valuebject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

/**
 * Describes unit price for something.
 * Responsible for performing calculations based on the price.
 */
public class UnitPrice {
    private final BigDecimal price;
    private final Currency currency;

    public UnitPrice(BigDecimal price, Currency currency) {
        Objects.requireNonNull(price);
        Objects.requireNonNull(currency);

        this.price = price;
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
        if (currency != money.currency()) {
            throw new IllegalArgumentException("Incorrect currency!");
        }

        return money.amount().divide(price, 1, roundingMode);
    }

    public UnitPrice divide(BigDecimal divisor) {
        return new UnitPrice(price.divide(divisor, RoundingMode.HALF_UP), currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitPrice unitPrice = (UnitPrice) o;
        return price.equals(unitPrice.price) && currency.equals(unitPrice.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, currency);
    }
}
