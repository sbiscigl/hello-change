package com.sbiscigl.moneystore;

import org.junit.Assert;
import org.junit.Test;

public class MoneyStoreTest {

    @Test
    public void ShouldCreateEmptyMoneyStore() {
        MoneyStore moneyStore = new MoneyStore();
        Assert.assertEquals(moneyStore.show(), "$0 0 0 0 0 0");
    }

    @Test
    public void ShouldCreateFromFactory() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        Assert.assertEquals(moneyStore.show(), "$0 0 0 0 0 0");
    }
}
