package com.sbiscigl.repl;

import com.sbiscigl.exception.CashRegisterException;
import com.sbiscigl.moneystore.Amounts;
import com.sbiscigl.moneystore.IMoneyStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class RegisterRepl {
    private IMoneyStore moneyStore;

    public RegisterRepl(IMoneyStore ms) {
        this.moneyStore = ms;
    }

    public void uiLoop() {
        Scanner scanner = new Scanner(System.in);
        boolean shouldEnd = false;
        while (!shouldEnd) {
            String command = scanner.next();
            if (Objects.equals(command, "show")) {
                System.out.println(moneyStore.show());
            } else if(Objects.equals(command, "put")) {
                List<Integer> bills = getAmounts(scanner);
                for (int i = 0; i < bills.size(); i++) {
                    try {
                        moneyStore.put(Amounts.amounts.get(i), bills.get(i));
                    } catch (CashRegisterException e) {
                        System.out.println(e.getMessage());
                    }
                }
                System.out.println(moneyStore.show());
            } else if(Objects.equals(command, "take")) {
                List<Integer> bills = getAmounts(scanner);
                for (int i = 0; i < bills.size(); i++) {
                    try {
                        moneyStore.take(Amounts.amounts.get(i), bills.get(i));
                    } catch (CashRegisterException e) {
                        System.out.println(e.getMessage());
                    }
                }
                System.out.println(moneyStore.show());
            } else if(Objects.equals(command, "change")) {
                String bill = scanner.next();
                try {
                    int amount = Integer.parseInt(bill);
                    try {
                        System.out.println(moneyStore.change(amount));
                    } catch (CashRegisterException e) {
                        System.out.println(e.getMessage());
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("change to be in integers");
                }
            } else if(Objects.equals(command, "quit")) {
                System.out.println("bye");
                shouldEnd = true;
            }
            else {
                System.out.println("operation not permitted");
            }
        }
    }

    private List<Integer> getAmounts(Scanner s) {
        List<Integer> bills = new ArrayList<>();
        int num;
        for (int i = 0; i < 5; i++) {
            String bill = s.next();
            try {
                num = Integer.parseInt(bill);
                bills.add(num);
            } catch (NumberFormatException nfe) {
                System.out.println("bills have to be in integers");
            }
        }
        return bills;
    }
}
