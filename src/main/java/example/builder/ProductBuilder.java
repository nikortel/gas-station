package example.builder;

import example.valuebject.UnitPrice;
import gas.Product;
import gas.ProductType;
import example.valuebject.VolumeUnit;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import static gas.ProductType.*;
import static example.valuebject.VolumeUnit.GALLON;
import static example.valuebject.VolumeUnit.LITER;

public class ProductBuilder {
    private ProductType type;
    private VolumeUnit unit;
    private BigDecimal costPerUnit;
    private Currency currency;

    public ProductBuilder(ProductType type) {
        this.type = type;
    }

    public static ProductBuilder e10Builder() {
        return new ProductBuilder(E10);
    }

    public static ProductBuilder e98Builder() {
        return new ProductBuilder(E98);
    }

    public static ProductBuilder dieselBuilder() {
        return new ProductBuilder(DIESEL);
    }

    public ProductBuilder perLiter() {
        this.unit = LITER;
        return this;
    }

    public ProductBuilder perGallon() {
        this.unit = GALLON;
        return this;
    }

    public ProductBuilder cost(BigDecimal costPerUnit) {
        this.costPerUnit = costPerUnit;
        return this;
    }

    public ProductBuilder euros() {
        this.currency = Currency.getInstance("EUR");
        return this;
    }

    public ProductBuilder dollars() {
        this.currency = Currency.getInstance("USD");
        return this;
    }

    public Product build() {
        Objects.requireNonNull(type);
        Objects.requireNonNull(unit);
        Objects.requireNonNull(costPerUnit);
        Objects.requireNonNull(currency);

        return new Product(type, unit, new UnitPrice(costPerUnit, currency));
    }
}
