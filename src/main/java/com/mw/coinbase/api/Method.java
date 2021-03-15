package com.mw.coinbase.api;

/**
 * @author LoomisM4
 */
public enum Method {
    GET("GET"),
    POST("POST");

    private String method;

    Method(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return this.method;
    }
}
