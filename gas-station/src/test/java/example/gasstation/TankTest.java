package example.gasstation;

import example.valueobject.DeciliterVolume;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static example.valueobject.DeciliterVolume.ZERO_VOLUME;
import static example.valueobject.Money.eur;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TankTest {

    @Mock
    private Product e98;

    @Test
    public void fillTank() {
        var tank = new Tank(new DeciliterVolume(ONE), e98);
        DeciliterVolume volumeAdded = tank.fill();
        assertEquals(new DeciliterVolume(ONE), volumeAdded);
        assertEquals(ZERO_VOLUME, tank.capacityAvailable());
    }

    @Test
    public void fillNonEmptyTank() {
        var tank = new Tank(new DeciliterVolume(TEN), e98);
        tank.add(new DeciliterVolume(new BigDecimal("7")));

        DeciliterVolume volumeAdded = tank.fill();
        assertEquals(new DeciliterVolume(new BigDecimal("3")), volumeAdded);
        assertEquals(ZERO_VOLUME, tank.capacityAvailable());
    }

    @Test
    public void fillTankWithNoCapacityLeft() {
        var tank = new Tank(new DeciliterVolume(TEN), e98);
        tank.add(new DeciliterVolume(TEN));

        DeciliterVolume volumeAdded = tank.fill();
        assertEquals(new DeciliterVolume(BigDecimal.ZERO), volumeAdded);
        assertEquals(ZERO_VOLUME, tank.capacityAvailable());
    }

    @Test
    public void removeFromEmptyTank() {
        var tank = new Tank(new DeciliterVolume(TEN), e98);
        assertEquals(ZERO_VOLUME, tank.remove(new DeciliterVolume(TEN)));
    }

    @Test
    public void removeAllFromTank() {
        var tank = new Tank(new DeciliterVolume(TEN), e98);
        tank.fill();
        DeciliterVolume removed = tank.remove(new DeciliterVolume(TEN));

        assertEquals(new DeciliterVolume(TEN), removed);
        assertTrue(tank.isEmpty());
    }

    @Test
    public void removeSomeFromTank() {
        var tank = new Tank(new DeciliterVolume(TEN), e98);
        tank.fill();
        DeciliterVolume removed = tank.remove(new DeciliterVolume(BigDecimal.ONE));
        assertEquals(new DeciliterVolume(BigDecimal.ONE), removed);
        assertEquals(new DeciliterVolume(new BigDecimal("9")), tank.productAvailable());
    }

    @Test
    public void calculatesCost() {
        var tank = new Tank(new DeciliterVolume(TEN), e98);
        when(e98.calculateCost(new DeciliterVolume(TEN))).thenReturn(eur(new BigDecimal("1.54")));
        assertEquals(eur(new BigDecimal("1.54")), tank.calculateCost(new DeciliterVolume(TEN)));
    }

    @Test
    public void calculatesVolume() {
        var tank = new Tank(new DeciliterVolume(TEN), e98);
        when(e98.calculateUnits(eur(TEN))).thenReturn(new DeciliterVolume(ONE));
        assertEquals(new DeciliterVolume(ONE), tank.calculateVolume(eur(TEN)));
    }

}