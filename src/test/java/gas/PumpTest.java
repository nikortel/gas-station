package gas;

import example.builder.ProductBuilder;
import example.builder.PumpBuilder;
import example.builder.TankBuilder;
import example.valuebject.DeciliterVolume;
import example.valuebject.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static gas.Product.Type.E10;
import static gas.VolumeUnit.GALLON;
import static gas.VolumeUnit.LITER;
import static org.junit.jupiter.api.Assertions.*;

class PumpTest {

    @Test
    public void getLiterOfGas() {
        Pump pump = createE10PumpForTest();
        pump.fillTank(E10);

        Receipt receipt = pump.get(DeciliterVolume.from(BigDecimal.ONE, LITER), E10);
        Receipt expected = new Receipt(new DeciliterVolume(BigDecimal.TEN), Money.eur(new BigDecimal("1.54")));
        assertEquals(expected, receipt);
    }

    @Test
    public void getGallonOfGas() {
        Pump pump = createE10PumpForTest();
        pump.fillTank(E10);

        Receipt receipt = pump.get(DeciliterVolume.from(BigDecimal.ONE, GALLON), E10);
        Receipt expected = new Receipt(new DeciliterVolume(new BigDecimal("37.85")), Money.eur(new BigDecimal("5.84")));
        assertEquals(expected, receipt);
    }

    @Test
    public void getTenEurosWorthOfGas() {
        Pump pump = createE10PumpForTest();
        pump.fillTank(E10);

        Receipt receipt = pump.get(Money.eur(BigDecimal.TEN), E10);
        Receipt expected = new Receipt(new DeciliterVolume(new BigDecimal("64.80")), Money.eur(BigDecimal.TEN));
        assertEquals(expected, receipt);
    }

    private Pump createE10PumpForTest() {
        ProductBuilder e95Builder = ProductBuilder
                .e10Builder()
                .cost(new BigDecimal("1.5432"))
                .euros()
                .perLiter();

        TankBuilder tankBuilder = TankBuilder.builder(e95Builder)
                .withMaximumCapacity(DeciliterVolume.from(BigDecimal.TEN, LITER));

        return PumpBuilder.builder()
                .withTank(tankBuilder)
                .build();
    }

}