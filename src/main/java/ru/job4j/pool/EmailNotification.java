package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        if (pool.isShutdown()) {
            throw new IllegalStateException("Thread pool is stopped");
        }
            pool.submit(
                    () -> send(
                            String.format("Notification %s to email %s", user.getUserName(), user.getEmail()),
                            String.format("Add a new event to %s", user.getUserName()),
                            user.getEmail()
                    )
            );

    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }
}
