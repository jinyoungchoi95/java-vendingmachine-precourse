package vendingmachine.domain;

import java.util.Map;

public class VendingMachine {

    private final Coins coins;
    private final Products products;
    private final Money remainMoney = Money.init();

    public VendingMachine(Coins coins, Products products) {
        this.coins = coins;
        this.products = products;
    }

    public Map<Coin, Integer> currentRemainCoins() {
        return coins.currentRemainCoins();
    }
}
