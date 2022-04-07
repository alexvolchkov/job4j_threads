package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

public class PoolThread implements Runnable {
    private final SimpleBlockingQueue<Runnable> tasks;
    private Thread thread = null;

    public PoolThread(SimpleBlockingQueue<Runnable> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        this.thread = Thread.currentThread();
        while (!thread.isInterrupted()) {
            try {
                Runnable runnable = tasks.poll();
                runnable.run();
            } catch (InterruptedException e) {
                thread.interrupt();
            }
        }
    }
}
