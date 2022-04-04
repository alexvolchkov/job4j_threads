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
                        queue.offer(index++);
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
                        queue.offer(1);
                }
        );
        producer.start();
        producer.join();
        Thread consumer = new Thread(
                () -> {
                    while (true) {
                        queue.poll();
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