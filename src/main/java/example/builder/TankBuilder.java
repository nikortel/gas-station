package example.builder;

import example.valuebject.DeciliterVolume;
import gas.Product;
import gas.Tank;

import java.math.BigDecimal;

public class TankBuilder {
    private DeciliterVolume capacity;
    private ProductBuilder productBuilder;

    private TankBuilder(ProductBuilder productBuilder) {
        this.productBuilder = productBuilder;
        this.capacity = new DeciliterVolume(BigDecimal.ZERO);
    }

    public static TankBuilder builder(ProductBuilder productBuilder) {
        return new TankBuilder(productBuilder);
    }

    public TankBuilder withMaximumCapacity(DeciliterVolume capacity) {
        this.capacity = capacity;
        return this;
    }

    public Tank build() {
        Product product = productBuilder.build();
        return new Tank(capacity, product);
    }


}
