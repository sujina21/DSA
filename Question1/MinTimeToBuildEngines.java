import java.util.Arrays;

public class MinTimeToBuildEngines {
    public static int engineTime(int[] engine, int splitCost) {
        int Engine = engine.length; // Number of engines
        int[] dp = new int[Engine + 1]; // Array to store the minimum time needed to build 'i' engines

        Arrays.fill(dp, Integer.MAX_VALUE); // Fill the array with a very large value indicating an unset state
        dp[0] = 0; // Setting the time required to build the first engine to zero

        for (int i = 1; i <= Engine; i++) { // Iterate through each engine
            dp[i] = engine[i - 1] + splitCost; // Time required to build one engine plus the split cost
            for (int j = 1; j < i; j++) {  // Iterate through each possible splitting point
                dp[i] = Math.min(dp[i], dp[j] + dp[i - j]); // Update the minimum time by choosing the minimum split 
            }
        }
        return dp[Engine]; // Return the minimum time required to build all engines
    }

    public static void main(String[] args) {
        int[] engine = {1, 2, 3}; // Array representing the time required to build each engine
        int splitCost = 1; // Cost incurred each time an engine is split

        int minTime = engineTime(engine, splitCost);
        System.out.println("Minimum time to build all engines: " + minTime);
    }
}
