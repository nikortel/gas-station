package example.builder;

import example.builder.ProductBuilder;
import example.gasstation.Product;
import example.valueobject.UnitPrice;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static example.gasstation.ProductType.E10;
import static example.valueobject.VolumeUnit.LITER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductBuilderTest {

    @Test
    public void buildsE10InLiter() {
        Product product = ProductBuilder
                .e10Builder()
                .cost(new BigDecimal("1.5402"))
                .euros()
                .perLiter()
                .build();

        assertEquals(E10, product.type());
        assertEquals(LITER, product.unit());
        assertEquals(new UnitPrice(new BigDecimal("1.5402"), Currency.getInstance("EUR")), product.unitPrice());
    }

}