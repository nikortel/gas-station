package gas;

import example.valuebject.DeciliterVolume;
import example.valuebject.Money;

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

    public DeciliterVolume fillTank(Product.Type type) {
        return findByType(type).fill();
    }

    public Receipt get(DeciliterVolume volume, Product.Type type) {
        Tank tank = findByType(type);
        DeciliterVolume removed = tank.remove(volume);
        Money cost = tank.calculateCost(removed);
        return new Receipt(removed, cost);
    }

    public Receipt get(Money money, Product.Type type) {
        Tank tank = findByType(type);
        return get(tank.calculateVolume(money), type);
    }

    public Tank findByType(Product.Type type) {
        return tanks.stream()
                .filter(byType(type))
                .findFirst()
                .orElseThrow();
    }

    private Predicate<Tank> byType(Product.Type type) {
        return x -> type.equals(x.type());
    }

}
