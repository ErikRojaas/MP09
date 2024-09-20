package com.project;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exercici0 {

    public static void main(String[] args) {
        // Crear un pool d'execució amb 2 fils
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Crear i enviar les tasques a l'executor
        executor.submit(new Task1());
        executor.submit(new Task2());

        // Tancar l'executor per alliberar recursos
        executor.shutdown();
        System.out.println("Executor tancat.");
    }
}