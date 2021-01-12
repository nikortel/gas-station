package example.gasstation;

import example.builder.ProductBuilder;
import example.valueobject.DeciliterVolume;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static example.valueobject.DeciliterVolume.ZERO_VOLUME;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(JUnitPlatform.class)
public class TankTest {

    private Product e95 = ProductBuilder
            .e98Builder()
            .cost(new BigDecimal("1.5432"))
            .euros()
            .perLiter()
            .build();

    @Test
    public void fillTank() {
        var tank = new Tank(new DeciliterVolume(ONE), e95);
        DeciliterVolume volumeAdded = tank.fill();
        assertEquals(new DeciliterVolume(ONE), volumeAdded);
        assertEquals(ZERO_VOLUME, tank.capacityAvailable());
    }

    @Test
    public void fillNonEmptyTank() {
        var tank = new Tank(new DeciliterVolume(TEN), e95);
        tank.add(new DeciliterVolume(new BigDecimal("7")));

        DeciliterVolume volumeAdded = tank.fill();
        assertEquals(new DeciliterVolume(new BigDecimal("3")), volumeAdded);
        assertEquals(ZERO_VOLUME, tank.capacityAvailable());
    }

    @Test
    public void fillTankWithNoCapacityLeft() {
        var tank = new Tank(new DeciliterVolume(TEN), e95);
        tank.add(new DeciliterVolume(TEN));

        DeciliterVolume volumeAdded = tank.fill();
        assertEquals(new DeciliterVolume(BigDecimal.ZERO), volumeAdded);
        assertEquals(ZERO_VOLUME, tank.capacityAvailable());
    }

    @Test
    public void removeFromEmptyTank() {
        var tank = new Tank(new DeciliterVolume(TEN), e95);
        assertEquals(ZERO_VOLUME, tank.remove(new DeciliterVolume(TEN)));
    }

    @Test
    public void removeAllFromTank() {
        var tank = new Tank(new DeciliterVolume(TEN), e95);
        tank.fill();
        DeciliterVolume removed = tank.remove(new DeciliterVolume(TEN));

        assertEquals(new DeciliterVolume(TEN), removed);
        assertTrue(tank.isEmpty());
    }

    @Test
    public void removeSomeFromTank() {
        var tank = new Tank(new DeciliterVolume(TEN), e95);
        tank.fill();
        DeciliterVolume removed = tank.remove(new DeciliterVolume(BigDecimal.ONE));
        assertEquals(new DeciliterVolume(BigDecimal.ONE), removed);
        assertEquals(new DeciliterVolume(new BigDecimal("9")), tank.productAvailable());
    }

}