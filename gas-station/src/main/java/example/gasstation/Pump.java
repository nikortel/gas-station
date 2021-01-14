package example.gasstation;

import example.valueobject.DeciliterVolume;
import example.valueobject.Money;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents a gas pump at gas station.
 */
public class Pump {
    private final Collection<Tank> tanks = new ArrayList<>();

    public Pump(Collection<Tank> tanks) {
        long uniqueProducts = tanks.stream()
                .map(Tank::product)
                .map(Product::type)
                .distinct()
                .count();

        if(uniqueProducts != tanks.size()) {
            throw new IllegalArgumentException("One pump can contain only unique products!");
        }

        this.tanks.addAll(tanks);
    }

    public DeciliterVolume fillTank(ProductType type) {
        return findByType(type).fill();
    }

    public DeciliterVolume add(DeciliterVolume volume, ProductType type) {
        return findByType(type).add(volume);
    }

    public Receipt get(DeciliterVolume volume, ProductType type) {
        Tank tank = findByType(type);
        DeciliterVolume removed = tank.remove(volume);
        Money cost = tank.calculateCost(removed);
        return new Receipt(removed, cost, tank.product());
    }

    public Receipt get(Money money, ProductType type) {
        Tank tank = findByType(type);
        return get(tank.calculateVolume(money), type);
    }

    public Tank findByType(ProductType type) {
        return tanks.stream()
                .filter(byType(type))
                .findFirst()
                .orElseThrow();
    }

    private Predicate<Tank> byType(ProductType type) {
        return x -> type.equals(x.type());
    }

}
