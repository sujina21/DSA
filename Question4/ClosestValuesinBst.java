package Question4; 

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Class representing a node in a binary tree
class node {
    int val;
    node left, right;

    // Constructor to initialize node value and children
    public node(int val) {
        this.val = val;
        this.left = this.right = null;
    }
}

// Class to find x closest values to a target in a binary search tree
public class ClosestValuesinBst {
    // Method to find x closest values to target in BST
    public static List<Integer> closeValue(node root, double target, int x) {
        List<Integer> result = new ArrayList<>(); // List to store closest values
        Stack<Integer> front = new Stack<>(); // Stack for values on the left of target
        Stack<Integer> back = new Stack<>(); // Stack for values on the right of target

        // Initialize both stacks during inorder traversal
        inorderTraversal(root, target, false, front);
        inorderTraversal(root, target, true, back);

        // Merge the stacks to find the x closest values
        while (x-- > 0) {
            if (front.isEmpty()) {
                result.add(back.pop());
            } else if (back.isEmpty()) {
                result.add(front.pop());
            } else if (Math.abs(front.peek() - target) < Math.abs(back.peek() - target)) {
                result.add(front.pop());
            } else {
                result.add(back.pop());
            }
        }

        return result;
    }

    // Helper method for inorder traversal with a stack
    private static void inorderTraversal(node root, double target, boolean reverse, Stack<Integer> stack) {
        if (root == null) {
            return;
        }

        Stack<node> nodeStack = new Stack<>(); // Stack to store nodes during traversal
        node current = root; // Current node pointer initialized to root

        // Inorder traversal loop
        while (current != null || !nodeStack.isEmpty()) {
            // Traverse left subtree and push nodes onto the stack
            while (current != null) {
                nodeStack.push(current);
                current = (reverse) ? current.right : current.left; // Update the current node based on traversal direction
            }

            // Pop the node from the stack
            current = nodeStack.pop();

            // Break conditions based on traversal direction and target value
            if (!reverse && current.val > target) {
                break;
            }
            if (reverse && current.val <= target) {
                break;
            }

            stack.push(current.val); // Push the current node value onto the stack

            current = (reverse) ? current.left : current.right; // Update the current node based on traversal direction
        }
    }

    // Main method to test the implementation
    public static void main(String[] args) {
        // Example usage
        node root = new node(4);
        root.left = new node(2);
        root.right = new node(5);
        root.left.left = new node(1);
        root.left.right = new node(3);

        double target = 3.8;
        int x = 2;

        List<Integer> cValue = closeValue(root, target, x); // Call the closest value method to find the x closest values to the target in the binary search tree
        System.out.println(cValue); // Print the result
    }
}
