package com.sbiscigl.moneystore;

final public class MoneyStoreFactory {
    //static class so private constructor
    private MoneyStoreFactory(){}

    public static IMoneyStore getMoneyStore() {
        return new MoneyStore();
    }
}
