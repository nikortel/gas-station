package example.conversion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

import static java.math.BigDecimal.TEN;

public class Conversions {

    public static Function<BigDecimal, BigDecimal> deciliterToLiter = x -> x.divide(TEN, RoundingMode.DOWN);
    public static Function<BigDecimal, BigDecimal> deciliterToGallon = x -> x.divide(new BigDecimal("37.8541"), RoundingMode.DOWN);

    public static Function<BigDecimal, BigDecimal> literToDeciliter = x -> x.multiply(TEN);
    public static Function<BigDecimal, BigDecimal> literToGallon = x -> x.divide(new BigDecimal("3.78541"), RoundingMode.DOWN);

    public static Function<BigDecimal, BigDecimal> gallonToDeciliter = x -> x.multiply(new BigDecimal("37.8541"));
    public static Function<BigDecimal, BigDecimal> gallonToLiter = x -> x.multiply(new BigDecimal("3.78541"));

}
