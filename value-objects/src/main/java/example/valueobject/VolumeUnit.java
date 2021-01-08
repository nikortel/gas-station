package example.valueobject;

import java.math.BigDecimal;

public enum VolumeUnit {
    DECILITER(BigDecimal.ONE),
    LITER(BigDecimal.TEN),
    GALLON(new BigDecimal("37.8541"));

    public final BigDecimal deciliterMultiplier;
    VolumeUnit(BigDecimal deciliterMultiplier) {
        this.deciliterMultiplier = deciliterMultiplier;
    }
}
