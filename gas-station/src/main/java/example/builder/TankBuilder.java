package example.builder;

import example.gasstation.Product;
import example.gasstation.Tank;
import example.valueobject.DeciliterVolume;

import java.math.BigDecimal;

public class TankBuilder {
    private DeciliterVolume capacity;
    private Builder<Product> productBuilder;

    private TankBuilder(Builder<Product> productBuilder) {
        this.productBuilder = productBuilder;
        this.capacity = new DeciliterVolume(BigDecimal.ZERO);
    }

    public static TankBuilder builder(Builder<Product> productBuilder) {
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
