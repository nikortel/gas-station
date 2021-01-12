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


    @Test
    public void givesSimpleUnitPrice() {
        var price = UnitPrice.eur(DECILITER, new BigDecimal("100.00"));
        var expected = Money.eur(new BigDecimal("200.00"));
        assertEquals(expected, price.grandTotal(new BigDecimal("2")));
    }

    @Test
    public void givesUnitPriceWithRounding() {
        var expected = Money.eur(new BigDecimal("150.08"));
        var price = UnitPrice.eur(DECILITER, new BigDecimal("1.0005"));

        assertEquals(expected, price.grandTotal(new BigDecimal("150")));
    }

    @Test
    public void calculatesUnitsFromMoney() {
        var money = Money.eur(new BigDecimal("50.00"));
        var price = UnitPrice.eur(DECILITER, new BigDecimal("1.5423"));
        var expected = new BigDecimal("32.4");

        assertEquals(expected, price.units(money));
    }

    @Test
    public void failsWhenCalculatingUnitsFromMoneyWithDifferentCurrency() {
        var money = Money.eur(new BigDecimal("50.00"));
        var price = new UnitPrice(DECILITER, new BigDecimal("1.5423"), Currency.getInstance("USD"));

        assertThrows(IllegalArgumentException.class, () -> price.units(money));
    }

    @Test
    public void convertsDecilitersToDeciliters() {
        UnitPrice unitPriceInDeciliters = UnitPrice.eur(DECILITER, BigDecimal.ONE);
        assertEquals(unitPriceInDeciliters, unitPriceInDeciliters.convert(DECILITER));
    }

    @Test
    public void convertsDecilitersToLiters() {
        UnitPrice unitPriceInDeciliters = UnitPrice.eur(DECILITER, BigDecimal.ONE);
        UnitPrice unitPriceInLiters = UnitPrice.eur(LITER, BigDecimal.TEN);
        assertEquals(unitPriceInLiters, unitPriceInDeciliters.convert(LITER));
    }

    @Test
    public void convertsDecilitersToGallons() {
        UnitPrice unitPriceInDeciliters = UnitPrice.eur(DECILITER, BigDecimal.ONE);
        UnitPrice unitPriceInGallons = UnitPrice.eur(GALLON, new BigDecimal("37.8541"));
        assertEquals(unitPriceInGallons, unitPriceInDeciliters.convert(GALLON));
    }

    @Test
    public void convertsLitersToDeciliters() {
        UnitPrice unitPriceInDeciliters = UnitPrice.eur(DECILITER, BigDecimal.ONE);
        UnitPrice unitPriceInLiters = UnitPrice.eur(LITER, BigDecimal.TEN);
        assertEquals(unitPriceInDeciliters, unitPriceInLiters.convert(DECILITER));
    }

    @Test
    public void convertsLitersToLiters() {
        UnitPrice unitPriceInLiters = UnitPrice.eur(LITER, BigDecimal.TEN);
        assertEquals(unitPriceInLiters, unitPriceInLiters.convert(LITER));
    }

    @Test
    public void convertsLitersToGallons() {
        UnitPrice unitPriceInLiters = UnitPrice.eur(LITER, BigDecimal.ONE);
        UnitPrice unitPriceInGallons = UnitPrice.eur(GALLON, new BigDecimal("3.7854"));
        assertEquals(unitPriceInGallons, unitPriceInLiters.convert(GALLON));
    }

    @Test
    public void convertsGallonsToLiters() {
        UnitPrice unitPriceInGallons = UnitPrice.eur(GALLON, BigDecimal.ONE);
        UnitPrice unitPriceInLiters = UnitPrice.eur(LITER, new BigDecimal("0.2642"));
        assertEquals(unitPriceInLiters, unitPriceInGallons.convert(LITER));
    }

    @Test
    public void distinctUnitPriceInSet() {
        var unitPrices = new HashSet<UnitPrice>(Arrays.asList(
                UnitPrice.eur(DECILITER, BigDecimal.ONE),
                UnitPrice.eur(DECILITER, BigDecimal.ONE),
                UnitPrice.eur(LITER, BigDecimal.ONE)));

        var expected = new HashSet<UnitPrice>(Arrays.asList(
                UnitPrice.eur(DECILITER, BigDecimal.ONE),
                UnitPrice.eur(LITER, BigDecimal.ONE)));

        assertEquals(expected, unitPrices);
    }

}