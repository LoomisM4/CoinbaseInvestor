package com.mw.coinbase.service;

import com.google.gson.Gson;
import com.mw.coinbase.api.Method;
import com.mw.coinbase.api.Request;
import com.mw.coinbase.object.Account;
import com.mw.coinbase.util.Constants;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * @author LoomisM4
 */
public class AccountService {
    private Constants constants = Constants.get();

    public Optional<Account> loadAccount(String id) {
        String path = "/accounts/" + id;

        Request request = new Request();
        request.setApiKey(constants.getApiKey());
        request.setSecretKey(constants.getSecretKey());
        request.setPassphrase(constants.getPassphrase());

        try {
            return Optional.ofNullable(request.send(path, Method.GET, this::mapAccount));
        } catch (IOException | InterruptedException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private Account mapAccount(CloseableHttpResponse response) {
        try {
            String body = EntityUtils.toString(response.getEntity(), "UTF-8");
            Gson gson = new Gson();
            return gson.fromJson(body, Account.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
