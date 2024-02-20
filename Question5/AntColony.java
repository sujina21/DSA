import java.util.Arrays;
import java.util.Random;

public class AntColony {
    // Number of ants in the colony
    private final int numOfAnts;
    // Number of cities in the problem
    private final int numOfCities;
    // Matrix to hold pheromone levels on paths between cities
    private final double[][] pheromones;
    // Matrix to hold distances between cities
    private final double[][] distances;
    // Parameters for controlling the ant behavior
    private final double alpha; // Influence of pheromones
    private final double beta; // Influence of distance
    private final double evaporationRate; // Rate at which pheromones evaporate

    // Constructor to initialize the AntColony
    public AntColony(int numOfAnts, double alpha, double beta, double evaporationRate, double[][] distances) {
        this.numOfAnts = numOfAnts;
        this.numOfCities = distances.length;
        this.pheromones = new double[numOfCities][numOfCities];
        this.distances = distances;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;

        // Initialize pheromones on all paths to 1.0
        for (int i = 0; i < numOfCities; i++) {
            Arrays.fill(pheromones[i], 1.0);
        }
    }

    // Method to solve the TSP using ant colony optimization
    public int[] solve() {
        // Initialize variables to store the best tour and its length
        int[] bestTour = null;
        double bestTourLength = Double.POSITIVE_INFINITY;

        // Run the ant colony optimization for a fixed number of iterations
        for (int iteration = 0; iteration < 100; iteration++) {
            // Iterate over each ant in the colony
            for (int ant = 0; ant < numOfAnts; ant++) {
                // Construct a tour for the current ant
                int[] tour = AntTour();
                // Calculate the length of the tour
                double tourLength = calculateTourLength(tour);

                // Update the best tour if the current tour is better
                if (tourLength < bestTourLength) {
                    bestTourLength = tourLength;
                    bestTour = tour.clone();
                }

                // Update the pheromones based on the current tour
                updatePheromones(tour, tourLength);
            }

            // Evaporate pheromones on all paths
            evaporatePheromones();
        }

        // Return the best tour found
        return bestTour;
    }

    // Method to construct a tour for a single ant
    private int[] AntTour() {
        int[] tour = new int[numOfCities];
        boolean[] visited = new boolean[numOfCities];
        Random random = new Random();

        // Randomly choose the starting city
        int currentCity = random.nextInt(numOfCities);
        tour[0] = currentCity;
        visited[currentCity] = true;

        // Construct the tour by selecting the next city at each step
        for (int i = 1; i < numOfCities; i++) {
            int nextCity = NextCity(currentCity, visited, random);
            tour[i] = nextCity;
            visited[nextCity] = true;
            currentCity = nextCity;
        }

        return tour;
    }

    // Method to select the next city for an ant based on pheromone levels and distances
    private int NextCity(int currentCity, boolean[] visited, Random random) {
        double[] probabilities = calculateProbabilities(currentCity, visited);

        double r = random.nextDouble();
        double cumulativeProbability = 0;

        // Choose the next city based on the calculated probabilities
        for (int i = 0; i < numOfCities; i++) {
            if (!visited[i]) {
                cumulativeProbability += probabilities[i];
                if (r <= cumulativeProbability) {
                    return i;
                }
            }
        }

        // Should not reach here, but just in case
        return -1;
    }

    // Method to calculate the probabilities of selecting each unvisited city as the next city
    private double[] calculateProbabilities(int currentCity, boolean[] visited) {
        double[] probabilities = new double[numOfCities];
        double total = 0;

        // Calculate probabilities based on pheromone levels and distances
        for (int i = 0; i < numOfCities; i++) {
            if (!visited[i]) {
                probabilities[i] = Math.pow(pheromones[currentCity][i], alpha) * Math.pow(1.0 / distances[currentCity][i], beta);
                total += probabilities[i];
            }
        }

        // Normalize probabilities
        for (int i = 0; i < numOfCities; i++) {
            probabilities[i] /= total;
        }

        return probabilities;
    }

    // Method to calculate the total length of a tour
    private double calculateTourLength(int[] tour) {
        double length = 0;

        // Calculate the length by summing up the distances between consecutive cities
        for (int i = 0; i < numOfCities - 1; i++) {
            length += distances[tour[i]][tour[i + 1]];
        }

        // Add the distance from the last city back to the starting city
        length += distances[tour[numOfCities - 1]][tour[0]];
        return length;
    }

    // Method to update the pheromone levels based on the length of a tour
    private void updatePheromones(int[] tour, double tourLength) {
        double pheromoneDelta = 1.0 / tourLength;

        // Update pheromone levels on all edges in the tour
        for (int i = 0; i < numOfCities - 1; i++) {
            int city1 = tour[i];
            int city2 = tour[i + 1];
            pheromones[city1][city2] += pheromoneDelta;
            pheromones[city2][city1] += pheromoneDelta;
        }

        // Update pheromone levels for the edge back to the starting city
        int lastCity = tour[numOfCities - 1];
        int firstCity = tour[0];
        pheromones[lastCity][firstCity] += pheromoneDelta;
        pheromones[firstCity][lastCity] += pheromoneDelta;
    }

    // Method to evaporate pheromones on all paths
    private void evaporatePheromones() {
        for (int i = 0; i < numOfCities; i++) {
            for (int j = 0; j < numOfCities; j++) {
                pheromones[i][j] *= (1.0 - evaporationRate);
            }
        }
    }

    // Main method for testing the AntColony class
    public static void main(String[] args) {
        // Example usage
        double[][] distances = {
                {0, 2, 9, 10},
                {2, 0, 6, 4},
                {9, 6, 0, 8},
                {10, 4, 8, 0}
        };

        int numOfAnts = 5;
        double alpha = 1.0;
        double beta = 2.0;
        double evaporationRate = 0.5;

        // Create an instance of AntColony
        AntColony antColony = new AntColony(numOfAnts, alpha, beta, evaporationRate, distances);
        // Solve the TSP using ant colony optimization
        int[] bestTour = antColony.solve();

        // Print the best tour found
        System.out.println("Best Tour: " + Arrays.toString(bestTour));
    }
}
