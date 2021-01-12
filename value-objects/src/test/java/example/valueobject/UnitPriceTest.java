package example.valueobject;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashSet;

import static example.valueobject.VolumeUnit.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UnitPriceTest {

    public static final Currency EUR = Currency.getInstance("EUR");

    @Test
    public void givesSimpleUnitPrice() {
        var price = new UnitPrice(DECILITER, new BigDecimal("100.00"), EUR);
        var expected = Money.eur(new BigDecimal("200.00"));
        assertEquals(expected, price.grandTotal(new BigDecimal("2")));
    }

    @Test
    public void givesUnitPriceWithRounding() {
        var expected = Money.eur(new BigDecimal("150.08"));
        var price = new UnitPrice(DECILITER, new BigDecimal("1.0005"), expected.currency());

        assertEquals(expected, price.grandTotal(new BigDecimal("150")));
    }

    @Test
    public void calculatesUnitsFromMoney() {
        var money = Money.eur(new BigDecimal("50.00"));
        var price = new UnitPrice(DECILITER, new BigDecimal("1.5423"), money.currency());
        var expected = new BigDecimal("32.4");

        assertEquals(expected, price.units(money, RoundingMode.DOWN));
    }

    @Test
    public void failsWhenCalculatingUnitsFromMoneyWithDifferentCurrency() {
        var money = Money.eur(new BigDecimal("50.00"));
        var price = new UnitPrice(DECILITER, new BigDecimal("1.5423"), Currency.getInstance("USD"));

        assertThrows(IllegalArgumentException.class, () -> price.units(money, RoundingMode.DOWN));
    }

    @Test
    public void convertsDecilitersToDeciliters() {
        UnitPrice unitPriceInDeciliters = new UnitPrice(DECILITER, BigDecimal.ONE, EUR);
        assertEquals(unitPriceInDeciliters, unitPriceInDeciliters.convert(DECILITER));
    }

    @Test
    public void convertsDecilitersToLiters() {
        UnitPrice unitPriceInDeciliters = new UnitPrice(DECILITER, BigDecimal.ONE, EUR);
        UnitPrice unitPriceInLiters = new UnitPrice(LITER, BigDecimal.TEN, EUR);
        assertEquals(unitPriceInLiters, unitPriceInDeciliters.convert(LITER));
    }

    @Test
    public void convertsDecilitersToGallons() {
        UnitPrice unitPriceInDeciliters = new UnitPrice(DECILITER, BigDecimal.ONE, EUR);
        UnitPrice unitPriceInGallons = new UnitPrice(GALLON, new BigDecimal("37.8541"), EUR);
        assertEquals(unitPriceInGallons, unitPriceInDeciliters.convert(GALLON));
    }

    @Test
    public void convertsLitersToDeciliters() {
        UnitPrice unitPriceInDeciliters = new UnitPrice(DECILITER, BigDecimal.ONE, EUR);
        UnitPrice unitPriceInLiters = new UnitPrice(LITER, BigDecimal.TEN, EUR);
        assertEquals(unitPriceInDeciliters, unitPriceInLiters.convert(DECILITER));
    }

    @Test
    public void convertsLitersToLiters() {
        UnitPrice unitPriceInLiters = new UnitPrice(LITER, BigDecimal.TEN, EUR);
        assertEquals(unitPriceInLiters, unitPriceInLiters.convert(LITER));
    }

    @Test
    public void convertsLitersToGallons() {
        UnitPrice unitPriceInLiters = new UnitPrice(LITER, BigDecimal.ONE, EUR);
        UnitPrice unitPriceInGallons = new UnitPrice(GALLON, new BigDecimal("3.7854"), EUR);
        assertEquals(unitPriceInGallons, unitPriceInLiters.convert(GALLON));
    }

    @Test
    public void convertsGallonsToLiters() {
        UnitPrice unitPriceInGallons = new UnitPrice(GALLON, BigDecimal.ONE, EUR);
        UnitPrice unitPriceInLiters = new UnitPrice(LITER, new BigDecimal("0.2642"), EUR);
        assertEquals(unitPriceInLiters, unitPriceInGallons.convert(LITER));
    }

    @Test
    public void distinctUnitPriceInSet() {
        var unitPrices = new HashSet<UnitPrice>(Arrays.asList(
                new UnitPrice(DECILITER, BigDecimal.ONE, EUR),
                new UnitPrice(DECILITER, BigDecimal.ONE, EUR),
                new UnitPrice(LITER, BigDecimal.ONE, EUR)));

        var expected = new HashSet<UnitPrice>(Arrays.asList(
                new UnitPrice(DECILITER, BigDecimal.ONE, EUR),
                new UnitPrice(LITER, BigDecimal.ONE, EUR)));

        assertEquals(expected, unitPrices);
    }

}