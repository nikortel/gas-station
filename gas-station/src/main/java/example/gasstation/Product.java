package example.gasstation;

import example.valueobject.DeciliterVolume;
import example.valueobject.Money;
import example.valueobject.UnitPrice;

import java.math.RoundingMode;

import static example.valueobject.VolumeUnit.DECILITER;

public class Product {

    private final ProductType type;
    private final UnitPrice unitPrice;

    public Product(ProductType type, UnitPrice unitPrice) {
        this.type = type;
        this.unitPrice = unitPrice;
    }

    public ProductType type() {
        return type;
    }

    public UnitPrice unitPrice() {
        return unitPrice;
    }

    public Money calculateCost(DeciliterVolume volume) {
        return unitPrice.convert(DECILITER).grandTotal(volume.value(), RoundingMode.HALF_UP);
    }

    public DeciliterVolume calculateUnits(Money money) {
        return new DeciliterVolume(unitPrice.convert(DECILITER).units(money, RoundingMode.DOWN));
    }
}
