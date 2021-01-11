package example.conversion;

import example.valueobject.VolumeUnit;

import java.math.BigDecimal;
import java.util.function.Function;

import static example.conversion.Conversions.*;
import static example.conversion.Conversions.literToGallon;

public class Converter {
    public static Function<BigDecimal, BigDecimal> conversion(VolumeUnit fromUnit, VolumeUnit toUnit) {
        switch (fromUnit) {
            case DECILITER:
                return fromDeciliterTo(toUnit);
            case LITER:
                return fromLiterTo(toUnit);
            case GALLON:
                return fromGallonTo(toUnit);
            default:
                throw new IllegalArgumentException(String.format("Cannot convert from %s to %s", fromUnit, toUnit));
        }
    }

    private static Function<BigDecimal, BigDecimal> fromDeciliterTo(VolumeUnit unit) {
        switch (unit) {
            case DECILITER:
                return Function.identity();
            case LITER:
                return deciliterToLiter;
            case GALLON:
                return deciliterToGallon;
            default:
                throw new IllegalArgumentException("Cannot convert into " + unit);
        }
    }

    private static Function<BigDecimal, BigDecimal> fromLiterTo(VolumeUnit unit) {
        switch (unit) {
            case DECILITER:
                return literToDeciliter;
            case LITER:
                return Function.identity();
            case GALLON:
                return literToGallon;
            default:
                throw new IllegalArgumentException("Cannot convert into " + unit);
        }
    }

    private static Function<BigDecimal, BigDecimal> fromGallonTo(VolumeUnit unit) {
        switch (unit) {
            case DECILITER:
                return gallonToDeciliter;
            case LITER:
                return gallonToLiter;
            case GALLON:
                return Function.identity();
            default:
                throw new IllegalArgumentException("Cannot convert into " + unit);
        }
    }

}
