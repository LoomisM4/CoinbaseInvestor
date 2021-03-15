package com.mw.coinbase.object;

import com.mw.coinbase.api.Product;
import com.mw.coinbase.util.Validator;
import lombok.Data;
import lombok.extern.java.Log;

import java.math.BigDecimal;

/**
 * @author LoomisM4
 */
@Log
@Data
public class InvestmentPlan {
    private Product product;
    private BigDecimal amount;
    private String wallet;
    private String interval;

    public boolean validate() {
        return Validator.checkAvailability(this.product, "No product ID specified!") &&
                Validator.checkAvailability(this.amount, "No amount specified!") &&
                Validator.checkAvailability(this.wallet, "No wallet specified!") &&
                Validator.checkAvailability(this.interval, "No interval specified!");
    }
}
