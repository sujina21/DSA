//Question 6

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.*;

public class ImageDownloaderApp extends JFrame {
    private JTextField urlTextField;
    private JButton downloadButton;
    private JTextArea logTextArea;
    private ExecutorService executorService;

    public ImageDownloaderApp() {
        super("Image Downloader");
        initComponents();
        executorService = Executors.newFixedThreadPool(5); // Use a fixed-size thread pool
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        urlTextField = new JTextField();
        downloadButton = new JButton("Download");
        logTextArea = new JTextArea();

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = urlTextField.getText();
                downloadImages(url);
            }
        });

        add(urlTextField, BorderLayout.NORTH);
        add(downloadButton, BorderLayout.CENTER);
        add(new JScrollPane(logTextArea), BorderLayout.SOUTH);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void downloadImages(String url) {
        log("Downloading images from: " + url);
        try {
            List<Future<Void>> futures = new CopyOnWriteArrayList<>();

            URL imageUrl = new URL(url);
            // Retrieve image URLs from the given URL (you might need to implement this method)
            List<URL> imageUrls = retrieveImageUrls(imageUrl);

            for (URL imageUrlToDownload : imageUrls) {
                Future<Void> future = executorService.submit(() -> {
                    downloadImage(imageUrlToDownload);
                    return null;
                });
                futures.add(future);
            }

            // Wait for all download tasks to complete
            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException ex) {
                    log("Error: " + ex.getMessage());
                }
            }

            log("All downloads completed.");
        } catch (IOException e) {
            log("Error: " + e.getMessage());
        }
    }

    private void downloadImage(URL imageUrl) {
        try {
            // Simulate image download (replace with actual download logic)
            Thread.sleep(2000);
            log("Downloaded image: " + imageUrl);
        } catch (InterruptedException e) {
            log("Download interrupted: " + imageUrl);
        }
    }

    private List<URL> retrieveImageUrls(URL url) throws IOException {
        // Implement logic to retrieve image URLs from the given URL
        // This can involve parsing HTML, looking for image tags, etc.
        // For simplicity, we'll just return a hardcoded list of URLs.
        return List.of(
                new URL("https://img.freepik.com/premium-photo/mountain-landscape-with-sunset-background_726745-519.jpg?w=360"),
                new URL("https://png.pngtree.com/background/20230411/original/pngtree-natural-landscape-snow-mountain-background-picture-image_2390197.jpg"),
                new URL("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.freepik.com%2Ffree-photos-vectors%2Fbackground&psig=AOvVaw1iScvNpc3F725iDRR0sN3y&ust=1708513461237000&source=images&cd=vfe&opi=89978449&ved=0CBMQjRxqFwoTCIDto-DiuYQDFQAAAAAdAAAAABAJ")
        );
    }

    private void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logTextArea.append(message + "\n");
            logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageDownloaderApp());
    }
}
