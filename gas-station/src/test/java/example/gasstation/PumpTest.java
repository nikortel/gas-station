package example.gasstation;

import example.builder.Builder;
import example.builder.ProductBuilder;
import example.builder.PumpBuilder;
import example.builder.TankBuilder;
import example.valueobject.DeciliterVolume;
import example.valueobject.Money;
import example.valueobject.UnitPrice;
import example.valueobject.VolumeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.NoSuchElementException;

import static example.gasstation.ProductType.DIESEL;
import static example.gasstation.ProductType.E10;
import static example.valueobject.Money.eur;
import static example.valueobject.VolumeUnit.GALLON;
import static example.valueobject.VolumeUnit.LITER;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PumpTest {

    private final Currency EUR = Currency.getInstance("EUR");

    @Test
    public void getLiterOfGas() {
        Pump pump = createE10PumpForTest();
        pump.fillTank(E10);

        Receipt receipt = pump.get(DeciliterVolume.from(ONE, LITER), E10);

        assertEquals(new DeciliterVolume(TEN), receipt.volume());
        assertEquals(eur(new BigDecimal("1.54")), receipt.cost());
        assertE10Receipt(receipt, new DeciliterVolume(TEN), eur(new BigDecimal("1.54")));
    }

    @Test
    public void getGallonOfGas() {
        Pump pump = createE10PumpForTest();
        pump.fillTank(E10);

        Receipt receipt = pump.get(DeciliterVolume.from(ONE, GALLON), E10);
        assertE10Receipt(receipt, new DeciliterVolume(new BigDecimal("37.85")), eur(new BigDecimal("5.84")));
    }

    @Test
    public void getTenEurosWorthOfGas() {
        Pump pump = createE10PumpForTest();
        pump.fillTank(E10);

        Receipt receipt = pump.get(eur(TEN), E10);
        assertE10Receipt(receipt, new DeciliterVolume(new BigDecimal("64.80")), eur(TEN));
    }

    @Test
    public void getsLessThanRequested() {
        Pump pump = createE10PumpForTest();
        pump.add(DeciliterVolume.from(ONE, LITER), E10);

        Receipt receipt = pump.get(eur(TEN), E10);
        assertE10Receipt(receipt, new DeciliterVolume(TEN), eur(new BigDecimal("1.5432")));
    }

    @Test
    public void noSuchProductThrowsException() {
        Pump pump = createE10PumpForTest();
        assertThrows(NoSuchElementException.class, () -> pump.fillTank(DIESEL));
    }

    @Test
    public void failsWhenNonUniqueProductsAdded() {
        Pump pump = new Pump();
        Tank tank = mock(Tank.class);
        when(tank.type()).thenReturn(E10);

        assertThrows(IllegalArgumentException.class,
                () -> pump
                .addTank(tank)
                .addTank(tank));
    }

    private Pump createE10PumpForTest() {
        Builder<Product> e10Builder = ProductBuilder
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

    private void assertE10Receipt(Receipt receipt, DeciliterVolume volume, Money cost) {
        assertEquals(volume, receipt.volume());
        assertEquals(cost, receipt.cost());
        assertEquals(new UnitPrice(LITER, new BigDecimal("1.5432"), EUR), receipt.product().unitPrice());
        assertEquals(E10, receipt.product().type());
    }

}