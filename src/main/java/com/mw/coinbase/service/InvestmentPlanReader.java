package com.mw.coinbase.service;

import com.google.gson.Gson;
import com.mw.coinbase.object.InvestmentPlan;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author LoomisM4
 */
public class InvestmentPlanReader {
    private static final String PATH = "/etc/coinbase";
    private static final String JSON = ".json";

    public List<InvestmentPlan> get() {
        List<InvestmentPlan> plans = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(PATH))) {
            paths.filter(p -> p.getFileName().toString().endsWith(JSON))
                    .map(p -> this.read(p.toString()))
                    .forEach(p -> p.ifPresent(plans::add));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return plans;
    }

    private Optional<InvestmentPlan> read(String file) {
        InvestmentPlan plan = null;

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = fis.readAllBytes();
            Gson gson = new Gson();
            plan = gson.fromJson(new String(bytes), InvestmentPlan.class);
            if (plan != null && !plan.validate())
                plan = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(plan);
    }
}
