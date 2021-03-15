package com.mw.coinbase;

import com.mw.coinbase.object.Account;
import com.mw.coinbase.object.InvestmentPlan;
import com.mw.coinbase.object.Order;
import com.mw.coinbase.service.AccountService;
import com.mw.coinbase.service.BuyService;
import com.mw.coinbase.util.Constants;
import com.mw.coinbase.util.Time;
import lombok.extern.java.Log;

import java.util.Optional;

/**
 * @author LoomisM4
 */
@Log
public class Investor implements Runnable {
    private InvestmentPlan investmentPlan;
    private boolean active = false;

    public Investor(InvestmentPlan investmentPlan) {
        this.investmentPlan = investmentPlan;
        log.info(String.format("Created new Investor for %s %s",
                investmentPlan.getAmount().toString(), investmentPlan.getProduct().toString()));
    }

    @Override
    public void run() {
        this.active = true;
        while (active) {
            Optional<Account> account = new AccountService().loadAccount(investmentPlan.getWallet());
            account.ifPresent(a -> {
                Optional<Order> placedOrder = new BuyService()
                        .buy(a, investmentPlan.getProduct(), investmentPlan.getAmount());
                placedOrder.ifPresent(o -> {
                    try {
                        Thread.sleep(Time.getTime(investmentPlan.getInterval()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            });

            try {
                this.wait(Time.HOUR);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
