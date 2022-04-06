package ru.job4j.nonblocking;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenIncrement() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread1 = new Thread(
                casCount::increment
        );
        Thread thread2 = new Thread(
                casCount::increment
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(casCount.get(), is(2));
    }
}