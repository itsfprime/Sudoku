package Java;

public class TestBoard {
    public static void main(String[] args) {
        Board board = new Board();
        board.generateBoard();
        testCreationTime(board, 350);
    }

    private static void testCreationTime(Board board, int n){
        long[] times = new long[n];

        // Run test
        long testStartTime = System.currentTimeMillis();
        for(int i = 0; i < n; i++){
            long execStartTime = System.currentTimeMillis();
            board.generatePuzzle(SudokuCommons.clueCount);
            long execEndTime = System.currentTimeMillis();
            System.out.printf("Exec %d: %dms\n", i+1, (execEndTime - execStartTime));
            times[i] = execStartTime - execEndTime;
        }

        // Calculate stats
        long executionTime = System.currentTimeMillis() - testStartTime;
        double meanTime = executionTime / (double)n;
        double standardDeviation = getStandardDeviationCreationTest(times);
        double[] confidenceInterval = getConfidentIntervalCreationTest(n, meanTime, standardDeviation);

        // Print results
        System.out.println("Total execution time: " + executionTime / 1000.0  + "s");
        System.out.printf("Standard deviation: %.2f\n", standardDeviation);
        System.out.printf("Average execution time: %.2fms [%.2fms - %.2fms]\n", executionTime / (double)n, confidenceInterval[0], confidenceInterval[1]);
    }

    private static double getStandardDeviationCreationTest(long[] times){
        double timesMean = 0;
        for (long time : times) timesMean += time;
        timesMean /= times.length;
        double deviationMean = 0;
        for (long time : times) deviationMean += Math.pow(time - timesMean, 2);
        deviationMean /= times.length;
        deviationMean = Math.sqrt(deviationMean);
        return deviationMean;
    }

    // 95% Confidence Interval
    private static double[] getConfidentIntervalCreationTest(int n, double mean, double standardDeviation){
        double z = 1.96;
        double variance = z * (standardDeviation / Math.sqrt(n));
        double lowEnd = mean - variance;
        double highEnd = mean + variance;
        return new double[]{lowEnd, highEnd};
    }
}
