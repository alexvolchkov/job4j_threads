package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void whenSumThenMatrix3() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        long start = System.currentTimeMillis();
        RolColSum.Sums[] result = RolColSum.sum(matrix);
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        assertThat(result[0].getRowSum(), is(6));
        assertThat(result[1].getRowSum(), is(15));
        assertThat(result[2].getRowSum(), is(24));
        assertThat(result[0].getColSum(), is(12));
        assertThat(result[1].getColSum(), is(15));
        assertThat(result[2].getColSum(), is(18));
    }

    @Test
    public void whenAsyncSumThenMatrix3() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        long start = System.currentTimeMillis();
        RolColSum.Sums[] result = RolColSum.asyncSum(matrix);
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        assertThat(result[0].getRowSum(), is(6));
        assertThat(result[1].getRowSum(), is(15));
        assertThat(result[2].getRowSum(), is(24));
        assertThat(result[0].getColSum(), is(12));
        assertThat(result[1].getColSum(), is(15));
        assertThat(result[2].getColSum(), is(18));
    }
}