

// Question 7


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class User {
    String username;
    List<User> followers;
    List<String> contentInteractions;

    public User(String username) {
        this.username = username;
        this.followers = new ArrayList<>();
        this.contentInteractions = new ArrayList<>();
    }
}

public class SocialMediaApp extends JFrame {
    private Map<String, User> users;
    private JTextArea recommendationArea;

    public SocialMediaApp() {
        super("Social Media Recommendation System");
        initComponents();
        users = new HashMap<>();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        recommendationArea = new JTextArea();
        recommendationArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(recommendationArea);

        JButton createUserButton = new JButton("Create User");
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUser();
            }
        });

        JButton followUserButton = new JButton("Follow User");
        followUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                followUser();
            }
        });

        JButton interactWithContentButton = new JButton("Interact with Content");
        interactWithContentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interactWithContent();
            }
        });

        JButton generateRecommendationsButton = new JButton("Generate Recommendations");
        generateRecommendationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRecommendations();
            }
        });

        add(createUserButton);
        add(followUserButton);
        add(interactWithContentButton);
        add(generateRecommendationsButton);
        add(scrollPane);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createUser() {
        String username = JOptionPane.showInputDialog("Enter username:");
        if (username != null && !username.isEmpty()) {
            users.put(username, new User(username));
            JOptionPane.showMessageDialog(this, "User created successfully!");
        }
    }

    private void followUser() {
        String followerUsername = JOptionPane.showInputDialog("Enter your username:");
        String followingUsername = JOptionPane.showInputDialog("Enter the username you want to follow:");

        if (followerUsername != null && followingUsername != null) {
            User follower = users.get(followerUsername);
            User following = users.get(followingUsername);

            if (follower != null && following != null) {
                follower.followers.add(following);
                JOptionPane.showMessageDialog(this, "You are now following " + followingUsername + "!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid usernames!");
            }
        }
    }

    private void interactWithContent() {
        String username = JOptionPane.showInputDialog("Enter your username:");
        String content = JOptionPane.showInputDialog("Enter the content you want to interact with:");

        if (username != null && content != null) {
            User user = users.get(username);

            if (user != null) {
                user.contentInteractions.add(content);
                JOptionPane.showMessageDialog(this, "You interacted with the content!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username!");
            }
        }
    }

    private void generateRecommendations() {
        String username = JOptionPane.showInputDialog("Enter your username:");
        User user = users.get(username);

        if (user != null) {
            List<String> recommendations = generateRecommendationsForUser(user);
            recommendationArea.setText(String.join("\n", recommendations));
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username!");
        }
    }

    private List<String> generateRecommendationsForUser(User user) {
        // Simplified recommendation algorithm
        List<String> recommendations = new ArrayList<>();
        for (User follower : user.followers) {
            for (String content : follower.contentInteractions) {
                if (!user.contentInteractions.contains(content)) {
                    recommendations.add(follower.username + " recommends: " + content);
                }
            }
        }
        return recommendations;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SocialMediaApp());
    }
}

