package io.github.censodev.utc.dataminingtest;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class UI {
    public static void main(String[] args) throws IOException {
        var scanner = new Scanner(System.in);
        System.out.print("Enter dataset location: ");
        var location = scanner.nextLine();
        var path = Paths.get(location);
        var app = new App(path);

        while (true) {
            printMenu();
            switch (scanner.nextLine()) {
                case "1":
                    app.runCalcMeanMediumModeJob();
                    break;
                case "2":
                    app.runDrawBoxplotJob();
                    break;
                case "3":
                    System.out.print("Enter min: ");
                    var min = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter max: ");
                    var max = Double.parseDouble(scanner.nextLine());
                    app.runNormalizeMinMaxJob(min, max);
                    break;
                case "4":
                    app.runNormalizeZScoreJob();
                    break;
                case "5":
                    app.runNormalizeZScoreMeanAbsDeviationJob();
                    break;
                case "6":
                    app.runNormalizeDecimalScalingJob();
                    break;
                case "7":
                    app.runCalcCorrelationCoefficientJob();
                    break;
                case "8":
                    System.out.print("Enter number of bins: ");
                    app.runSmoothDataByEqualWidthBinningJob(Integer.parseInt(scanner.nextLine()));
                    break;
                case "9":
                    System.out.print("Enter number of bins: ");
                    app.runSmoothDataByEqualFrequencyBinningJob(Integer.parseInt(scanner.nextLine()));
                    break;
                default:
                    scanner.close();
                    return;
            }
        }
    }

    static void printMenu() {
        System.out.println("=============================================");
        System.out.println("1. Mean, medium, mode");
        System.out.println("2. Boxplot");
        System.out.println("3. Normalize min-max");
        System.out.println("4. Normalize z-score");
        System.out.println("5. Normalize z-score by mean absolute deviant");
        System.out.println("6. Normalize decimal scaling");
        System.out.println("7. Correlation coefficient");
        System.out.println("8. Smooth data by equal-width binning");
        System.out.println("9. Smooth data by equal-frequency binning");
        System.out.println("=============================================");
        System.out.print("> Choose an option: ");
    }
}
