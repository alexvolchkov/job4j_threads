package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class CacheTest {

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base test = new Base(1, 1, "test");
        assertTrue(cache.add(test));
        assertThat(cache.get(1), is(test));
    }

    @Test
    public void whenNotAdd() {
        Cache cache = new Cache();
        Base test = new Base(1, 1, "test");
        cache.add(test);
        assertFalse(cache.add(test));
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base test = new Base(1, 1, "test");
        cache.add(test);
        test.setName("testUpdate");
        assertTrue(cache.update(test));
        assertThat(cache.get(1).getVersion(), is(2));
    }

    @Test
    public void whenNotUpdate() {
        Cache cache = new Cache();
        Base test = new Base(1, 1, "test");
        assertFalse(cache.update(test));
    }

    @Test(expected = OptimisticException.class)
    public void whenException() {
        Cache cache = new Cache();
        Base test = new Base(1, 1, "test");
        Base testUpdate = new Base(1, 2, "testUpdate");
        cache.add(test);
        cache.update(testUpdate);
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base test = new Base(1, 1, "test");
        cache.add(test);
        cache.delete(test);
        assertNull(cache.get(1));
    }
}