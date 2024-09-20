package com.project;

public class Task2 implements Runnable {

    @Override
    public void run() {
        System.out.println("Task2: Comprovant l'estat de la xarxa...");
        try {
            Thread.sleep(3000); // Simula una operació de 3 segons
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Task2: Estat de la xarxa verificat.");
    }
}