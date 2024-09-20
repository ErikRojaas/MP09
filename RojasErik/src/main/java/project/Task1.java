package com.project;

public class Task1 implements Runnable {

    @Override
    public void run() {
        System.out.println("Task1: Registrant esdeveniments de sistema...");
        try {
            Thread.sleep(2000); // Simula una operació de 2 segons
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Task1: Esdeveniments registrats.");
    }
}
