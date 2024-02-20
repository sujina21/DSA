// Question 2 (a) - Solution for finding the minimum moves to equalize dresses

public class MinMovesToEqualizeDresses {

    // Method to calculate the minimum moves needed to equalize dresses
    public static int minMovesToEqualize(int[] dresses) {
        int n = dresses.length; // Number of dresses

        // Calculate the prefix sums
        int[] prefixSums = new int[n + 1]; // Array to store prefix sums
        for (int i = 1; i <= n; i++) {
            prefixSums[i] = prefixSums[i - 1] + dresses[i - 1]; // Calculate prefix sum up to index i
        }

        // Calculate the total number of dresses
        int totalDresses = prefixSums[n]; // Total dresses is the sum of all dresses

        // Initialize the minimum moves needed
        int minMoves = Integer.MAX_VALUE; // Set initial minimum moves to maximum value

        // Iterate through each position and calculate moves required
        for (int i = 0; i < n; i++) {
            int leftDresses = prefixSums[i]; // Number of dresses on the left side
            int rightDresses = totalDresses - prefixSums[i] - dresses[i]; // Number of dresses on the right side
            int moves = Math.abs(leftDresses - rightDresses); // Calculate the moves required to equalize dresses at this position
            minMoves = Math.min(minMoves, moves); // Update the minimum moves needed
        }

        // If it's not possible to equalize the dresses, return -1
        return (minMoves == Integer.MAX_VALUE) ? -1 : minMoves; // Return the minimum moves required
    }

    // Main method
    public static void main(String[] args) {
        int[] dresses = {2, 1, 3, 0, 2}; // Array representing the number of dresses at each position
        int result = minMovesToEqualize(dresses); // Calculate the minimum moves to equalize dresses
        System.out.println("Minimum moves required: " + result); // Print the result
    }
}
