package com.sbiscigl.moneystore;

import com.sbiscigl.exception.CashRegisterException;
import org.junit.Assert;
import org.junit.Test;

public class MoneyStoreChangeTest {

    @Test
    public void ShouldMakeChangeForOne() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        try {
            moneyStore.put(1, 1);
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals("$1 0 0 0 0 1", moneyStore.show());

        try {
            Assert.assertEquals("0 0 0 0 1" ,moneyStore.change(1));
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals("$0 0 0 0 0 0", moneyStore.show());
    }

    @Test
    public void ShouldMakeChangeFromMultipleDenominations() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        try {
            moneyStore.put(1, 5);
            moneyStore.put(5, 3);
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals("$20 0 0 3 0 5", moneyStore.show());

        try {
            Assert.assertEquals("0 0 2 0 3" ,moneyStore.change(13));
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals("$7 0 0 1 0 2", moneyStore.show());
    }

    @Test
    public void shouldMakeChangeUsingHigherBills() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        try {
            moneyStore.put(1, 5);
            moneyStore.put(5, 3);
            moneyStore.put(20, 1);
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals("$40 1 0 3 0 5", moneyStore.show());

        try {
            Assert.assertEquals("1 0 0 0 0" ,moneyStore.change(20));
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals("$20 0 0 3 0 5", moneyStore.show());
    }

    @Test
    public void shouldMakeChangePdfExample() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        try {
            moneyStore.put(2, 4);
            moneyStore.put(5, 3);
            moneyStore.put(20, 1);
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals("$43 1 0 3 4 0", moneyStore.show());

        try {
            Assert.assertEquals("0 0 1 3 0" ,moneyStore.change(11));
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals("$32 1 0 2 1 0", moneyStore.show());
    }

    @Test
    public void shouldThrowExceptionIfNoMoneyIsIn() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        String exceptionMsg = null;
        try {
            moneyStore.change(1);
        } catch (CashRegisterException e) {
            //fail if its thrown
            exceptionMsg = e.getMessage();
        }
        Assert.assertEquals(exceptionMsg, "not enough in the cash register");
        //The map should stay unchanged from the insert
        Assert.assertEquals(moneyStore.show(), "$0 0 0 0 0 0");
    }

    @Test
    public void shouldThrowExceptionIfNotRightBillsIsIn() {
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        String exceptionMsg = null;
        try {
            moneyStore.put(20, 5);
        } catch (CashRegisterException e) {
            //fail if its thrown
            Assert.assertNull(e);
        }
        Assert.assertEquals("$100 5 0 0 0 0", moneyStore.show());

        try {
            moneyStore.change(5);
        } catch (CashRegisterException e) {
            exceptionMsg = e.getMessage();
        }
        Assert.assertEquals(exceptionMsg, "not enough bills to make change");
        //The map should stay unchanged from the insert
        Assert.assertEquals(moneyStore.show(), "$100 5 0 0 0 0");
    }
}
