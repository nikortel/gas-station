package example.valueobject;

import example.conversion.Converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static example.valueobject.VolumeUnit.DECILITER;
import static java.math.BigDecimal.ZERO;

/**
 * Represents volume of liquid in deciliters
 */
public class DeciliterVolume {
    public static DeciliterVolume ZERO_VOLUME = new DeciliterVolume(ZERO);

    private final BigDecimal volume;

    public DeciliterVolume(BigDecimal volume) {
        Objects.requireNonNull(volume);

        if (volume.compareTo(ZERO) < 0) {
            throw new IllegalArgumentException("Volume cannot me negative!");
        }

        this.volume = volume.setScale(2, RoundingMode.DOWN);
    }

    public static DeciliterVolume from(BigDecimal amount, VolumeUnit unit) {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(unit);

        return new DeciliterVolume(Converter.conversion(unit, DECILITER).apply(amount));
    }

    public boolean isMoreOrEqualThan(DeciliterVolume volume) {
        return isMoreThan(volume) || this.equals(volume);
    }

    public boolean isMoreThan(DeciliterVolume compareTo) {
        return this.volume.compareTo(compareTo.volume) > 0;
    }

    public boolean isLessThan(DeciliterVolume compareTo) {
        return this.volume.compareTo(compareTo.volume) < 0;
    }

    public DeciliterVolume add(DeciliterVolume volume) {
        return new DeciliterVolume(this.volume.add(volume.volume));
    }

    public DeciliterVolume subtract(DeciliterVolume volume) {
        if (isLessThan(volume)) {
            throw new IllegalArgumentException(String.format("Not enough to subtract %s from %s", this, volume));
        }
        return new DeciliterVolume(this.volume.subtract(volume.volume));
    }

    public BigDecimal value() {
        return volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeciliterVolume that = (DeciliterVolume) o;
        return volume.equals(that.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volume);
    }

    @Override
    public String toString() {
        return "DeciliterVolume{" +
                "volume=" + volume +
                '}';
    }
}
