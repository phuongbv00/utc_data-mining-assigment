package io.github.censodev.utc.dataminingtest;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class StatisticUtil {
    private StatisticUtil() {}

    /**
     * @param dataset dataset
     * @return Sorted dataset
     */
    public static List<Double> sort(List<Double> dataset) {
        return dataset.stream().sorted().collect(Collectors.toList());
    }

    /**
     * Return the mean of dataset
     *
     * @param dataset dataset
     * @return the mean of dataset
     */
    public static double mean(List<Double> dataset) {
        if (dataset.isEmpty())
            return 0D;
        return dataset.stream().collect(Collectors.averagingDouble(Double::doubleValue));
    }

    /**
     * Return the medium of dataset
     *
     * @param dataset  dataset
     * @param isSorted dataset is sorted
     * @return the medium of dataset
     */
    public static double medium(List<Double> dataset, boolean isSorted) {
        if (dataset.isEmpty())
            return 0D;
        var sorted = isSorted ? dataset : sort(dataset);
        return sorted.size() % 2 == 0
                ? (sorted.get(sorted.size() / 2 - 1) + sorted.get(sorted.size() / 2)) / 2
                : sorted.get(sorted.size() / 2);
    }

    /**
     * Return the mode of dataset
     *
     * @param dataset dataset
     * @return the mode of dataset
     */
    public static double mode(List<Double> dataset) {
        if (dataset.isEmpty())
            return 0D;
        var mode = dataset.get(0);
        var maxFreq = 1L;
        var freqMap = dataset.stream().collect(Collectors.groupingBy(n -> n, Collectors.counting()));
        for (var entry : freqMap.entrySet()) {
            if (entry.getValue() >= maxFreq) {
                mode = entry.getKey();
                maxFreq = entry.getValue();
            }
        }
        return mode;
    }

    /**
     * Return the σ of dataset
     *
     * @param dataset dataset
     * @return the σ of dataset
     */
    public static double calcSigma(List<Double> dataset) {
        var n = dataset.size();
        var sumOfSquares = dataset
                .stream()
                .mapToDouble(Double::doubleValue)
                .map(num -> num * num)
                .sum();
        return Math.sqrt(sumOfSquares / n - Math.pow(mean(dataset), 2));
    }

    /**
     * Return the z-score normalized of dataset
     *
     * @param dataset dataset
     * @return z-score normalized of dataset
     */
    public static List<Double> normalizeZScore(List<Double> dataset) {
        var sigma = calcSigma(dataset);
        var mean = mean(dataset);
        return dataset
                .stream()
                .map(vi -> (vi - mean) / sigma)
                .collect(Collectors.toList());
    }

    /**
     * Mean, medium, mode calculator
     */
    @Getter
    public static class M3 {
        private final List<Double> dataset;
        private final List<Double> sortedDataset;
        private final double mean;
        private final double medium;
        private final double mode;

        public M3(List<Double> dataset) {
            this.dataset = dataset;
            sortedDataset = sort(dataset);
            mean = mean(dataset);
            medium = medium(sortedDataset, true);
            mode = mode(dataset);
        }

        public void print() {
            var n = dataset.size();
            System.out.printf("mean = (%.1f + ... + %.1f)/%d = %.2f%n", dataset.get(0), dataset.get(n - 1), n, mean);
            System.out.println("Sorted dataset:");
            System.out.println(sortedDataset);
            if (n % 2 == 0) {
                System.out.printf("medium = (%.1f + %.1f)/2 = %.2f%n", sortedDataset.get(n / 2 - 1), sortedDataset.get(n / 2), medium);
            } else {
                System.out.printf("medium = %.1f%n", medium);
            }
            System.out.printf("mode = %.1f%n", mode);
        }
    }

    /**
     * Create Boxplot from dataset
     */
    @Getter
    public static class Boxplot {
        private final List<Double> sortedDataset;
        private final List<Double> leftSortedDataset;
        private final List<Double> rightSortedDataset;
        private final Double Q1;
        private final Double Q2;
        private final Double Q3;
        private final Double IQR;
        private final Double min;
        private final Double max;

        public Boxplot(List<Double> dataset) {
            sortedDataset = sort(dataset);
            var n = sortedDataset.size();
            leftSortedDataset = sortedDataset.subList(0, n / 2);
            rightSortedDataset = sortedDataset.subList(n % 2 == 0 ? n / 2 : n / 2 + 1, n);
            max = sortedDataset.get(n - 1);
            min = sortedDataset.get(0);
            Q2 = medium(sortedDataset, true);
            Q1 = medium(leftSortedDataset, true);
            Q3 = medium(rightSortedDataset, true);
            IQR = Q3 - Q1;
        }

        public void draw() {
            System.out.println(sortedDataset);
            System.out.println(leftSortedDataset);
            System.out.println(rightSortedDataset);
            var nL = leftSortedDataset.size();
            var nR = rightSortedDataset.size();
            System.out.println("max = " + max);
            System.out.println("min = " + min);
            if (nL % 2 == 0) {
                System.out.printf("Q1 = (%.1f + %.1f)/2 = %.2f%n", leftSortedDataset.get(nL / 2 - 1), leftSortedDataset.get(nL / 2), Q1);
            } else {
                System.out.printf("Q1 = %.1f%n", Q1);
            }
            System.out.printf("Q2 = %.2f%n", Q2);
            if (nR % 2 == 0) {
                System.out.printf("Q3 = (%.1f + %.1f)/2 = %.2f%n", rightSortedDataset.get(nR / 2 - 1), rightSortedDataset.get(nR / 2), Q3);
            } else {
                System.out.printf("Q3 = %.1f%n", Q3);
            }
            System.out.printf("IQR = Q3 - Q1 = %.2f - %.2f = %.2f%n", Q3, Q1, IQR);
        }
    }
}
