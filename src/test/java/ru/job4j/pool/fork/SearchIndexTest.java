package ru.job4j.pool.fork;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SearchIndexTest {

    @Test
    public void whenFirstIndexLessThan10() {
        Integer[] array = {1, 2, 3};
        assertThat(SearchIndex.indexOf(array, 1), is(0));
    }

    @Test
    public void whenLastIndexLessThan10() {
        Integer[] array = {1, 2, 3};
        assertThat(SearchIndex.indexOf(array, 3), is(2));
    }

    @Test
    public void whenNotFoundLessThan10() {
        Integer[] array = {1, 2, 3};
        assertThat(SearchIndex.indexOf(array, 4), is(-1));
    }

    @Test
    public void whenFirstIndexMoreThan10() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        assertThat(SearchIndex.indexOf(array, 1), is(0));
    }

    @Test
    public void whenLastIndexMoreThan10() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        assertThat(SearchIndex.indexOf(array, 11), is(10));
    }

    @Test
    public void whenNotFoundMoreThan10() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        assertThat(SearchIndex.indexOf(array, 12), is(-1));
    }
}