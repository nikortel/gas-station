package example.gasstation;

import example.builder.ProductBuilder;
import example.builder.PumpBuilder;
import example.builder.TankBuilder;
import example.valueobject.DeciliterVolume;
import example.valueobject.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static example.gasstation.ProductType.DIESEL;
import static example.gasstation.ProductType.E10;
import static example.valueobject.VolumeUnit.GALLON;
import static example.valueobject.VolumeUnit.LITER;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PumpTest {

    @Test
    public void getLiterOfGas() {
        Pump pump = createE10PumpForTest();
        pump.fillTank(E10);

        Receipt receipt = pump.get(DeciliterVolume.from(ONE, LITER), E10);
        Receipt expected = new Receipt(new DeciliterVolume(TEN), Money.eur(new BigDecimal("1.54")));
        assertEquals(expected, receipt);
    }

    @Test
    public void getGallonOfGas() {
        Pump pump = createE10PumpForTest();
        pump.fillTank(E10);

        Receipt receipt = pump.get(DeciliterVolume.from(ONE, GALLON), E10);
        Receipt expected = new Receipt(new DeciliterVolume(new BigDecimal("37.85")), Money.eur(new BigDecimal("5.84")));
        assertEquals(expected, receipt);
    }

    @Test
    public void getTenEurosWorthOfGas() {
        Pump pump = createE10PumpForTest();
        pump.fillTank(E10);

        Receipt receipt = pump.get(Money.eur(TEN), E10);
        Receipt expected = new Receipt(new DeciliterVolume(new BigDecimal("64.80")), Money.eur(TEN));
        assertEquals(expected, receipt);
    }

    @Test
    public void noSuchProductThrowsException() {
        Pump pump = createE10PumpForTest();
        assertThrows(NoSuchElementException.class, () -> pump.fillTank(DIESEL));
    }

    private Pump createE10PumpForTest() {
        ProductBuilder e10Builder = ProductBuilder
                .e10Builder()
                .cost(new BigDecimal("1.5432"))
                .euros()
                .perLiter();

        TankBuilder tankBuilder = TankBuilder.builder(e10Builder)
                .withMaximumCapacity(DeciliterVolume.from(TEN, LITER));

        return PumpBuilder.builder()
                .withTank(tankBuilder)
                .build();
    }

}