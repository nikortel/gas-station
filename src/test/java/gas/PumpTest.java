package gas;

import example.valuebject.DeciliterVolume;
import example.valuebject.Money;
import example.valuebject.Receipt;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static gas.VolumeUnit.GALLON;
import static gas.VolumeUnit.LITER;
import static org.junit.jupiter.api.Assertions.*;

class PumpTest {

    @Test
    public void getLiterOfGas() {
        Product e95 = ProductBuilder
                .e10Builder()
                .cost(new BigDecimal("1.5432"))
                .euros()
                .perLiter()
                .build();

        Pump pump = new Pump(new Tank(DeciliterVolume.from(BigDecimal.TEN, LITER), e95));
        pump.fillTank();

        Receipt receipt = pump.get(DeciliterVolume.from(BigDecimal.ONE, LITER));
        Receipt expected = new Receipt(new DeciliterVolume(BigDecimal.TEN), Money.eur(new BigDecimal("1.54")));
        assertEquals(expected, receipt);
    }

    @Test
    public void getGallonOfGas() {
        Product e95 = ProductBuilder
                .e10Builder()
                .cost(new BigDecimal("1.5432"))
                .euros()
                .perLiter()
                .build();

        Pump pump = new Pump(new Tank(DeciliterVolume.from(BigDecimal.TEN, LITER), e95));
        pump.fillTank();

        Receipt receipt = pump.get(DeciliterVolume.from(BigDecimal.ONE, GALLON));
        Receipt expected = new Receipt(new DeciliterVolume(new BigDecimal("37.85")), Money.eur(new BigDecimal("5.84")));
        assertEquals(expected, receipt);
    }

    @Test
    public void getTenEurosWorthOfGas() {
        Product e95 = ProductBuilder
                .e10Builder()
                .cost(new BigDecimal("1.5432"))
                .euros()
                .perLiter()
                .build();

        Pump pump = new Pump(new Tank(DeciliterVolume.from(BigDecimal.TEN, LITER), e95));
        pump.fillTank();

        Receipt receipt = pump.get(Money.eur(BigDecimal.TEN));
        Receipt expected = new Receipt(new DeciliterVolume(new BigDecimal("64.80")), Money.eur(BigDecimal.TEN));
        assertEquals(expected, receipt);
    }

}