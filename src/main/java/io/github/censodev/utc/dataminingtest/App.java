package io.github.censodev.utc.dataminingtest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.censodev.utc.dataminingtest.StatisticUtil.*;

public class App implements IApp {
    private final List<Double> X;
    private final List<Double> Y;

    public App(Path path) throws IOException {
        var lines = Files.lines(path);
        List<String> data = lines
                .map(s -> s.replace(" ", ""))
                .collect(Collectors.toList());
        lines.close();
        X = Arrays.stream(data.get(0).split(","))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
        Y = Arrays.stream(data.get(1).split(","))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }

    @Override
    public void runCalcMeanMediumModeJob() {
        System.out.println("Mean, medium, mode of X:");
        new M3(X).print();
        System.out.println();
        System.out.println("Mean, medium, mode of Y:");
        new M3(Y).print();
    }

    @Override
    public void runDrawBoxplotJob() {
        System.out.println("Boxplot X:");
        new Boxplot(X).draw();
        System.out.println();
        System.out.println("Boxplot Y:");
        new Boxplot(Y).draw();
    }

    @Override
    public void runNormalizeMinMaxJob(double min, double max) {
        System.out.println(getMinMaxNormalizationFormula());
        System.out.println();
        System.out.println("Normalize min-max for X:");
        System.out.println(X);
        System.out.println(normalizeMinMax(X, 0, 1));
        System.out.println();
        System.out.println("Normalize min-max for Y:");
        System.out.println(Y);
        System.out.println(normalizeMinMax(Y, 0, 1));
    }

    @Override
    public void runNormalizeZScoreJob() {
        System.out.println(getZScoreNormalizationFormula());
        System.out.println(getSigmaFormula());
        System.out.println();
        System.out.println("Normalize z-score for X:");
        System.out.println("σX = " + calcSigma(X));
        System.out.println(X);
        System.out.println(normalizeZScore(X));
        System.out.println();
        System.out.println("Normalize z-score for Y:");
        System.out.println("σY = " + calcSigma(Y));
        System.out.println(Y);
        System.out.println(normalizeZScore(Y));
    }

    @Override
    public void runNormalizeZScoreMeanAbsDeviationJob() {
        System.out.println(getZScoreMeanAbsDeviationNormalizationFormula());
        System.out.println(getMeanAbsDeviationFormula());
        System.out.println();
        System.out.println("Normalize z-score mean abs deviation for X:");
        System.out.println("sX = " + calcMeanAbsDeviation(X));
        System.out.println(X);
        System.out.println(normalizeZScoreMeanAbsDeviation(X));
        System.out.println();
        System.out.println("Normalize z-score mean abs deviation for Y:");
        System.out.println("sY = " + calcMeanAbsDeviation(Y));
        System.out.println(Y);
        System.out.println(normalizeZScoreMeanAbsDeviation(Y));
    }

    @Override
    public void runNormalizeDecimalScalingJob() {
        System.out.println(getDecimalScalingNormalizationFormula());
        System.out.println();
        System.out.println("Normalize decimal scaling for X:");
        System.out.println(X);
        System.out.println(normalizeDecimalScaling(X));
        System.out.println();
        System.out.println("Normalize decimal scaling for Y:");
        System.out.println(Y);
        System.out.println(normalizeDecimalScaling(Y));
    }

    @Override
    public void runCalcCorrelationCoefficientJob() {
        System.out.println("Correlation coefficient of X and Y:");
        System.out.println(getCorrelationCoefficientFormula());
        System.out.println();
        System.out.println("meanX = " + mean(X));
        System.out.println("meanY = " + mean(Y));
        System.out.println("σX = " + calcSigma(X));
        System.out.println("σy = " + calcSigma(Y));
        System.out.println("rxy = " + calcCorrelationCoefficient(X, Y));
    }

    @Override
    public void runPartitionByEqualWidthJob(int bins) {
        System.out.println("Partition X by equal-width:");
        new EqualWidthPartition(bins, X).print();
        System.out.println();
        System.out.println("Partition Y by equal-width:");
        new EqualWidthPartition(bins, Y).print();
    }
}
