package com.project;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class ResultCalculator implements Callable<Integer> {
    private ConcurrentHashMap<String, Integer> dataMap;

    public ResultCalculator(ConcurrentHashMap<String, Integer> dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public Integer call() throws Exception {
        // Simula lectura del saldo final
        System.out.println("Calculant resultat final...");
        Thread.sleep(1000); // Simula retard en la lectura
        return dataMap.getOrDefault("saldo", 0);
    }
}
