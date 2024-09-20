package com.project;

import java.util.concurrent.ConcurrentHashMap;

public class DataInitializer implements Runnable {
    private ConcurrentHashMap<String, Integer> dataMap;

    public DataInitializer(ConcurrentHashMap<String, Integer> dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public void run() {
        System.out.println("Inicialitzant dades...");
        dataMap.put("saldo", 1000); // Inicialitza el saldo a 1000
        System.out.println("Dades inicialitzades: saldo = 1000");
    }
}
