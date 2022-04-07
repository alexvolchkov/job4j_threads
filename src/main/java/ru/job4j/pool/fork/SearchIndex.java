package ru.job4j.pool.fork;

import java.util.concurrent.ForkJoinPool;

public class SearchIndex {
    private static final int LINE_SEARCH = 10;

    public static <T> int indexOf(T[] array, T value) {
        int rsl;
        if (array.length <= LINE_SEARCH) {
            rsl = lineSearch(array, value);
        } else {
            rsl = forkJoinSearch(array, value);
        }
        return rsl;
}

    private static <T> int forkJoinSearch(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelMergeSearch<>(array, 0, array.length - 1, value));
    }

    private static <T> int lineSearch(T[] array, T value) {
        int rsl = -1;
        for (int i = 0; i < array.length; i++) {
            if (value.equals(array[i])) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }
}
