package com.project;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    // Variables compartides entre els microserveis per emmagatzemar els resultats
    private static AtomicInteger resultMicroservice1 = new AtomicInteger(0);
    private static AtomicInteger resultMicroservice2 = new AtomicInteger(0);
    private static AtomicInteger resultMicroservice3 = new AtomicInteger(0);

    public static void main(String[] args) {
        // Creem una CyclicBarrier amb 3 parties (microserveis)
        CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                // Aquest bloc s'executa quan tots els fils han arribat a la barrera
                System.out.println("Tots els microserveis han acabat. Combinant resultats...");

                // Combina els resultats dels tres microserveis sumant-los
                int resultatFinal = resultMicroservice1.get() + resultMicroservice2.get() + resultMicroservice3.get();
                
                // Mostra el resultat final combinat
                System.out.println("Resultat final combinat: " + resultatFinal);
            }
        });

        // ExecutorService per gestionar els fils
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Definim i executem les tasques (microserveis)
        executor.submit(new MicroserviceTask("Microservei 1", barrier, resultMicroservice1, 100));
        executor.submit(new MicroserviceTask("Microservei 2", barrier, resultMicroservice2, 200));
        executor.submit(new MicroserviceTask("Microservei 3", barrier, resultMicroservice3, 300));

        // Tanquem l'executor
        executor.shutdown();
    }
}

// Classe que simula la tasca de cada microservei
class MicroserviceTask implements Runnable {
    private String name;
    private CyclicBarrier barrier;
    private AtomicInteger result;
    private int baseValue;  // Valor base per simular el resultat de cada microservei

    public MicroserviceTask(String name, CyclicBarrier barrier, AtomicInteger result, int baseValue) {
        this.name = name;
        this.barrier = barrier;
        this.result = result;
        this.baseValue = baseValue;
    }

    @Override
    public void run() {
        try {
            // Simulem el processament de dades
            System.out.println(name + " està processant dades...");
            Thread.sleep((long) (Math.random() * 1000)); // Simula temps de treball variable

            // Calcula el resultat del microservei
            int resultatParcial = baseValue + (int) (Math.random() * 100); // Valor aleatori afegit al baseValue
            result.set(resultatParcial); // Emmagatzema el resultat parcial en una variable compartida

            System.out.println(name + " ha acabat el seu treball amb resultat: " + resultatParcial);

            // Esperem que els altres microserveis acabin el seu treball
            barrier.await();

        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
