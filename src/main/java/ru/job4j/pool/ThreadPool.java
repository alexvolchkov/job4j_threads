package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);
    private boolean isStopped = false;

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(new PoolThread(tasks)));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        if (this.isStopped) {
            throw new IllegalStateException("Thread pool is stopped");
        }
        tasks.offer(job);
    }

    public synchronized void shutdown() {
        isStopped = true;
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public synchronized void waitAllTasksFinished() {
        while (!tasks.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
