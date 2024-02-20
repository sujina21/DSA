import java.util.ArrayList;
import java.util.List;

public class SecretSharing {
    
    // Method to determine the people known based on intervals and the first person
    public static List<Integer> peopleKnown(int n, int[][] intervals, int firstOne) { 
        boolean[] known = new boolean[n]; // Array to track which people are known
        known[firstOne] = true; // Mark the first person as known

        // Iterate through intervals
        for (int[] interval : intervals) { 
            for (int i = interval[0]; i <= interval[1]; i++) {
                if (known[i]) { // If person 'i' is known
                   
                    // Mark all people within the interval as known
                    for (int j = interval[0]; j <= interval[1]; j++) {
                        known[j] = true;
                    }
                    break; // Exit the loop once a known person is found in the interval
                }
            }
        }

        // Create a list to store the indices of known people
        List<Integer> answer = new ArrayList<>(); 
        for (int i = 0; i < n; i++) { 
            if (known[i]) { // If person 'i' is known
                answer.add(i); // Add the index to the list
            }
        }

        return answer; // Return the list of known people
    }

    // Main method
    public static void main(String[] args) {
        int n = 5; // Total number of people
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}}; // Intervals of people knowing each other
        int firstOne = 0; // Index of the first person

        // Determine the known people based on intervals and the first person
        List<Integer> answer = peopleKnown(n, intervals, firstOne);
        System.out.println(answer); // Print the list of known people
    }
}
