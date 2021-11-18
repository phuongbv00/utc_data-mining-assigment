package io.github.censodev.utc.dataminingtest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.censodev.utc.dataminingtest.StatisticUtil.*;

public class App implements IApp {
    private final int n;
    private final List<Double> X;
    private final List<Double> Y;
    private final List<Double> sortedX;
    private final List<Double> sortedY;

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
        sortedX = sort(X);
        sortedY = sort(Y);
        n = X.size();
    }

    @Override
    public void runCalcMeanMediumModeJob() {
        System.out.printf("Xmean = (%.1f + ... + %.1f)/%d = %.2f%n", X.get(0), X.get(n - 1), n, mean(X));
        System.out.printf("Ymean = (%.1f + ... + %.1f)/%d = %.2f%n", Y.get(0), Y.get(n - 1), n, mean(Y));
        System.out.println("Sắp xếp tập dữ liệu:");
        System.out.println(sortedX);
        System.out.println(sortedY);
        if (n % 2 == 0) {
            System.out.printf("Xmedium = (%.1f + %.1f)/2 = %.2f%n", sortedX.get(n / 2 - 1), sortedX.get(n / 2), medium(sortedX, true));
            System.out.printf("Ymedium = (%.1f + %.1f)/2 = %.2f%n", sortedY.get(n / 2 - 1), sortedY.get(n / 2), medium(sortedY, true));
        } else {
            System.out.printf("Xmedium = %.1f%n", medium(sortedX, true));
            System.out.printf("Ymedium = %.1f%n", medium(sortedY, true));
        }
        System.out.printf("Xmode = %.1f%n", mode(X));
        System.out.printf("Ymode = %.1f%n", mode(Y));
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
    public void runNormalizeMinMaxJob() {

    }

    @Override
    public void runNormalizeZScoreJob() {
    }
}
