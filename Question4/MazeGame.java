package Question4; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Class representing a maze game
public class MazeGame {
    // Class representing a state in the maze
    static class State {
        int x, y; // Coordinates of the state
        String keys; // Keys collected so far

        // Constructor to initialize the state
        State(int x, int y, String keys) {
            this.x = x;
            this.y = y;
            this.keys = keys;
        }
    }

    // Method to find the shortest path in the maze
    public static int shortestPath(char[][] grid) {
        int m = grid.length; // Number of rows in the maze
        int n = grid[0].length; // Number of columns in the maze
        Set<Character> keys = new HashSet<>(); // Set to store keys present in the maze
        Map<Character, int[]> doors = new HashMap<>(); // Map to store door positions in the maze
        int start_x = -1, start_y = -1; // Coordinates of the starting point in the maze

        // Loop through the maze to find keys, doors, and the starting point
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char cell = grid[i][j];
                if (cell == 'S') {
                    start_x = i;
                    start_y = j;
                } else if ('a' <= cell && cell <= 'z') {
                    keys.add(cell);
                } else if ('A' <= cell && cell <= 'Z') {
                    doors.put(cell, new int[] { i, j });
                }
            }
        }

        List<Character> keysList = new ArrayList<>(keys); // List of keys present in the maze
        int[] minDistance = { Integer.MAX_VALUE }; // Array to store the minimum distance found
        dfs(grid, start_x, start_y, keysList, doors, new boolean[m][n], "", 0, minDistance); // Depth-first search to find the shortest path

        return minDistance[0] == Integer.MAX_VALUE ? -1 : minDistance[0]; // Return the shortest distance or -1 if no path is found
    }

    // Depth-first search to explore the maze and find the shortest path
    private static void dfs(char[][] grid, int x, int y, List<Character> keys, Map<Character, int[]> doors,
            boolean[][] visited, String collectedKeys, int distance, int[] minDistance) {
        if (distance >= minDistance[0]) // If the current distance exceeds the minimum distance found so far, stop exploration
            return;

        visited[x][y] = true; // Mark the current cell as visited

        // Explore neighboring cells
        for (int[] dir : new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }) {
            int nx = x + dir[0]; // New x-coordinate of the neighboring cell
            int ny = y + dir[1]; // New y-coordinate of the neighboring cell

            // Check if the neighboring cell is within the maze and not visited
            if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length && !visited[nx][ny]) {
                char cell = grid[nx][ny]; // Value of the neighboring cell

                if (cell == 'P' || cell == 'S') {
                    dfs(grid, nx, ny, keys, doors, visited, collectedKeys, distance + 1, minDistance); // Continue exploration
                } else if ('a' <= cell && cell <= 'z') { // If the cell contains a key
                    String newCollectedKeys = collectedKeys + cell; // Collect the key
                    if (newCollectedKeys.length() == keys.size()) { // If all keys are collected
                        minDistance[0] = Math.min(minDistance[0], distance + 1); // Update the minimum distance
                    } else {
                        dfs(grid, nx, ny, keys, doors, visited, newCollectedKeys, distance + 1, minDistance); // Continue exploration
                    }
                } else if ('A' <= cell && cell <= 'Z') { // If the cell contains a door
                    char key = Character.toLowerCase(cell); // Find the corresponding key
                    if (collectedKeys.indexOf(key) != -1) { // If the key is collected
                        dfs(grid, nx, ny, keys, doors, visited, collectedKeys, distance + 1, minDistance); // Continue exploration
                    }
                }
            }
        }

        visited[x][y] = false; // Mark the current cell as unvisited after exploration
    }

    // Main method to test the implementation
    public static void main(String[] args) {
        char[][] grid = {
            { 'S', 'P', 'q', 'P', 'P' },
            { 'W', 'W', 'W', 'P', 'W' },
            { 'r', 'P', 'Q', 'P', 'R' }
        };
        System.out.println(shortestPath(grid)); // Output: 8
    }
}
