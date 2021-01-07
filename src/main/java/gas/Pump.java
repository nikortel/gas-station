package gas;

import example.valuebject.DeciliterVolume;
import example.valuebject.Money;
import example.valuebject.Receipt;

public class Pump {
    private Tank tank; //We might want to support more than one tank per Pump

    public Pump(Tank tank) {
        this.tank = tank;
    }

    public DeciliterVolume fillTank() {
        return tank.fill();
    }

    public Receipt get(DeciliterVolume volume) {
        DeciliterVolume removed = tank.remove(volume);
        Money cost = tank.calculateCost(removed);
        return new Receipt(removed, cost);
    }

    public Receipt get(Money money) {
        return get(tank.calculateVolume(money));
    }

}
