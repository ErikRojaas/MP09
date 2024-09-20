package com.project;

import java.util.concurrent.ConcurrentHashMap;

public class DataUpdater implements Runnable {
    private ConcurrentHashMap<String, Integer> dataMap;

    public DataUpdater(ConcurrentHashMap<String, Integer> dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public void run() {
        System.out.println("Actualitzant dades...");
        // Simula càlcul d'interessos
        Integer saldoActual = dataMap.get("saldo");
        if (saldoActual != null) {
            dataMap.put("saldo", saldoActual + 50); // Afegir interessos de 50
            System.out.println("Dades actualitzades: saldo = " + (saldoActual + 50));
        }
    }
}