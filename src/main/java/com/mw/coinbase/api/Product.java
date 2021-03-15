package com.mw.coinbase.api;

import com.google.gson.annotations.SerializedName;

/**
 * @author LoomisM4
 */
public enum Product {
    @SerializedName(value = "ETH-EUR", alternate = {"eth-eur"})
    ETH("ETH-EUR");

    private String product;

    Product(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return this.product;
    }
}
