package com.mw.coinbase.service;

import com.google.gson.Gson;
import com.mw.coinbase.api.Method;
import com.mw.coinbase.api.Product;
import com.mw.coinbase.api.Request;
import com.mw.coinbase.object.Account;
import com.mw.coinbase.object.Order;
import com.mw.coinbase.util.Constants;
import lombok.extern.java.Log;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * @author LoomisM4
 */
@Log
public class BuyService {
    private Constants constants = Constants.get();

    public Optional<Order> buy(Account account, Product product, BigDecimal amount) {
        BigDecimal balance = new BigDecimal(account.getAvailable());

        if (balance.compareTo(amount) >= 0) {
            // enough money
            Request request = new Request();
            request.setApiKey(constants.getApiKey());
            request.setSecretKey(constants.getSecretKey());
            request.setPassphrase(constants.getPassphrase());

            Order order = new Order();
            order.setProductId(product.toString());
            order.setSide("buy");
            order.setFunds(amount.toString());
            order.setType("market");

            request.setBody(new Gson().toJson(order));

            try {
                Optional<Order> placedOrder =
                        Optional.ofNullable(request.send("/orders", Method.POST, this::mapOrder));
                placedOrder.ifPresent(o -> log.info("Placed order: " + o.getId()));
                return placedOrder;
            } catch (IOException | InterruptedException | NoSuchAlgorithmException e) {
                log.warning("Something went wrong: " + e.getMessage());
            }

        } else {
            log.warning("Not enough money");
        }

        return Optional.empty();
    }

    private Order mapOrder(CloseableHttpResponse response) {
        try {
            String body = EntityUtils.toString(response.getEntity(), "UTF-8");
            Gson gson = new Gson();
            return gson.fromJson(body, Order.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
