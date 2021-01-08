package gas;

import example.valueobject.DeciliterVolume;
import example.valueobject.Money;
import example.valueobject.UnitPrice;
import example.valueobject.VolumeUnit;

import java.math.RoundingMode;

public class Product {

    private final ProductType type;
    private final VolumeUnit unit;
    private final UnitPrice unitPrice;

    public Product(ProductType type, VolumeUnit unit, UnitPrice unitPrice) {
        this.type = type;
        this.unit = unit;
        this.unitPrice = unitPrice;
    }

    public ProductType type() {
        return type;
    }

    public VolumeUnit unit() {
        return unit;
    }

    public UnitPrice unitPrice() {
        return unitPrice;
    }

    public Money calculateCost(DeciliterVolume volume) {
        return unitPrice.divide(unit.deciliterMultiplier).grandTotal(volume.value(), RoundingMode.HALF_UP);
    }

    public DeciliterVolume calculateUnits(Money money) {
        return new DeciliterVolume(unitPrice.divide(unit.deciliterMultiplier).units(money, RoundingMode.DOWN));
    }
}
