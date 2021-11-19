package io.github.censodev.utc.dataminingtest;

import lombok.Getter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class StatisticUtil {
    private StatisticUtil() {
    }

    /**
     * Return sorted dataset
     *
     * @param dataset dataset
     * @return sorted dataset
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
     * Return σ formula
     */
    public static String getSigmaFormula() {
        return "σ = sqrt(sum_of_squares / n - mean_of_dataset ^ 2)";
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
     * Return z-score normalization formula
     */
    public static String getZScoreNormalizationFormula() {
        return "vi' = (vi - mean_of_dataset) / σ";
    }

    /**
     * Return the z-score normalized dataset
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
     * Return min-max normalization formula
     */
    public static String getMinMaxNormalizationFormula() {
        return "vi' = (vi - min_of_dataset) / (max_of_dataset - min_of_dataset) * (max - min) + min";
    }

    /**
     * Return min-max normalized dataset
     *
     * @param dataset dataset
     * @param min min setting
     * @param max max setting
     * @return min-max normalized dataset
     */
    public static List<Double> normalizeMinMax(List<Double> dataset, double min, double max) {
        var sortedDataset = sort(dataset);
        var minOfDataset = sortedDataset.get(0);
        var maxOfDataset = sortedDataset.get(dataset.size() - 1);
        var min2max = maxOfDataset - minOfDataset;
        return dataset
                .stream()
                .map(vi -> (vi - minOfDataset) / min2max * (max - min) + min)
                .collect(Collectors.toList());
    }

    /**
     * Return decimal scaling normalization formula
     */
    public static String getDecimalScalingNormalizationFormula() {
        return "Find the smallest integer j such that max(abs(vi / 10 ^ j)) < 1 so data become vi / 10 ^ j";
    }

    /**
     * Return decimal scaling normalized dataset
     *
     * @param dataset dataset
     * @return decimal scaling normalized dataset
     */
    public static List<Double> normalizeDecimalScaling(List<Double> dataset) {
        var n = dataset.size();
        var sortedDataset = sort(dataset);
        var absFirst = Math.abs(sortedDataset.get(0));
        var absLast = Math.abs(sortedDataset.get(n - 1));
        var absMax = Math.max(absFirst, absLast);
        var j = new AtomicInteger(0);
        while (absMax / Math.pow(10, j.get()) >= 1) {
            j.incrementAndGet();
        }
        return dataset
                .stream()
                .map(vi -> vi / Math.pow(10, j.get()))
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
