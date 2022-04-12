package ru.job4j.pool.fork;

import java.util.concurrent.RecursiveTask;

public class ParallelMergeSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T value;
    private static final int NOT_FOUND = -1;
    private static final int LINE_SEARCH = 10;

    public ParallelMergeSearch(T[] array, int from, int to, T value) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        if (to - from < LINE_SEARCH) {
            return lineSearch(array, value);
        }
        int mid = (from + to) / 2;
        ParallelMergeSearch<T> leftSearch = new ParallelMergeSearch<>(array, from, mid, value);
        ParallelMergeSearch<T> rightSearch = new ParallelMergeSearch<>(array, mid + 1, to, value);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return Math.max(left, right);
    }

    private int lineSearch(T[] array, T value) {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (value.equals(array[i])) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }
}
