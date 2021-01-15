package example.builder;

import example.valueobject.UnitPrice;
import example.valueobject.VolumeUnit;
import example.gasstation.Product;
import example.gasstation.ProductType;

import java.math.BigDecimal;
import java.util.Currency;

import static example.gasstation.ProductType.*;
import static example.valueobject.VolumeUnit.GALLON;
import static example.valueobject.VolumeUnit.LITER;

public class ProductBuilder implements
        ProductCostBuilder,
        ProductCostCurrencyBuilder,
        ProductMetricVolumeUnitBuilder,
        ProductImperialVolumeUnitBuilder,
        Builder<Product> {

    private ProductType type;
    private VolumeUnit unit;
    private BigDecimal costPerUnit;
    private Currency currency;

    public ProductBuilder(ProductType type) {
        this.type = type;
    }

    public static ProductCostBuilder e10Builder() {
        return new ProductBuilder(E10);
    }

    public static ProductCostBuilder e98Builder() {
        return new ProductBuilder(E98);
    }

    public static ProductCostBuilder dieselBuilder() {
        return new ProductBuilder(DIESEL);
    }

    @Override
    public Builder<Product> perLiter() {
        this.unit = LITER;
        return this;
    }

    @Override
    public Builder<Product> perGallon() {
        this.unit = GALLON;
        return this;
    }

    @Override
    public ProductCostCurrencyBuilder cost(BigDecimal costPerUnit) {
        this.costPerUnit = costPerUnit;
        return this;
    }

    @Override
    public ProductMetricVolumeUnitBuilder euros() {
        this.currency = Currency.getInstance("EUR");
        return this;
    }

    @Override
    public ProductImperialVolumeUnitBuilder dollars() {
        this.currency = Currency.getInstance("USD");
        return this;
    }

    @Override
    public Product build() {
        return new Product(type, new UnitPrice(unit, costPerUnit, currency));
    }
}
