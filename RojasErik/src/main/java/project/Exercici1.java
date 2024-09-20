package com.project;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Exercici1 {

    public static void main(String[] args) {
        // Crear un ConcurrentHashMap per compartir dades
        ConcurrentHashMap<String, Integer> dataMap = new ConcurrentHashMap<>();

        // Crear un pool d'execució amb 3 fils
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Crear i enviar les tasques a l'executor
        executor.submit(new DataInitializer(dataMap));
        executor.submit(new DataUpdater(dataMap));

        // Crear i enviar la tasca Callable a l'executor
        Future<Integer> resultFuture = executor.submit(new ResultCalculator(dataMap));

        try {
            // Recollir i mostrar el resultat final
            Integer finalResult = resultFuture.get();
            System.out.println("Saldo final: " + finalResult);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Tancar l'executor per alliberar recursos
            executor.shutdown();
            System.out.println("Executor tancat.");
        }
    }
}