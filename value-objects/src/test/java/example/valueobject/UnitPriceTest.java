package example.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnitPlatform.class)
public class UnitPriceTest {

    @Test
    public void givesSimpleUnitPrice() {
        var price = new UnitPrice(new BigDecimal("100.00"), Currency.getInstance("EUR"));
        var expected = Money.eur(new BigDecimal("200.00"));
        assertEquals(expected, price.grandTotal(new BigDecimal("2"), RoundingMode.HALF_UP));
    }

    @Test
    public void givesUnitPriceWithRounding() {
        var expected = Money.eur(new BigDecimal("150.08"));
        var price = new UnitPrice(new BigDecimal("1.0005"), expected.currency());

        assertEquals(expected, price.grandTotal(new BigDecimal("150"), RoundingMode.HALF_UP));
    }

    @Test
    public void calculatesUnitsFromMoney() {
        var money = Money.eur(new BigDecimal("50.00"));
        var price = new UnitPrice(new BigDecimal("1.5423"), money.currency());
        var expected = new BigDecimal("32.4");

        assertEquals(expected, price.units(money, RoundingMode.DOWN));
    }

}