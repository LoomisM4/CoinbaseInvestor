
package com.mw.coinbase.object;

import lombok.Data;

@Data
public class Account {
    private String id;
    private String currency;
    private String balance;
    private String hold;
    private String available;
    private String profileId;
    private Boolean tradingEnabled;
}
