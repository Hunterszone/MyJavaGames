package main;

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
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class Launcher extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String version = "0.0";
	boolean needDownload = false;
	static JFrame frame;

	public static void main(String[] args) {
		frame = new Launcher();
	}

//	public void printTextField(String text) {
//		textArea.setText(text + "\n");
//	}

	public void needDownload() {
		{
			File filename = new File(System.getProperty("user.dir") + "/version.txt");
			try {
				List<String> lines = Files.readAllLines(Paths.get(filename.getAbsolutePath()), StandardCharsets.UTF_8);
				for (String str : lines) {
					version = str;
//					printTextField("Version: " + str);
					System.out.println("Version: " + str);
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
				location = new URL("https://github.com/Hunterszone/MyJavaGames/tree/master/PotOGold");
				verfile = new URL(
						"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/version.txt");

				URLConnection connection = location.openConnection();
				URLConnection verconnection = verfile.openConnection();

				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				BufferedReader versin = new BufferedReader(new InputStreamReader(verconnection.getInputStream()));

				String inputLine;
				String inputVersion = versin.readLine();

				progressBar.setValue(50);
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine + " || " + version);
					if (!inputVersion.contains(version)) {
						needDownload = true;
						System.out.println("Downloading!");
						File filename = new File(System.getProperty("user.dir") + "/version.txt");
						if (filename.exists())
							filename.delete();
						filename.createNewFile();
						PrintWriter writer = new PrintWriter(filename.getAbsoluteFile(), "UTF-8");
						writer.println(inputVersion);
						System.out.println("Updated to version " + inputVersion + " from version " + version);
						writer.close();
					} else {
						System.out.println("No download needed!");
						File filename = new File(System.getProperty("user.dir") + "/version.txt");
						PrintWriter writer = new PrintWriter(filename.getAbsoluteFile(), "UTF-8");
						writer.print("");
						writer.println("up-to-date");
						writer.close();
					}
				}
				progressBar.setValue(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
		frame = this;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		frame.setTitle("Downloading game...");
		frame.setBounds(width / 2 - 200, height / 2 - 50, 400, 100);
		frame.setVisible(true);
		progressBar = new JProgressBar(0);
		oprogressBar = new JProgressBar(0);
		progressBar.setBounds(0, 0, 400, 50);
		oprogressBar.setBounds(0, 50, 400, 50);
		frame.add(progressBar);
		frame.add(oprogressBar);
		progressBar.setValue(0);
		oprogressBar.setValue(0);
		needDownload();

		{
			{
				File f = new File("res/");

				if (!f.exists()) {
					if (!f.mkdir()) {
						System.out.println("Unable to create dir!!");
						System.exit(1);
					} else {
						System.out.println("Created dir!");
					}
				} else {
					System.out.println("Dir already exists");
				}
			}
			{
				File f = new File("res/sounds/");
				if (!f.exists()) {
					if (!f.mkdir()) {
						System.out.println("Unable to create dir!!");
						System.exit(1);
					} else {
						System.out.println("Created dir!");
					}
				} else {
					System.out.println("Dir already exists");
				}
			}
			{
				File f = new File("res/particles/");
				if (!f.exists()) {
					if (!f.mkdir()) {
						System.out.println("Unable to create dir!!");
						System.exit(1);
					} else {
						System.out.println("Created dir!");
					}
				} else {
					System.out.println("Dir already exists");
				}
			}
			{
				File f = new File("res/fonts/");
				if (!f.exists()) {
					if (!f.mkdir()) {
						System.out.println("Unable to create dir!!");
						System.exit(1);
					} else {
						System.out.println("Created dir!");
					}
				} else {
					System.out.println("Dir already exists");
				}
			}

			progressBar.setValue(10);
			try {
				{
					URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/images.txt");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("HEAD");
					File fi = new File("res/images.txt");
					System.out.println((fi.length() != conn.getContentLength()) + " " + fi.length() + " || "
							+ conn.getContentLength());
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						System.out.println("Downloading resource!");
						download(
								"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/images.txt",
								"res/images.txt", conn.getContentLength());
					} else {
						System.out.println("No need to download resource!");
					}

				}

				{
					URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/sounds/sounds.txt");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("HEAD");
					File fi = new File("res/sounds/sounds.txt");
					System.out.println((fi.length() != conn.getContentLength()) + " " + fi.length() + " || "
							+ conn.getContentLength());
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						System.out.println("Downloading resource!");
						download(
								"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/sounds/sounds.txt",
								"res/sounds/sounds.txt", conn.getContentLength());
					} else {
						System.out.println("No need to download resource!");
					}

				}
				{
					URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/particles/particles.txt");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("HEAD");
					File fi = new File("res/particles/particles.txt");
					System.out.println((fi.length() != conn.getContentLength()) + " " + fi.length() + " || "
							+ conn.getContentLength());
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						System.out.println("Downloading resource!");
						download(
								"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/particles/particles.txt",
								"res/particles/particles.txt", conn.getContentLength());
					} else {
						System.out.println("No need to download resource!");
					}

				}
				{
					URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/fonts/fonts.txt");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("HEAD");
					File fi = new File("res/fonts/fonts.txt");
					System.out.println((fi.length() != conn.getContentLength()) + " " + fi.length() + " || "
							+ conn.getContentLength());
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						System.out.println("Downloading resource!");
						download(
								"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/fonts/fonts.txt",
								"res/fonts/fonts.txt", conn.getContentLength());
					} else {
						System.out.println("No need to download resource!");
					}

				}
				{
					URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/dlls.txt");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("HEAD");
					File fi = new File("dlls.txt");
					System.out.println((fi.length() != conn.getContentLength()) + " " + fi.length() + " || "
							+ conn.getContentLength());
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						System.out.println("Downloading resource!");
						download("https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/dlls.txt",
								"dlls.txt", conn.getContentLength());
					} else {
						System.out.println("No need to download resource!");
					}

				}
				progressBar.setValue(15);
				ArrayList<String> downloadImg = readTextFile("res/images.txt");
				ArrayList<String> downloadSounds = readTextFile("res/sounds/sounds.txt");
				ArrayList<String> downloadParticles = readTextFile("res/particles/particles.txt");
				ArrayList<String> downloadFonts = readTextFile("res/fonts/fonts.txt");
				ArrayList<String> downloadDLLs = readTextFile("dlls.txt");

				for (final String str : downloadImg) {

					URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/" + str);
					final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					progressBar.setValue(30);
					conn.setRequestMethod("HEAD");
					File fi = new File("res/" + str);
					if (!fi.getParentFile().exists()) {
						fi.getParentFile().mkdirs();
					}
					System.out.println((fi.length() != conn.getContentLength()) + " " + fi.length() + " || "
							+ conn.getContentLength());
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						System.out.println("Downloading resource!");
						progressBar.setValue(50);
						Thread t = new Thread() {
							@Override
							public void run() {
								download(
										"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/"
												+ str,
										"res/" + str, conn.getContentLength());
							}
						};
						t.start();
						threads.add(t);

					} else {
						System.out.println("No need to download resource!");
						progressBar.setValue(100);
					}

				}
				for (final String str : downloadSounds) {

					URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/sounds/"
									+ str);
					final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//					progressBar.setValue(30);
					conn.setRequestMethod("HEAD");
					File fi = new File("res/sounds/" + str);
					if (!fi.getParentFile().exists()) {
						fi.getParentFile().mkdirs();
					}
					System.out.println((fi.length() != conn.getContentLength()) + " " + fi.length() + " || "
							+ conn.getContentLength());
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						System.out.println("Downloading resource!");
//						progressBar.setValue(50);
						Thread t = new Thread() {
							@Override
							public void run() {
								download(
										"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/sounds/"
												+ str,
										"res/sounds/" + str, conn.getContentLength());
							}
						};
						t.start();
						threads.add(t);

					} else {
						System.out.println("No need to download resource!");
//						progressBar.setValue(100);
					}

				}

				for (final String str : downloadParticles) {

					URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/particles/"
									+ str);
					final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//					progressBar.setValue(30);
					conn.setRequestMethod("HEAD");
					File fi = new File("res/particles/" + str);
					if (!fi.getParentFile().exists()) {
						fi.getParentFile().mkdirs();
					}
					System.out.println((fi.length() != conn.getContentLength()) + " " + fi.length() + " || "
							+ conn.getContentLength());
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						System.out.println("Downloading resource!");
//						progressBar.setValue(50);
						Thread t = new Thread() {
							@Override
							public void run() {
								download(
										"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/particles/"
												+ str,
										"res/particles/" + str, conn.getContentLength());
							}
						};
						t.start();
						threads.add(t);

					} else {
						System.out.println("No need to download resource!");
//						progressBar.setValue(100);
					}

				}
				for (final String str : downloadFonts) {

					URL url = new URL(
							"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/fonts/"
									+ str);
					final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//					progressBar.setValue(30);
					conn.setRequestMethod("HEAD");
					File fi = new File("res/fonts/" + str);
					if (!fi.getParentFile().exists()) {
						fi.getParentFile().mkdirs();
					}
					System.out.println((fi.length() != conn.getContentLength()) + " " + fi.length() + " || "
							+ conn.getContentLength());
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						System.out.println("Downloading resource!");
//						progressBar.setValue(50);
						Thread t = new Thread() {
							@Override
							public void run() {
								download(
										"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/res/fonts/"
												+ str,
										"res/fonts/" + str, conn.getContentLength());
							}
						};
						t.start();
						threads.add(t);

					} else {
						System.out.println("No need to download resource!");
//						progressBar.setValue(100);
					}

				}

				for (final String str : downloadDLLs) {

					URL url = new URL("https://github.com/Hunterszone/MyJavaGames/raw/master/PotOGold/" + str);
					final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					progressBar.setValue(30);
					conn.setRequestMethod("HEAD");
					File fi = new File("/" + str);
					if (!fi.getParentFile().exists()) {
						fi.getParentFile().mkdirs();
					}
					System.out.println((fi.length() != conn.getContentLength()) + " " + fi.length() + " || "
							+ conn.getContentLength());
					if (!fi.exists() || fi.length() != conn.getContentLength()) {
						System.out.println("Downloading resource!");
						progressBar.setValue(50);
						Thread t = new Thread() {
							@Override
							public void run() {
								download("https://github.com/Hunterszone/MyJavaGames/raw/master/PotOGold/" + str,
										"/" + str, conn.getContentLength());
							}
						};
						t.start();
						threads.add(t);

					} else {
						System.out.println("No need to download resource!");
						progressBar.setValue(100);
					}

				}

				i++;
				oprogressBar.setValue((int) ((i * 100.0f) / downloadImg.size() - threads.size()));

			} catch (Exception e) {

			}
		}
		progressBar.setValue(50);
		File f = new File("PotOGold64bit.jar");
		System.out.println("Exists: " + f.exists());
		try {
			URL url = new URL(
					"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/PotOGold64bit.jar");
			final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("HEAD");
			if (needDownload || !f.exists() || f.length() != conn.getContentLength()) {

				Thread t = new Thread() {

					@Override
					public void run() {
						download(
								"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/PotOGold64bit.jar",
								"PotOGold64bit.jar", conn.getContentLength());
					}
				};
				t.start();
				threads.add(t);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		File f2 = new File("PotOGold32bit.jar");
		System.out.println("Exists: " + f2.exists());
		try {
			URL url = new URL(
					"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/PotOGold32bit.jar");
			final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("HEAD");
			if (needDownload || !f2.exists() || f2.length() != conn.getContentLength()) {

				Thread t = new Thread() {

					@Override
					public void run() {
						download(
								"https://raw.githubusercontent.com/Hunterszone/MyJavaGames/master/PotOGold/PotOGold32bit.jar",
								"PotOGold32bit.jar", conn.getContentLength());
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

		String exec = (System.getProperty("user.dir") + java.io.File.separator + "PotOGold64bit.jar");
		String[] command = { "java", "-jar", exec };
		ProcessBuilder pb = new ProcessBuilder(command[0], command[1], command[2]);
		progressBar.setValue(100);
		System.out.println(pb.command());
		try {
			pb.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Running " + exec);
		frame.dispose();

	}

	void download(String source, String destination, int size) {

		// ten percent of the total download size
		File ofile = new File(System.getProperty("user.dir") + "", destination);
		System.out.printf("\nDownloading\n\t%s\nTo\n\t%s\n", source, destination);
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
//			OutputStream out = new FileOutputStream(ofile);
//			BufferedOutputStream fos = new BufferedOutputStream(out);
			FileOutputStream fos = new FileOutputStream(ofile);
			while ((inChar = input.read(buff)) != -1) {
				if (System.nanoTime() > lastTime + 2000000000) {
					lastTime = System.nanoTime();
					int percentage = (int) ((i * 100.0f) / size);

					progressBar.setValue(((int) ((percentage * 100.0f) / 100)));
					frame.setTitle(ofile.getName() + ": " + progressBar.getValue() + "%" + " Total: "
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