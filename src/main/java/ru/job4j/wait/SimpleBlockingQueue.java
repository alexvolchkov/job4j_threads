package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private final int size;

    public synchronized Queue<T> getQueue() {
        return new LinkedList<>(queue);
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == size) {
                wait();
        }
        queue.offer(value);
        notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
                wait();
        }
        T value = queue.poll();
        notify();
        return value;
    }
}
