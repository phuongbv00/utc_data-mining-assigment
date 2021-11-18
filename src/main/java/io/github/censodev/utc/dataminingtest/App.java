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
    public void runNormalizeMinMaxJob() {

    }

    @Override
    public void runNormalizeZScoreJob() {
    }
}
