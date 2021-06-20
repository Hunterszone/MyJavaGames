package launcher;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

import extras.ConsoleForm;
import marytts.exceptions.MaryConfigurationException;
import util.TextToSpeech;

public class Launcher extends JFrame {

	private static final long serialVersionUID = 1L;
	String version = "";
	boolean needDownload = false;
	static UpdateLogger updlog = new UpdateLogger();

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		try {
			new Launcher();
		} catch (final MaryConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void needDownload() throws FileNotFoundException, UnsupportedEncodingException {
		{
			final File filename = new File(System.getProperty("user.dir") + java.io.File.separator + "version.txt");
			try {
				final List<String> lines = Files.readAllLines(Paths.get(filename.getAbsolutePath()), StandardCharsets.UTF_8);
				for (final String str : lines) {
					version = str;
					System.out.println("Version: " + str);
					updlog.logger.info("Version: " + str);
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		progressBar.setValue(25);

		{
			URL location;
			URL verfile;
			String inputLine;
			final PrintStream printStream = new PrintStream(new ConsoleForm());
			File filename = new File(System.getProperty("user.dir") + java.io.File.separator + "version.txt");
			PrintWriter writer = new PrintWriter(filename.getAbsoluteFile(), "UTF-8");

			try {
				location = new URL("https://github.com/Hunterszone/MyJavaGames/tree/master/EvilGalaxy");
				verfile = new URL(
						"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/version.txt");

				final URLConnection connection = location.openConnection();
				final URLConnection verconnection = verfile.openConnection();

				final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				final BufferedReader versin = new BufferedReader(new InputStreamReader(verconnection.getInputStream()));

				final String inputVersion = versin.readLine();

				progressBar.setValue(50);


				while ((inputLine = in.readLine()) != null) {
					System.setOut(printStream);
					System.setErr(printStream);

					if (!inputVersion.contains(version)) {
						needDownload = true;
						System.out.println("Downloading!");
						writer.println(inputVersion);
						System.out.println("Updated to version " + inputVersion + " from version " + version);
						writer.close();
					} else {
						System.out.println("No download needed!");
						filename = new File(System.getProperty("user.dir") + java.io.File.separator + "version.txt");
						writer = new PrintWriter(filename.getAbsoluteFile(), "UTF-8");
						writer.print("");
						writer.println("up-to-date");
						writer.close();
					}
				}

				updlog.logger.info("Downloading!");
				updlog.logger.info("Updated to version " + inputVersion + " from version " + version);

				progressBar.setValue(100);
			} catch (final Exception e) {
				e.printStackTrace();
			} finally {
				printStream.close();
				writer.close();
			}
		}
	}

	private final JProgressBar progressBar;
	private final JProgressBar oprogressBar;
	int i = 0;
	List<Thread> threads = new ArrayList<Thread>();
	long lastTime = 0;

	public Launcher() throws MaryConfigurationException, FileNotFoundException, UnsupportedEncodingException {
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int width = (int) screenSize.getWidth();
		final int height = (int) screenSize.getHeight();
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
		TextToSpeech.playVoice("Updating...");
		needDownload();

		{
			{
				final File f = new File("saves/");
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
				final File f = new File("sounds/");
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
				final File f = new File("images/");

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
					final URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/saves/save.txt");
					final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("HEAD");
					final File fi = new File("saves/save.txt");
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
					final URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/images.txt");
					final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("HEAD");
					final File fi = new File("images.txt");
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
					final URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/sounds.txt");
					final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("HEAD");
					final File fi = new File("sounds.txt");
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
				final ArrayList<String> downloadImg = readTextFile("images.txt");
				final ArrayList<String> downloadSounds = readTextFile("sounds.txt");

				for (final String str : downloadImg) {

					final URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/images/"
									+ str);
					final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					progressBar.setValue(30);
					conn.setRequestMethod("HEAD");
					final File fi = new File("images/" + str);
					if (!fi.getParentFile().exists()) {
						fi.getParentFile().mkdirs();
					}
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						progressBar.setValue(50);
						final Thread t = new Thread() {
							@Override
							public void run() {
								try {
									download(
											"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/images/"
													+ str,
											"images/" + str, conn.getContentLength());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
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

					final URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/sounds/"
									+ str);
					final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					// progressBar.setValue(30);
					conn.setRequestMethod("HEAD");
					final File fi = new File("sounds/" + str);
					if (!fi.getParentFile().exists()) {
						fi.getParentFile().mkdirs();
					}
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						// progressBar.setValue(50);
						final Thread t = new Thread() {
							@Override
							public void run() {
								try {
									download(
											"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/EvilGalaxy/sounds/"
													+ str,
											"sounds/" + str, conn.getContentLength());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
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
			} catch (final Exception e) {

			}
		}
		progressBar.setValue(50);
		final File f = new File("EvilGalaxy.jar");
		System.out.println("Exists: " + f.exists());
		updlog.logger.info("Exists: " + f.exists());
		try {
			final URL url = new URL(
					"https://github.com/Hunterszone/MyJavaGames/blob/master/EvilGalaxy/EvilGalaxy.jar?raw=true");
			final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("HEAD");
			if (needDownload || !f.exists() || f.length() != conn.getContentLength()) {

				final Thread t = new Thread() {

					@Override
					public void run() {
						try {
							download(
									"https://github.com/Hunterszone/MyJavaGames/blob/master/EvilGalaxy/EvilGalaxy.jar?raw=true",
									"EvilGalaxy.jar", conn.getContentLength());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				t.start();
				threads.add(t);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		for (final Thread t : threads) {
			try {
				t.join();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			    Thread.currentThread().interrupt();
			}

		}

		progressBar.setValue(95);

		final String exec = (System.getProperty("user.dir") + java.io.File.separator + "EvilGalaxy.jar");
		final String[] command = { "java", "-jar", exec };
		final ProcessBuilder pb = new ProcessBuilder(command[0], command[1], command[2]);
		progressBar.setValue(100);
		System.out.println(pb.command());
		try {
			pb.start();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		System.out.println("Running " + exec);
		dispose();

		System.exit(0);

	}

	void download(String source, String destination, int size) throws IOException {

		// ten percent of the total download size
		final File ofile = new File(System.getProperty("user.dir") + "", destination);
		System.out.println("\nDownloading from\n\t " + source + "\nTo\n\t " + destination + "\n");
		updlog.logger.logp(Level.INFO, "Downloading from\n\t " + source + "\nTo\n\t " + destination + "\n", "", "");
		final URL url = new URL(source);
		final InputStream input = url.openStream();
		final FileOutputStream fos = new FileOutputStream(ofile);
		
		try {
			if (ofile.exists())
				ofile.delete();
			if (!ofile.createNewFile()) {
				throw new IOException("Can't create " + ofile.getAbsolutePath());
			}

			int inChar;
			final byte[] buff = new byte[16 * 1024];
			// OutputStream out = new FileOutputStream(ofile);
			// BufferedOutputStream fos = new BufferedOutputStream(out);
			while ((inChar = input.read(buff)) != -1) {
				if (System.nanoTime() > lastTime + 2000000000) {
					lastTime = System.nanoTime();
					final int percentage = (int) ((i * 100.0f) / size);

					progressBar.setValue(((int) ((percentage * 100.0f) / 100)));
					setTitle(ofile.getName() + ": " + progressBar.getValue() + "%" + " Total: "
							+ oprogressBar.getValue() + "%");
				}
				fos.write(buff, 0, inChar);
			}
			i++;
			oprogressBar.setValue((int) ((i * 100.0f) / threads.size()));
			System.out.println("Downloaded " + ofile.getAbsolutePath());
		} catch (final EOFException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			input.close();
			fos.close();
		}
	}

	public static ArrayList<String> readTextFile(String fileName) throws IOException {

		final ArrayList<String> values = new ArrayList<String>();
		FileReader file = null;
		final BufferedReader reader = new BufferedReader(file);

		try {
			file = new FileReader(fileName);
			String line = "";
			while ((line = reader.readLine()) != null) {
				values.add(line);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			reader.close();
			file.close();
		}
		return values;
	}
}