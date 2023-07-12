package launcher;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import marytts.exceptions.MaryConfigurationException;
import util.TextToSpeech;

public class Launcher extends JFrame {

    private static final long serialVersionUID = 1L;
    private JProgressBar progressBar;
    private JProgressBar oProgressBar;
    private String version = "";
    private boolean needDownload;
    private int i = 0;
    private long lastTime;
    private static final UpdateLogger updlog = new UpdateLogger();

    public static void main(String[] args) throws MaryConfigurationException {
        try {
            new Launcher();
        } catch (final MaryConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Launcher() throws MaryConfigurationException {
        initializeUI();
        needDownload();
        performDownloads();
        launchGame();
    }

    private void initializeUI() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screenSize.getWidth();
        final int height = (int) screenSize.getHeight();
        setTitle("Updating game...");
        setBounds(width / 2 - 200, height / 2 - 50, 400, 100);
        setVisible(true);
        setProgressBar(new JProgressBar(0));
        oProgressBar = new JProgressBar(0);
        getProgressBar().setBounds(0, 0, 400, 50);
        oProgressBar.setBounds(0, 50, 400, 50);
        add(getProgressBar());
        add(oProgressBar);
        getProgressBar().setValue(0);
        oProgressBar.setValue(0);
        TextToSpeech.playVoice("Updating...");
    }

    public void needDownload() {
        try {
            final Path versionFile = Paths.get(System.getProperty("user.dir"), "version.txt");
            final List<String> lines = Files.readAllLines(versionFile);
            for (final String line : lines) {
                version = line;
                System.out.println("Version: " + line);
                getUpdlog().getLogger().info("Version: " + line);
            }

            final URL url = new URL("https://github.com/Hunterszone/MyJavaGames/tree/master/EvilGalaxy");
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            boolean needsUpdate = false;

            while ((inputLine = reader.readLine()) != null) {
                if (!inputLine.contains(version)) {
                    needsUpdate = true;
                    break;
                }
            }

            if (needsUpdate) {
                needDownload = true;
                System.out.println("Downloading!");
                Files.write(versionFile, inputLine.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
                System.out.println("Updated to version " + inputLine + " from version " + version);
            } else {
                System.out.println("No download needed!");
                Files.write(versionFile, "up-to-date".getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            }

            reader.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void performDownloads() {
        try {
            final ExecutorService executor = Executors.newFixedThreadPool(10);
            final List<String> downloadImg = readTextFile("images.txt");
            final List<String> downloadSounds = readTextFile("sounds.txt");

            downloadFiles(downloadImg, "images/");
            downloadFiles(downloadSounds, "sounds/");

            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadFiles(List<String> fileList, String destinationDir) throws IOException {
        final int totalFiles = fileList.size();
        final int batchSize = Math.min(totalFiles, 10);
        final ExecutorService executor = Executors.newFixedThreadPool(batchSize);

        for (final String file : fileList) {
            executor.execute(() -> {
                try {
                    final URL url = new URL("https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/" + destinationDir + file);
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("HEAD");

                    final Path filePath = Paths.get(destinationDir, file);
                    final Path parentDir = filePath.getParent();
                    if (parentDir != null && !Files.exists(parentDir))
                        Files.createDirectories(parentDir);

                    final long localFileSize = Files.exists(filePath) ? Files.size(filePath) : 0;
                    if (localFileSize != conn.getContentLength()) {
                        download(url.toString(), filePath.toString(), conn.getContentLength());
                    } else {
                        System.out.println("No need to download " + destinationDir + file);
                        getUpdlog().getLogger().info("No need to download " + destinationDir + file);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private void download(String source, String destination, int size) throws IOException {
        final Path filePath = Paths.get(System.getProperty("user.dir"), destination);
        final List<Thread> threads = new ArrayList<>();
        System.out.println("\nDownloading from\n\t " + source + "\nTo\n\t " + filePath + "\n");
        getUpdlog().getLogger().logp(Level.INFO, "Downloading from\n\t " + source + "\nTo\n\t " + filePath + "\n", "", "");

        final URL url = new URL(source);
        try (InputStream input = url.openStream();
             PrintWriter writer = new PrintWriter(filePath.toFile())) {

            final byte[] buffer = new byte[16 * 1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                writer.write(new String(buffer, 0, bytesRead));
            }

            i++;
            oProgressBar.setValue((int) ((i * 100.0f) / threads.size()));
            System.out.println("Downloaded " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void launchGame() {
        try {
            final String exec = (System.getProperty("user.dir") + java.io.File.separator + "EvilGalaxy.jar");
            final String[] command = { "java", "-jar", exec };
            final ProcessBuilder pb = new ProcessBuilder(command);
            getProgressBar().setValue(100);
            System.out.println(pb.command());
            pb.start();
            System.out.println("Running " + exec);
            dispose();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> readTextFile(String fileName) throws IOException {
        final List<String> values = new ArrayList<>();
        final Path filePath = Paths.get(fileName);
        if (Files.exists(filePath)) {
            values.addAll(Files.readAllLines(filePath));
        }
        return values;
    }

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public static UpdateLogger getUpdlog() {
		return updlog;
	}
}
