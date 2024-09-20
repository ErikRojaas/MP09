package com.project;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exercici2 {

    public static void main(String[] args) {
        // Crear un ExecutorService amb 2 fils
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Crear el CompletableFuture per simular la validació de dades
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Validant les dades...");
            try {
                Thread.sleep(1000); // Simula temps de validació
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Dades validades"; // Retorna el resultat de la validació
        }, executor)  // Usar l'executor personalitzat

        // Processar les dades validades i retornar un String
        .thenApplyAsync(result -> {
            System.out.println("Processant les dades...");
            try {
                Thread.sleep(1000); // Simula temps de processament
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return result + " i calculat"; // Modifica el resultat
        }, executor)  // Usar l'executor personalitzat
        
        // Mostrar el resultat final, retornant Void
        .thenAcceptAsync(result -> {
            System.out.println("Mostrant el resultat final: " + result);
        }, executor);  // Usar l'executor personalitzat

        // Esperar que totes les operacions asíncrones es completin
        future.join(); // Esperar que la cadena de futures acabi

        // Aturar l'executor després d'acabar totes les tasques
        executor.shutdown();

        System.out.println("Procés completat.");
    }
}
