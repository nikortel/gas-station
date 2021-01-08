package example.builder;

import example.gasstation.Pump;
import example.gasstation.Tank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class PumpBuilder {
    private Collection<TankBuilder> tankBuilders = new ArrayList<>();

    public static PumpBuilder builder() {
        return new PumpBuilder();
    }

    public PumpBuilder withTank(TankBuilder tank) {
        tankBuilders.add(tank);
        return this;
    }

    public Pump build() {
        Collection<Tank> tanks = tankBuilders.stream()
                .map(TankBuilder::build)
                .collect(Collectors.toList());
        return new Pump(tanks);
    }
}
