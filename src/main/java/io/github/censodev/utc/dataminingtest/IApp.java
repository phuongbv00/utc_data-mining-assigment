package io.github.censodev.utc.dataminingtest;

public interface IApp {
    void runCalcMeanMediumModeJob();

    void runDrawBoxplotJob();

    void runNormalizeMinMaxJob(double min, double max);

    void runNormalizeZScoreJob();

    void runNormalizeZScoreMeanAbsDeviationJob();

    void runNormalizeDecimalScalingJob();

    void runCalcCorrelationCoefficientJob();

    void runSmoothDataByEqualWidthBinningJob(int bins);
}
