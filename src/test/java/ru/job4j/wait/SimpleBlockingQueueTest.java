package ru.job4j.wait;

import static org.hamcrest.core.Is.is;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {

    @Test
    public void whenWaitOffer() {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {
                    int index = 1;
                    while (true) {
                        try {
                            queue.offer(index++);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        while (producer.getState() != Thread.State.WAITING) {
            producer.getState();
        }
        assertThat(queue.getQueue().size(), is(10));
    }

    @Test
    public void whenWaitPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {
                    try {
                        queue.offer(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        producer.start();
        producer.join();
        Thread consumer = new Thread(
                () -> {
                    while (true) {
                        try {
                            queue.poll();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        consumer.start();
        while (consumer.getState() != Thread.State.WAITING) {
            consumer.getState();
        }
        assertThat(queue.getQueue().size(), is(0));
    }
}