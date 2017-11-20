package com.sbiscigl.moneystore;

final public class MoneyStoreFactory {
    //static class so private constructor
    public static IMoneyStore getMoneyStore() {
        return new MoneyStore();
    }
}
