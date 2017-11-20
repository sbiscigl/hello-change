package com.sbiscigl;

import com.sbiscigl.moneystore.IMoneyStore;
import com.sbiscigl.moneystore.MoneyStoreFactory;
import com.sbiscigl.repl.RegisterRepl;

public class App {
    public static void main( String[] args ) {
        System.out.println("ready");
        IMoneyStore moneyStore = MoneyStoreFactory.getMoneyStore();
        RegisterRepl registerRepl = new RegisterRepl(moneyStore);
        registerRepl.uiLoop();
    }
}
