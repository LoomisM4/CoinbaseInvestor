package com.mw.coinbase.util;

import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import java.util.List;

/**
 * @author LoomisM4
 */
@Log
@UtilityClass
public class Validator {
    public static boolean checkAvailability(Object o, String errorMessage) {
        if (o == null || o.toString().trim().isEmpty()) {
            log.warning(errorMessage);
            return false;
        }

        return true;
    }
}
