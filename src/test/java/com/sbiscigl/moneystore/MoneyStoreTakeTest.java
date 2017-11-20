package com.sbiscigl.moneystore;

import com.sbiscigl.exception.CashRegisterException;
import org.junit.Assert;
import org.junit.Test;

public class MoneyStoreTakeTest {

    @Test
    public void ShouldTakeTwenty() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        try {
            moneyStore.put(20, 2);
            moneyStore.take(20, 1);
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals(moneyStore.show(), "$20 1 0 0 0 0");
    }

    @Test
    public void ShouldTakeTen() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        try {
            moneyStore.put(20, 1);
            moneyStore.put(10, 2);
            moneyStore.take(10, 1);
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals(moneyStore.show(), "$30 1 1 0 0 0");
    }

    @Test
    public void ShouldTakeFive() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        try {
            moneyStore.put(20, 1);
            moneyStore.put(10, 2);
            moneyStore.put(5, 3);
            moneyStore.take(5, 2);
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals(moneyStore.show(), "$45 1 2 1 0 0");
    }

    @Test
    public void ShouldTakeTwo() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        try {
            moneyStore.put(20, 1);
            moneyStore.put(10, 2);
            moneyStore.put(5, 3);
            moneyStore.put(2, 4);
            moneyStore.take(2, 4);
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals(moneyStore.show(), "$55 1 2 3 0 0");
    }

    @Test
    public void ShouldTakeOne() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        try {
            moneyStore.put(20, 1);
            moneyStore.put(10, 2);
            moneyStore.put(5, 3);
            moneyStore.put(2, 4);
            moneyStore.put(1, 5);
            moneyStore.take(1, 3);
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals(moneyStore.show(), "$65 1 2 3 4 2");
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

    @Test
    public void ShouldThrowExceptionWhenNotEnoughLeft() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        String exceptionMsg = null;
        try {
            moneyStore.put(10, 3);
            moneyStore.put(1, 5);
            moneyStore.take(1, 6);
        } catch (CashRegisterException e) {
            //fail if its thrown
            exceptionMsg = e.getMessage();
        }
        Assert.assertEquals(exceptionMsg, "dont have enough of that denomination to make change");
        //The map should stay unchanged from the insert
        Assert.assertEquals(moneyStore.show(), "$35 0 3 0 0 5");
    }
}
