package com.project;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.BrokenBarrierException;
import java.util.Arrays;

public class Main {

    // Variables compartides per emmagatzemar els resultats
    private static double sumaResult = 0.0;
    private static double mitjanaResult = 0.0;
    private static double desviacioResult = 0.0;

    // Conjunt de dades
    private static final double[] dades = {1.2, 3.5, 5.1, 7.3, 9.8, 11.0, 13.6, 15.9, 18.4};

    public static void main(String[] args) {
        // Creem una CyclicBarrier amb 3 parties (una per cada càlcul)
        CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                // Aquest bloc s'executa quan tots els fils han arribat a la barrera
                System.out.println("Tots els càlculs han acabat. Mostrant resultats finals...");
                System.out.println("Suma: " + sumaResult);
                System.out.println("Mitjana: " + mitjanaResult);
                System.out.println("Desviació estàndard: " + desviacioResult);
            }
        });

        // ExecutorService per gestionar els fils
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Executem les tres tasques
        executor.submit(new SumaTask(barrier));
        executor.submit(new MitjanaTask(barrier));
        executor.submit(new DesviacioTask(barrier));

        // Tanquem l'executor
        executor.shutdown();
    }

    // Tasca per calcular la suma
    static class SumaTask implements Runnable {
        private CyclicBarrier barrier;

        public SumaTask(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                // Calcula la suma de les dades
                double suma = Arrays.stream(dades).sum();
                sumaResult = suma;  // Emmagatzema el resultat de la suma

                System.out.println("Suma calculada: " + suma);

                // Esperem que els altres càlculs acabin
                barrier.await();

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    // Tasca per calcular la mitjana
    static class MitjanaTask implements Runnable {
        private CyclicBarrier barrier;

        public MitjanaTask(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                // Calcula la mitjana de les dades
                double suma = Arrays.stream(dades).sum();
                double mitjana = suma / dades.length;
                mitjanaResult = mitjana;  // Emmagatzema el resultat de la mitjana

                System.out.println("Mitjana calculada: " + mitjana);

                // Esperem que els altres càlculs acabin
                barrier.await();

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    // Tasca per calcular la desviació estàndard
    static class DesviacioTask implements Runnable {
        private CyclicBarrier barrier;

        public DesviacioTask(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                // Calcula la mitjana per a poder calcular la desviació estàndard
                double mitjana = Arrays.stream(dades).average().orElse(0.0);
                double sumSquaredDiffs = Arrays.stream(dades)
                    .map(d -> Math.pow(d - mitjana, 2))
                    .sum();
                double desviacio = Math.sqrt(sumSquaredDiffs / dades.length);
                desviacioResult = desviacio;  // Emmagatzema el resultat de la desviació estàndard

                System.out.println("Desviació estàndard calculada: " + desviacio);

                // Esperem que els altres càlculs acabin
                barrier.await();

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
