package example.builder;

import example.gasstation.Product;

public interface ProductMetricVolumeUnitBuilder {
    Builder<Product> perLiter();
}
