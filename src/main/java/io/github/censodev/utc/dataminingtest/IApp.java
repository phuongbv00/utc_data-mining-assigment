package io.github.censodev.utc.dataminingtest;

import java.util.List;
import java.util.stream.Collectors;

public interface IApp {
    void runCalcMeanMediumModeJob();

    void runDrawBoxplot();

    default Double mean(List<Double> numbers) {
        return numbers.stream().collect(Collectors.averagingDouble(Double::doubleValue));
    }

    default Double medium(List<Double> numbers, boolean isSorted) {
        var sorted = isSorted
                ? numbers
                : numbers.stream().sorted().collect(Collectors.toList());
        return sorted.size() % 2 == 0
                ? (sorted.get(sorted.size() / 2 - 1) + sorted.get(sorted.size() / 2)) / 2
                : sorted.get(sorted.size() / 2);
    }

    default Double mode(List<Double> numbers) {
        var mode = numbers.get(0);
        var maxFreq = 1L;
        var freqMap = numbers.stream().collect(Collectors.groupingBy(n -> n, Collectors.counting()));
        for (var entry : freqMap.entrySet()) {
            if (entry.getValue() >= maxFreq) {
                mode = entry.getKey();
                maxFreq = entry.getValue();
            }
        }
        return mode;
    }

    default void drawBoxplot(List<Double> sortedList) {
        var n = sortedList.size();
        var listLeft = sortedList.subList(0, n / 2);
        var listRight = sortedList.subList(n % 2 == 0 ? n / 2 : n / 2 + 1, n);
        System.out.println(sortedList);
        System.out.println(listLeft);
        System.out.println(listRight);
        var nL = listLeft.size();
        var nR = listRight.size();
        System.out.println("max = " + sortedList.get(n - 1));
        System.out.println("min = " + sortedList.get(0));
        var Q2 = medium(sortedList, true);
        var Q1 = medium(listLeft, true);
        var Q3 = medium(listRight, true);
        if (nL % 2 == 0) {
            System.out.printf("Q1 = (%.1f + %.1f)/2 = %.2f%n", listLeft.get(nL / 2 - 1), listLeft.get(nL / 2), Q1);
        } else {
            System.out.printf("Q1 = %.1f%n", Q1);
        }
        System.out.printf("Q2 = %.2f%n", Q2);
        if (nR % 2 == 0) {
            System.out.printf("Q3 = (%.1f + %.1f)/2 = %.2f%n", listRight.get(nR / 2 - 1), listRight.get(nR / 2), Q3);
        } else {
            System.out.printf("Q3 = %.1f%n", Q3);
        }
        System.out.printf("IQR = Q3 - Q1 = %.2f - %.2f = %.2f%n", Q3, Q1, Q3 - Q1);
    }
}
