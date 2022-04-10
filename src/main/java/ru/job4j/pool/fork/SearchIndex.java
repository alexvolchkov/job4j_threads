package ru.job4j.pool.fork;

import java.util.concurrent.ForkJoinPool;

public class SearchIndex {

    public static <T> int indexOf(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelMergeSearch<>(array, 0, array.length - 1, value));
    }
}
