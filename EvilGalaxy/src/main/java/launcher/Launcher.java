package launcher;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import frames.ConsoleForm;
import game_engine.DrawScene;

public class Launcher extends JFrame {

	private static final long serialVersionUID = 1L;
	String version = "";
	boolean needDownload = false;
	static UpdateLogger updlog = new UpdateLogger();

	public static void main(String[] args) {
		new Launcher();
	}

	public void needDownload() {
		{
			File filename = new File(System.getProperty("user.dir") + java.io.File.separator + "version.txt");
			try {
				List<String> lines = Files.readAllLines(Paths.get(filename.getAbsolutePath()), StandardCharsets.UTF_8);
				for (String str : lines) {
					version = str;
					System.out.println("Version: " + str);
					updlog.logger.info("Version: " + str);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		progressBar.setValue(25);

		{
			URL location;
			URL verfile;

			try {
				location = new URL("https://github.com/Hunterszone/MyJavaGames/tree/master/EvilGalaxy");
				verfile = new URL(
						"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/version.txt");

				URLConnection connection = location.openConnection();
				URLConnection verconnection = verfile.openConnection();

				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				BufferedReader versin = new BufferedReader(new InputStreamReader(verconnection.getInputStream()));

				String inputLine;
				String inputVersion = versin.readLine();

				progressBar.setValue(50);

				PrintStream printStream = new PrintStream(new ConsoleForm());

				while ((inputLine = in.readLine()) != null) {

					System.setOut(printStream);
					System.setErr(printStream);

					if (!inputVersion.contains(version)) {
						needDownload = true;
						System.out.println("Downloading!");
						File filename = new File(System.getProperty("user.dir") + java.io.File.separator + "version.txt");
						PrintWriter writer = new PrintWriter(filename.getAbsoluteFile(), "UTF-8");
						writer.println(inputVersion);
						System.out.println("Updated to version " + inputVersion + " from version " + version);
						writer.close();
					} else {
						System.out.println("No download needed!");
						File filename = new File(System.getProperty("user.dir") + java.io.File.separator + "version.txt");
						PrintWriter writer = new PrintWriter(filename.getAbsoluteFile(), "UTF-8");
						writer.print("");
						writer.println("up-to-date");
						writer.close();
					}
				}

				updlog.logger.info("Downloading!");
				updlog.logger.info("Updated to version " + inputVersion + " from version " + version);

				progressBar.setValue(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	JProgressBar progressBar;
	JProgressBar oprogressBar;
	int i = 0;
	List<Thread> threads = new ArrayList<Thread>();
	long lastTime = 0;

	public Launcher() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		setTitle("Updating game...");
		setBounds(width / 2 - 200, height / 2 - 50, 400, 100);
		setVisible(true);
		progressBar = new JProgressBar(0);
		oprogressBar = new JProgressBar(0);
		progressBar.setBounds(0, 0, 400, 50);
		oprogressBar.setBounds(0, 50, 400, 50);
		add(progressBar);
		add(oprogressBar);
		progressBar.setValue(0);
		oprogressBar.setValue(0);
		DrawScene.initVoice("Updating...");
		needDownload();

		{
			{
				File f = new File("saves/");
				if (!f.exists()) {
					if (!f.mkdir()) {
						System.out.println("Unable to create dir saves/!!");
						updlog.logger.info("Unable to create dir saves/!!");
						System.exit(1);
					} else {
						System.out.println("Created dir saves/!");
						updlog.logger.info("Created dir saves/!");
					}
				} else {
					System.out.println("Dir saves/ already exists");
					updlog.logger.info("Dir saves/ already exists");
				}
			}

			{
				File f = new File("sounds/");
				if (!f.exists()) {
					if (!f.mkdir()) {
						System.out.println("Unable to create dir sounds/!!");
						updlog.logger.info("Unable to create dir sounds/!!");
						System.exit(1);
					} else {
						System.out.println("Created dir sounds/!");
						updlog.logger.info("Created dir sounds/!");
					}
				} else {
					System.out.println("Dir sounds/ already exists");
					updlog.logger.info("Dir sounds/ already exists");
				}
			}

			{
				File f = new File("images/");

				if (!f.exists()) {
					if (!f.mkdir()) {
						System.out.println("Unable to create dir images/!!");
						updlog.logger.info("Unable to create dir images/!!");
						System.exit(1);
					} else {
						System.out.println("Created dir images/!");
						updlog.logger.info("Created dir images/!");
					}
				} else {
					System.out.println("Dir images/ already exists");
					updlog.logger.info("Dir images/ already exists");
				}
			}

			progressBar.setValue(10);
			try {
				{
					URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/saves/save.txt");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("HEAD");
					File fi = new File("saves/save.txt");
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						download(
								"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/saves/save.txt",
								"saves/save.txt", conn.getContentLength());
					} else {
						System.out.println("No need to download saves.txt!");
						updlog.logger.info("No need to download saves.txt!");
					}
				}

				{
					URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/images.txt");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("HEAD");
					File fi = new File("images.txt");
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						download(
								"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/images.txt",
								"images.txt", conn.getContentLength());
					} else {
						System.out.println("No need to download images.txt!");
						updlog.logger.info("No need to download images.txt!");
					}
				}

				{
					URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/sounds.txt");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("HEAD");
					File fi = new File("sounds.txt");
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						download(
								"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/sounds.txt",
								"sounds.txt", conn.getContentLength());
					} else {
						System.out.println("No need to download sounds.txt!");
						updlog.logger.info("No need to download sounds.txt!");
					}

				}

				progressBar.setValue(15);
				ArrayList<String> downloadImg = readTextFile("images.txt");
				ArrayList<String> downloadSounds = readTextFile("sounds.txt");

				for (final String str : downloadImg) {

					URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/images/"
									+ str);
					final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					progressBar.setValue(30);
					conn.setRequestMethod("HEAD");
					File fi = new File("images/" + str);
					if (!fi.getParentFile().exists()) {
						fi.getParentFile().mkdirs();
					}
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						progressBar.setValue(50);
						Thread t = new Thread() {
							@Override
							public void run() {
								download(
										"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/images/"
												+ str,
										"images/" + str, conn.getContentLength());
							}
						};
						t.start();
						threads.add(t);

					} else {
						System.out.println("No need to download " + "images/" + str);
						updlog.logger.info("No need to download " + "images/" + str);
						progressBar.setValue(100);
					}
				}
				for (final String str : downloadSounds) {

					URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/sounds/"
									+ str);
					final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					// progressBar.setValue(30);
					conn.setRequestMethod("HEAD");
					File fi = new File("sounds/" + str);
					if (!fi.getParentFile().exists()) {
						fi.getParentFile().mkdirs();
					}
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						// progressBar.setValue(50);
						Thread t = new Thread() {
							@Override
							public void run() {
								download(
										"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/sounds/"
												+ str,
										"sounds/" + str, conn.getContentLength());
							}
						};
						t.start();
						threads.add(t);

					} else {
						System.out.println("No need to download " + "sounds/" + str);
						updlog.logger.info("No need to download " + "sounds/" + str);
						// progressBar.setValue(100);
					}

				}
				i++;
				oprogressBar.setValue((int) ((i * 100.0f) / downloadImg.size() - threads.size()));
			} catch (Exception e) {

			}
		}
		progressBar.setValue(50);
		File f = new File("EvilGalaxy.jar");
		System.out.println("Exists: " + f.exists());
		updlog.logger.info("Exists: " + f.exists());
		try {
			URL url = new URL(
					"https://github.com/Hunterszone/MyJavaGames/blob/master/EvilGalaxy/EvilGalaxy.jar?raw=true");
			final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("HEAD");
			if (needDownload || !f.exists() || f.length() != conn.getContentLength()) {

				Thread t = new Thread() {

					@Override
					public void run() {
						download(
								"https://github.com/Hunterszone/MyJavaGames/blob/master/EvilGalaxy/EvilGalaxy.jar?raw=true",
								"EvilGalaxy.jar", conn.getContentLength());
					}
				};
				t.start();
				threads.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		progressBar.setValue(95);

		String exec = (System.getProperty("user.dir") + java.io.File.separator + "EvilGalaxy.jar");
		String[] command = { "java", "-jar", exec };
		ProcessBuilder pb = new ProcessBuilder(command[0], command[1], command[2]);
		progressBar.setValue(100);
		System.out.println(pb.command());
		try {
			pb.start();
		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.println("Running " + exec);
		dispose();

		System.exit(0);

	}

	void download(String source, String destination, int size) {

		// ten percent of the total download size
		File ofile = new File(System.getProperty("user.dir") + "", destination);
		System.out.println("\nDownloading from\n\t " + source + "\nTo\n\t " + destination + "\n");
		updlog.logger.logp(Level.INFO, "Downloading from\n\t " + source + "\nTo\n\t " + destination + "\n", "", "");
		try {
			if (ofile.exists())
				ofile.delete();
			if (!ofile.createNewFile()) {
				throw new IOException("Can't create " + ofile.getAbsolutePath());
			}

			int inChar;
			byte[] buff = new byte[16 * 1024];
			URL url = new URL(source);
			InputStream input = url.openStream();
			// OutputStream out = new FileOutputStream(ofile);
			// BufferedOutputStream fos = new BufferedOutputStream(out);
			FileOutputStream fos = new FileOutputStream(ofile);
			while ((inChar = input.read(buff)) != -1) {
				if (System.nanoTime() > lastTime + 2000000000) {
					lastTime = System.nanoTime();
					int percentage = (int) ((i * 100.0f) / size);

					progressBar.setValue(((int) ((percentage * 100.0f) / 100)));
					setTitle(ofile.getName() + ": " + progressBar.getValue() + "%" + " Total: "
							+ oprogressBar.getValue() + "%");
				}
				fos.write(buff, 0, inChar);
			}
			i++;
			oprogressBar.setValue((int) ((i * 100.0f) / threads.size()));
			input.close();
			fos.close();
			System.out.println("Downloaded " + ofile.getAbsolutePath());
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> readTextFile(String fileName) {

		ArrayList<String> values = new ArrayList<String>();
		FileReader file = null;

		try {

			file = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(file);
			String line = "";
			while ((line = reader.readLine()) != null) {
				values.add(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					// Ignore issues during closing
				}
			}
		}
		return values;
	}
}