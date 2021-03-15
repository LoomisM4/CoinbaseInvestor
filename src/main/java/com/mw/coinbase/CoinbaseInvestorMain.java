package com.mw.coinbase;

import com.mw.coinbase.object.InvestmentPlan;
import com.mw.coinbase.service.InvestmentPlanReader;

import java.util.List;

/**
 * @author LoomisM4
 */
public class CoinbaseInvestorMain {
    public static void main(String[] args) {
        List<InvestmentPlan> plans = new InvestmentPlanReader().get();

        plans.stream()
                .map(Investor::new)
                .forEach(i -> new Thread(i).start());
    }
}
