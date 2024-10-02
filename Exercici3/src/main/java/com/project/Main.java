package com.project;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        // Nombre màxim de connexions simultànies
        final int maxConnexions = 3;
        
        // Creem la pàgina web amb un semàfor que limita les connexions
        WebPage paginaWeb = new WebPage(maxConnexions);

        // ExecutorService per gestionar els fils
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Simulem múltiples connexions (10 usuaris)
        for (int i = 1; i <= 10; i++) {
            final int usuariID = i;
            executor.submit(() -> {
                try {
                    paginaWeb.connectar(usuariID);
                    // Simulem que l'usuari està connectat durant un temps
                    TimeUnit.SECONDS.sleep((long) (Math.random() * 10) + 1);
                    paginaWeb.desconnectar(usuariID);
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

// Classe WebPage que controla el nombre de connexions simultànies
class WebPage {
    private final Semaphore semafor;

    // Constructor que inicialitza el semàfor amb el nombre màxim de connexions
    public WebPage(int maxConnexions) {
        this.semafor = new Semaphore(maxConnexions);
    }

    // Mètode per simular la connexió d'un usuari
    public void connectar(int usuariID) {
        try {
            System.out.println("🌐 [Usuari " + usuariID + "] intentant connectar a la pàgina...");
            // El semàfor controla l'accés. Si no hi ha permisos disponibles, ha d'esperar.
            semafor.acquire();
            System.out.println("✅ [Usuari " + usuariID + "] connectat a la pàgina.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Mètode per simular la desconnexió d'un usuari
    public void desconnectar(int usuariID) {
        System.out.println("❌ [Usuari " + usuariID + "] desconnectat de la pàgina.");
        // Allibera un permís quan l'usuari es desconnecta
        semafor.release();
    }
}
