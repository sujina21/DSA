// Importing necessary Java utilities
import java.util.*;

// Defining a class to represent an edge in a graph
class Edge implements Comparable<Edge> {
    int src, dest, weight;

    // Constructor to initialize edge properties
    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    // Implementing compareTo method to compare edges based on weights
    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }
}

// Main class for Kruskal's Minimum Spanning Tree algorithm
public class KruskalMST3b {
    private int V; // Number of vertices in the graph
    private List<Edge> edges; // List to store edges of the graph

    // Constructor to initialize the number of vertices and edges list
    public KruskalMST3b(int vertices) {
        V = vertices;
        edges = new ArrayList<>();
    }

    // Method to add an edge to the graph
    public void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }

    // Method to find the minimum spanning tree using Kruskal's algorithm
    public List<Edge> kruskalMST() {
        List<Edge> result = new ArrayList<>(); // List to store the minimum spanning tree edges
        Collections.sort(edges); // Sorting the edges based on weights

        int[] parent = new int[V]; // Array to store parent vertices
        Arrays.fill(parent, -1); // Initializing parent array with -1

        // Iterating through sorted edges
        for (Edge edge : edges) {
            int srcParent = find(parent, edge.src); // Finding parent of source vertex
            int destParent = find(parent, edge.dest); // Finding parent of destination vertex

            // If including this edge does not create a cycle, add it to the result
            if (srcParent != destParent) {
                result.add(edge);
                union(parent, srcParent, destParent); // Union the sets of source and destination vertices
            }
        }

        return result; // Return the minimum spanning tree edges
    }

    // Method to find the parent vertex of a given vertex
    private int find(int[] parent, int vertex) {
        if (parent[vertex] == -1) // If the vertex has no parent
            return vertex; // Return the vertex itself
        return find(parent, parent[vertex]); // Recursively find the parent vertex
    }

    // Method to perform union of two sets
    private void union(int[] parent, int x, int y) {
        int xRoot = find(parent, x); // Find the root parent of x
        int yRoot = find(parent, y); // Find the root parent of y
        parent[xRoot] = yRoot; // Set the root parent of x as the parent of y
    }

    // Main method to test the implementation
    public static void main(String[] args) {
        KruskalMST3b graph = new KruskalMST3b(4); // Creating a graph with 4 vertices

        // Adding edges to the graph
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 4);

        List<Edge> result = graph.kruskalMST(); // Finding the minimum spanning tree

        // Printing the edges in the minimum spanning tree
        System.out.println("Edges in the minimum spanning tree:");
        for (Edge edge : result) {
            System.out.println(edge.src + " - " + edge.dest + " : " + edge.weight);
        }
    }
}
