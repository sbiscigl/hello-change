package com.sbiscigl.moneystore;

import com.sbiscigl.exception.CashRegisterException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MoneyStore implements IMoneyStore {
    private Map<Integer, Integer> moneyMap = new HashMap<>();

    MoneyStore() {
        Amounts.amounts.forEach(i -> this.moneyMap.put(i, 0));
    }

    public String put(int denomination, int amount) throws CashRegisterException {
        if (!Amounts.amounts.contains(denomination)) {
            throw new CashRegisterException("this denomination does not exist in the register");
        }
        this.moneyMap.put(denomination, this.moneyMap.get(denomination) + amount);
        return show();
    }

    public String take(int denomination, int amount) throws CashRegisterException {
        if (!Amounts.amounts.contains(denomination)) {
            throw new CashRegisterException("this denomination does not exist in the register");
        }
        if (amount > this.moneyMap.get(denomination)) {
            throw new CashRegisterException("dont have enough of that denomination to make change");
        }
        this.moneyMap.put(denomination, this.moneyMap.get(denomination) - amount);
        return show(this.moneyMap);
    }

    public String change(int amount) throws CashRegisterException {
        //Check if total is more than we have flat out
        int total = this.moneyMap.entrySet()
                .stream()
                .mapToInt(i -> i.getValue() * i.getKey())
                .sum();

        if (amount >  total) {
            throw new CashRegisterException("not enough in the cash register");
        }

        //Create change map to represent the change we are giving
        Map <Integer, Integer> changeMap = makeChange(this.moneyMap, amount);

        int changeTotal = changeMap.entrySet()
                .stream()
                .mapToInt(i -> i.getValue() * i.getKey())
                .sum();

        if (changeTotal != amount) {
            throw new CashRegisterException("not enough bills to make change");
        }

        //Subtract change map values from the store
        Amounts.amounts.forEach( i -> moneyMap.put(i, moneyMap.get(i) - changeMap.get(i)));

        return show(changeMap);
    }

    public String show() {
        int total = this.moneyMap.entrySet()
                .stream()
                .mapToInt(i -> i.getValue() * i.getKey())
                .sum();

        return "$" + total + " " + show(this.moneyMap);
    }

    private String show(Map<Integer, Integer> map) {
        StringBuilder output = new StringBuilder();
        Amounts.amounts.forEach(i -> output.append(map.get(i)).append(" "));
        output.setLength(output.length() - 1);
        return output.toString();
    }

    private Map<Integer, Integer> makeChange(Map<Integer, Integer> map, int sum) {

        List<Integer> allBills = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                allBills.add(entry.getKey());
            }
        }

        List<List<Integer>> subsets = new ArrayList<>();
        findSubsets(allBills, sum, new ArrayList<>(), subsets);

        List<Integer> smallest = new ArrayList<>();
        if (subsets.size() > 0) {
            smallest = subsets.get(0);
            for (List<Integer> subset: subsets) {
                if (subset.size() < smallest.size()) {
                    smallest = subset;
                }
            }
        }

        Map<Integer, Integer> changeMap = new HashMap<>();
        Amounts.amounts.forEach(i -> changeMap.put(i, 0));
        smallest.forEach( i -> {
            changeMap.merge(i, 1, (a, b) -> a + b);
        });

        return changeMap;
    }

    private void findSubsets(List<Integer> list, int sum, List<Integer> currentSubset,
                        List<List<Integer>> subsets) {
        int currentSubsetSum = currentSubset.stream().mapToInt(Integer::intValue).sum();
        if (currentSubsetSum == sum) {
            subsets.add(currentSubset);
        }
        if (currentSubsetSum >= sum) {
            return;
        }
        for(int i = 0; i < list.size(); i++) {
            ArrayList<Integer> remaining = new ArrayList<>();
            int n = list.get(i);
            for (int j = i + 1; j < list.size(); j++){
                remaining.add(list.get(j));
            }
            ArrayList<Integer> newSubset = new ArrayList<>(currentSubset);
            newSubset.add(n);
            findSubsets(remaining, sum, newSubset, subsets);
        }
    }
}
