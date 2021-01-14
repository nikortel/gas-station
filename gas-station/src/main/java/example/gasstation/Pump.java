package example.gasstation;

import example.valueobject.DeciliterVolume;
import example.valueobject.Money;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Represents a gas pump at gas station.
 */
public class Pump {
    private final Collection<Tank> tanks = new ArrayList<>();

    public Pump() {
    }

    public void addTank(Tank tank) {
        if(findByType(tank.type()).isPresent()) {
            throw new IllegalArgumentException("One pump can contain only unique products!");
        }

        tanks.add(tank);
    }

    public DeciliterVolume fillTank(ProductType type) {
        return getByType(type).fill();
    }

    public DeciliterVolume add(DeciliterVolume volume, ProductType type) {
        return getByType(type).add(volume);
    }

    public Receipt get(DeciliterVolume volume, ProductType type) {
        Tank tank = getByType(type);
        DeciliterVolume removed = tank.remove(volume);
        Money cost = tank.calculateCost(removed);
        return new Receipt(removed, cost, tank.product());
    }

    public Receipt get(Money money, ProductType type) {
        Tank tank = getByType(type);
        return get(tank.calculateVolume(money), type);
    }

    private Optional<Tank> findByType(ProductType type) {
        return tanks.stream()
                .filter(byType(type))
                .findFirst();
    }

    private Tank getByType(ProductType type) {
        return findByType(type).orElseThrow();
    }

    private Predicate<Tank> byType(ProductType type) {
        return x -> type.equals(x.type());
    }
}
