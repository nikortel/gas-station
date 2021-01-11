package example.valueobject;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;

import static example.valueobject.VolumeUnit.*;
import static java.math.BigDecimal.*;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class DeciliterVolumeTest {

    @Test
    public void tenIsMoreThanOne() {
        assertTrue(new DeciliterVolume(TEN).isMoreThan(new DeciliterVolume(ONE)));
    }

    @Test
    public void oneIsNotMoreThanTen() {
        assertFalse(new DeciliterVolume(ONE).isMoreThan(new DeciliterVolume(TEN)));
    }

    @Test
    public void oneIsNotMoreOrEqualThanTen() {
        assertFalse(new DeciliterVolume(ONE).isMoreOrEqualThan(new DeciliterVolume(TEN)));
    }

    @Test
    public void tenIsMoreOrEqualThanOne() {
        assertTrue(new DeciliterVolume(TEN).isMoreOrEqualThan(new DeciliterVolume(ONE)));
    }

    @Test
    public void tenIsMoreOrEqualThanTen() {
        assertTrue(new DeciliterVolume(TEN).isMoreOrEqualThan(new DeciliterVolume(TEN)));
    }

    @Test
    public void doesNotAllowNegativeVolume() {
        assertThrows(IllegalArgumentException.class, () -> new DeciliterVolume(new BigDecimal("-1")));
    }

    @Test
    public void doesNotCreateFromNegativeVolume() {
        assertThrows(IllegalArgumentException.class, () -> DeciliterVolume.from(new BigDecimal("-1"), DECILITER));
    }

    @Test
    public void createsDeciliterVolumeFromLiters() {
        assertEquals(new DeciliterVolume(TEN), DeciliterVolume.from(ONE, LITER));
    }

    @Test
    public void createsDeciliterVolumeFromGallon() {
        assertEquals(new DeciliterVolume(new BigDecimal("37.85")), DeciliterVolume.from(ONE, GALLON));
    }

    @Test
    public void addsVolume() {
        DeciliterVolume initial = new DeciliterVolume(ONE);
        DeciliterVolume more = new DeciliterVolume(new BigDecimal("0.01"));
        assertEquals(new DeciliterVolume(new BigDecimal("1.01")), initial.add(more));
    }

    @Test
    public void subtractsVolume() {
        DeciliterVolume initial = new DeciliterVolume(ONE);
        DeciliterVolume less = new DeciliterVolume(new BigDecimal("0.01"));
        assertEquals(new DeciliterVolume(new BigDecimal("0.99")), initial.subtract(less));
    }

    @Test
    public void cannotSubtractMoreThanThereIs() {
        DeciliterVolume initial = new DeciliterVolume(ZERO);
        DeciliterVolume less = new DeciliterVolume(new BigDecimal("0.01"));
        assertThrows(IllegalArgumentException.class, () -> initial.subtract(less));
    }

    @Test
    public void volumeEqualsSameVolume() {
        assertEquals(new DeciliterVolume((ONE)), new DeciliterVolume(ONE));
    }

    @Test
    public void differentVolumeDoesNotEqualDifferentVolume() {
        assertNotEquals(new DeciliterVolume((ONE)), new DeciliterVolume(ZERO));
    }

    @Test
    public void onlyDistinctVolumesInSet() {
        var volumes = new HashSet<DeciliterVolume>(asList(
                new DeciliterVolume(ONE),
                new DeciliterVolume(ONE),
                new DeciliterVolume(TEN)));

        var expected = new HashSet<DeciliterVolume>(asList(
                new DeciliterVolume(ONE),
                new DeciliterVolume(TEN)));

        assertEquals(expected, volumes);
    }

    @Test
    public void returnValue() {
        assertEquals(ONE.setScale(2, RoundingMode.HALF_UP), new DeciliterVolume(ONE).value());
    }
}