package example.builder;

import java.math.BigDecimal;

public interface ProductCostBuilder {
    ProductCostCurrencyBuilder cost(BigDecimal costPerUnit);
}
