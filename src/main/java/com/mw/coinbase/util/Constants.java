package com.mw.coinbase.util;

import lombok.Getter;
import lombok.extern.java.Log;

/**
 * @author LoomisM4
 */
@Getter
@Log
public class Constants {
    private String apiKey;
    private String secretKey;
    private String passphrase;
    private String url = "https://api.pro.coinbase.com";

    private static Constants constants;

    private Constants() {
        apiKey = System.getenv("API_KEY");
        secretKey = System.getenv("SECRET_KEY");
        passphrase = System.getenv("PASSPHRASE");

        if (apiKey == null) {
            log.warning("No API_KEY provided");
        }

        if (secretKey == null) {
            log.warning("No SECRET_KEY provided");
        }

        if (passphrase == null) {
            log.warning("No PASSPHRASE provided");
        }

        if (apiKey == null || secretKey == null || passphrase == null) {
            System.exit(1);
        }
    }

    public static Constants get() {
        if (constants == null)
            constants = new Constants();

        return constants;
    }
}
