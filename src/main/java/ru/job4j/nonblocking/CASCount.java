package ru.job4j.nonblocking;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int temp;
        do {
            temp = get();
        } while (!count.compareAndSet(temp, ++temp));
    }

    public int get() {
        try {
            return count.get();
        } catch (NullPointerException e) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
    }
}
