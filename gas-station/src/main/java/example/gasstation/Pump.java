package example.gasstation;

import example.valueobject.DeciliterVolume;
import example.valueobject.Money;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * Represents a gad pump as gas station.
 */
public class Pump {
    private final Collection<Tank> tanks = new ArrayList<>();

    public Pump(Collection<Tank> tanks) {
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
