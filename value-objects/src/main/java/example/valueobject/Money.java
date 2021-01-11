package example.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public class Money {
    private static final Currency EURO_CURRENCY = Currency.getInstance("EUR");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");

    public static final Money ZERO_EURO = eur(new BigDecimal("0.00"));
    private final BigDecimal amount;
    private final Currency currency;
    private final int scale;

    public Money(BigDecimal amount, Currency currency, int scale) {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(currency);

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Denomination cannot be negative!");
        }

        this.amount = amount.setScale(scale, RoundingMode.HALF_UP);
        this.currency = currency;
        this.scale = scale;
    }

    public static Money from(BigDecimal amount, Currency currency) {
        return new Money(amount, currency, currency.getDefaultFractionDigits());
    }

    public static Money eur(BigDecimal amount) {
        return from(amount, EURO_CURRENCY);
    }

    public static Money usd(BigDecimal amount) {
        return from(amount, USD_CURRENCY);
    }

    public Currency currency() {
        return currency;
    }

    public BigDecimal amount() {
        return amount;
    }

    public Money add(Money money) {
        ensureCompatibility(money);
        return from(amount.add(money.amount), currency);
    }

    public boolean isMoreThan(Money money) {
        ensureCompatibility(money);
        return amount.compareTo(money.amount) > 0;
    }

    public boolean isLessThan(Money money) {
        ensureCompatibility(money);
        return amount.compareTo(money.amount) < 0;
    }

    public Money subtract(Money money) {
        ensureCompatibility(money);
        return from(amount.subtract(money.amount), currency);
    }

    public void ensureCompatibility(Money money) {
        if (hasDifferentCurrency(money.currency)) {
            throw new IllegalArgumentException(String.format("Money %s does not have same currency as money %s ", this, money));
        }
    }

    public boolean hasDifferentCurrency(Currency currency) {
        return this.currency != currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount.equals(money.amount) && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency=" + currency +
                ", scale=" + scale +
                '}';
    }
}
