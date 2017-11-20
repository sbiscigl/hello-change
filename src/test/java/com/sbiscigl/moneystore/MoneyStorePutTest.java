package com.sbiscigl.moneystore;

import com.sbiscigl.exception.CashRegisterException;
import org.junit.Assert;
import org.junit.Test;

public class MoneyStorePutTest {

    @Test
    public void ShouldAddTwenty() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        try {
            moneyStore.put(20, 1);
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals(moneyStore.show(), "$20 1 0 0 0 0");
    }

    @Test
    public void ShouldAddTen() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        try {
            moneyStore.put(20, 1);
            moneyStore.put(10, 2);
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals(moneyStore.show(), "$40 1 2 0 0 0");
    }

    @Test
    public void ShouldAddFive() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        try {
            AddTwentyTenFive(moneyStore);
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals(moneyStore.show(), "$55 1 2 3 0 0");
    }

    private static void AddTwentyTenFive(IMoneyStore moneyStore) throws CashRegisterException {
        moneyStore.put(20, 1);
        moneyStore.put(10, 2);
        moneyStore.put(5, 3);
    }

    @Test
    public void ShouldAddTwo() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        try {
            AddTwentyTenFive(moneyStore);
            moneyStore.put(2, 4);
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals(moneyStore.show(), "$63 1 2 3 4 0");
    }

    @Test
    public void ShouldAddOne() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        try {
            AddTwentyTenFive(moneyStore);
            moneyStore.put(2, 4);
            moneyStore.put(1, 5);
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals(moneyStore.show(), "$68 1 2 3 4 5");
    }

    @Test
    public void ShouldThrowExceptionWhenDenominationIsNotSupported() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        String exceptionMsg = null;
        try {
            moneyStore.put(7, 1);
        } catch (CashRegisterException e) {
            //fail if its thrown
            exceptionMsg = e.getMessage();
        }
        Assert.assertEquals(exceptionMsg, "this denomination does not exist in the register");
    }
}
