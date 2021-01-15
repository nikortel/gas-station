package example.builder;

import example.gasstation.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static example.gasstation.ProductType.E10;
import static example.valueobject.UnitPrice.eur;
import static example.valueobject.UnitPrice.usd;
import static example.valueobject.VolumeUnit.GALLON;
import static example.valueobject.VolumeUnit.LITER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductBuilderTest {

    @Test
    public void buildsE10InMetricSystem() {
        Product product = ProductBuilder
                .e10Builder()
                .cost(new BigDecimal("1.5402"))
                .euros()
                .perLiter()
                .build();

        assertEquals(E10, product.type());
        assertEquals(eur(LITER, new BigDecimal("1.5402")), product.unitPrice());
    }

    @Test
    public void buildsE10InImperialSystem() {
        Product product = ProductBuilder
                .e10Builder()
                .cost(new BigDecimal("1.5402"))
                .dollars()
                .perGallon()
                .build();

        assertEquals(E10, product.type());
        assertEquals(usd(GALLON, new BigDecimal("1.5402")), product.unitPrice());
    }

}