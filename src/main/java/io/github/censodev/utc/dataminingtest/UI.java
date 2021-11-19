package io.github.censodev.utc.dataminingtest;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class UI {
    public static void main(String[] args) throws IOException {
        var scanner = new Scanner(System.in);
        System.out.println("Enter dataset location:");
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
                    app.runNormalizeMinMaxJob();
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
                    app.runCalcCorrelationCoefficient();
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
        System.out.println("=============================================");
        System.out.print("> Choose an option: ");
    }
}
