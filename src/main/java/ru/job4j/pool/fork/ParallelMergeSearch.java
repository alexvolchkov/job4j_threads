package ru.job4j.pool.fork;

import java.util.concurrent.RecursiveTask;

public class ParallelMergeSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T value;
    private static final int NOT_FOUND = -1;

    public ParallelMergeSearch(T[] array, int from, int to, T value) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        if (from == to) {
            return (array[from].equals(value)) ? from : NOT_FOUND;
        }
        int mid = (from + to) / 2;
        ParallelMergeSearch<T> leftSearch = new ParallelMergeSearch<>(array, from, mid, value);
        ParallelMergeSearch<T> rightSearch = new ParallelMergeSearch<>(array, mid + 1, to, value);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        int rsl;
        if (left == right
                || (left < right && left != NOT_FOUND)
                || (left > right && right == NOT_FOUND)) {
            rsl = left;
        } else {
            rsl = right;
        }
        return rsl;
    }
}
