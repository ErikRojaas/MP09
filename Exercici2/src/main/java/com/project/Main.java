package com.project;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        // Capacitat de l'aparcament
        final int capacitat = 5;
        
        // Creem l'aparcament amb un semàfor amb "capacitat" permisos
        ParkingLot aparcament = new ParkingLot(capacitat);

        // ExecutorService per gestionar els fils
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Simulem l'entrada de 10 cotxes
        for (int i = 1; i <= 10; i++) {
            final int cotxeID = i;
            executor.submit(() -> {
                try {
                    aparcament.entrarAparcament(cotxeID);
                    // Simulem que el cotxe està aparcat durant un temps aleatori
                    TimeUnit.SECONDS.sleep((long) (Math.random() * 10) + 1);
                    aparcament.sortirAparcament(cotxeID);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Tanquem l'executor
        executor.shutdown();

        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}

// Classe ParkingLot que gestiona l'aparcament
class ParkingLot {
    private final Semaphore semafor;

    // Constructor amb la capacitat de l'aparcament
    public ParkingLot(int capacitat) {
        this.semafor = new Semaphore(capacitat);
    }

    // Mètode per simular l'entrada d'un cotxe
    public void entrarAparcament(int cotxeID) {
        try {
            System.out.println("🅿️ [Cotxe " + cotxeID + "] intentant entrar a l'aparcament...");
            // Simulem un petit retard abans d'intentar entrar
            TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 500));
            
            // El cotxe intenta obtenir un permís (si no hi ha espai, espera)
            semafor.acquire();
            System.out.println("✅ [Cotxe " + cotxeID + "] ha entrat a l'aparcament.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Mètode per simular la sortida d'un cotxe
    public void sortirAparcament(int cotxeID) {
        System.out.println("🚗 [Cotxe " + cotxeID + "] surt de l'aparcament.");
        // Alliberem un permís quan el cotxe surt
        semafor.release();
    }
}
