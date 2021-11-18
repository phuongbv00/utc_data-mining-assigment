package io.github.censodev.utc.dataminingtest;

import java.util.List;
import java.util.stream.Collectors;

public interface IApp {
    void runCalcMeanMediumModeJob();

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
}
