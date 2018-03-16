package com.example.piotr.cointor;

/**
 * Created by Piotr on 09.12.2017.
 */

public class Currency {
    private String name;
    private double rate;

    public Currency(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }
}
