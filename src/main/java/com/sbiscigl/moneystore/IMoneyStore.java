package com.sbiscigl.moneystore;

import com.sbiscigl.exception.CashRegisterException;

import java.util.List;

public interface IMoneyStore {
    String put(int denomination, int amount) throws CashRegisterException;
    String take(int denomination, int amount) throws CashRegisterException;
    String change(int amount) throws CashRegisterException;
    String show();
}
