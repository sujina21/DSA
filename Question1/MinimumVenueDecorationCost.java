public class MinimumVenueDecorationCost {
    public static int minimumCostToDecorate(int[][] minCost) {
        
        if (minCost == null || minCost[0].length == 0) // Check if the input array is null or empty
            return 0;

       
        int k = minCost[0].length; // Number of themes
        int n = minCost.length; // Number of venues

        int[][] dp = new int[n][k]; // Initializing array to store the minimum cost for each venue and theme     
        for (int i = 0; i < n; i++) { // Loop over venues
            for (int j = 0; j < k; j++) { // Loop over themes
                dp[i][j] = minCost[i][j]; // Initialize cost to decorate current venue with current theme

                
                if (i > 0) { // If not the first venue
                    int prevMin = Integer.MAX_VALUE; // Initialize with maximum value
                    for (int x = 0; x < k; x++) { // Iterate over themes of the previous venue
                        if (x != j) { // Exclude the current theme
                            prevMin = Math.min(prevMin, dp[i - 1][x]); // Update the minimum cost
                        }
                    }
                    dp[i][j] += prevMin; // Update the current cost by adding the minimum cost of the previous venue with a different theme
                }
            }
        }

        int minminCost = Integer.MAX_VALUE; // Initialize the minimum cost
        for (int j = 0; j < k; j++) { // Iterate over themes of the last venue
            minminCost = Math.min(minminCost, dp[n - 1][j]); // Find the minimum cost among all themes
        }

        return minminCost; // Return the minimum cost of decorating all venues
    }

    public static void main(String[] args) {
        int[][] minCost = {{2, 3, 1}, {4, 6, 8}, {3, 2, 5}};
        System.out.println(minimumCostToDecorate(minCost)); 
    }
}
