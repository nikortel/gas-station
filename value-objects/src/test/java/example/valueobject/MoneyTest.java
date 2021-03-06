package example.valueobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashSet;

import static example.valueobject.Money.*;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {

    @Test
    public void doesNotAllowNegativeMoney() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> eur(new BigDecimal("-0.01")));
    }

    @Test
    public void createsEuroWithProperBaseAttributes() {
        Money euroMoney = eur(new BigDecimal("100.05"));
        assertEquals(new BigDecimal("100.05"), euroMoney.amount());
        assertEquals(2, euroMoney.amount().scale());
        assertEquals(Currency.getInstance("EUR"), euroMoney.currency());
    }

    @Test
    public void addEuros() {
        Money baseMoney = eur(new BigDecimal("100.49"));
        Money moneyToAdd = eur(new BigDecimal("50.49"));
        Money expected = eur(new BigDecimal("150.98"));

        assertEquals(expected, baseMoney.add(moneyToAdd));
    }

    @Test
    public void subtractEuros() {
        Money baseMoney = eur(new BigDecimal("100.49"));
        Money moneyToSubtract = eur(new BigDecimal("50.49"));
        Money expected = eur(new BigDecimal("50.00"));

        assertEquals(expected, baseMoney.subtract(moneyToSubtract));
    }

    @Test
    public void isMoreThan() {
        assertTrue(eur(new BigDecimal("0.01")).isMoreThan(ZERO_EURO));
    }

    @Test
    public void isLessThan() {
        assertTrue(ZERO_EURO.isLessThan(eur(new BigDecimal("0.01"))));
    }

    @ParameterizedTest
    @ValueSource(strings = { "0.99", "1.00" })
    public void isNotMoreThan(String amount) {
        assertFalse(eur(new BigDecimal(amount)).isMoreThan(eur(new BigDecimal("1.00"))));
    }

    @ParameterizedTest
    @ValueSource(strings = { "1.01", "1.00" })
    public void isNotLessThan(String amount) {
        assertFalse(eur(new BigDecimal(amount)).isLessThan(eur(new BigDecimal("1.00"))));
    }

    @Test
    public void isEqual() {
        assertEquals(ZERO_EURO, eur(new BigDecimal("0.00")));
    }

    @Test
    public void isNotEqualWhenAmountDifferent() {
        assertNotEquals(ZERO_EURO, eur(new BigDecimal("0.01")));
    }

    @Test
    public void failsLessThanComparisonWhenCurrenciesDoNotMatch() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ZERO_EURO.isLessThan(usd(new BigDecimal("0.00"))));
    }

    @Test
    public void failsMoreThanComparisonWhenCurrenciesDoNotMatch() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ZERO_EURO.isMoreThan(usd(new BigDecimal("0.00"))));
    }

    @Test
    public void cannotAddUsdToEur() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ZERO_EURO.add(usd(new BigDecimal("0.00"))));
    }

    @Test
    public void cannotSubtractUsdFromEur() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ZERO_EURO.subtract(usd(new BigDecimal("0.00"))));
    }

    @Test
    public void isNotEqualWhenDifferentCurrency() {
        assertNotEquals(ZERO_EURO, usd(new BigDecimal("0.00")));
    }

    @Test
    public void effectivelySameAmountsAreEqual() {
        assertEquals(eur(new BigDecimal("2")), eur(new BigDecimal("2.00")));
    }

    @Test
    public void distinctMoneyInSet() {
        var moneys = new HashSet<Money>(asList(
                eur(ONE),
                eur(ONE),
                eur(TEN)));
        var expected = new HashSet<Money>(asList(
                eur(ONE),
                eur(TEN)));

        assertEquals(expected, moneys);
    }

}