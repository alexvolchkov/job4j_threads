package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums() {
        }

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < rsl.length; i++) {
            rsl[i] = new RolColSum.Sums();
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                rsl[i].setRowSum(rsl[i].getRowSum() + matrix[i][j]);
                rsl[j].setColSum(rsl[j].getColSum() + matrix[i][j]);
            }
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = getTask(matrix, i);
        }
        return sums;
    }

    private static Sums getTask(int[][] matrix, int index) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(
                () -> {
                    int sumRow = 0;
                    int sumCol = 0;
                    for (int i = 0; i < matrix.length; i++) {
                        sumRow += matrix[index][i];
                        sumCol += matrix[i][index];
                    }
                    return new Sums(sumRow, sumCol);
                }
        ).get();
    }
}
