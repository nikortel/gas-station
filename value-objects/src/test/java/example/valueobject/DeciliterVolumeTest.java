package example.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class DeciliterVolumeTest {

    @Test
    public void tenIsMoreThanOne() {
        assertTrue(new DeciliterVolume(BigDecimal.TEN).isMoreThan(new DeciliterVolume(BigDecimal.ONE)));
    }

    @Test
    public void oneIsNotMoreThanTen() {
        assertFalse(new DeciliterVolume(BigDecimal.ONE).isMoreThan(new DeciliterVolume(BigDecimal.TEN)));
    }

    @Test
    public void oneIsNotMoreOrEqualThanTen() {
        assertFalse(new DeciliterVolume(BigDecimal.ONE).isMoreOrEqualThan(new DeciliterVolume(BigDecimal.TEN)));
    }

    @Test
    public void tenIsMoreOrEqualThanOne() {
        assertTrue(new DeciliterVolume(BigDecimal.TEN).isMoreOrEqualThan(new DeciliterVolume(BigDecimal.ONE)));
    }

    @Test
    public void tenIsMoreOrEqualThanTen() {
        assertTrue(new DeciliterVolume(BigDecimal.TEN).isMoreOrEqualThan(new DeciliterVolume(BigDecimal.TEN)));
    }

}