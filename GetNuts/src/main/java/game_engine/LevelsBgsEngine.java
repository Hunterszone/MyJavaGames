package game_engine;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import main.Main;

public class LevelsBgsEngine {

	static List<String> levels = new ArrayList<String>();
	static List<Image> backgrounds = new ArrayList<Image>();
	static char[][] map;
	private static String fileName;

	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = null;
		if (new File(path).exists()) {
			encoded = Files.readAllBytes(Paths.get(path));
		}
		return new String(encoded, encoding);
	}

	public static void addLevels() {
		try {
			final Stream<Path> files = Files.list(Paths.get("levels"));
			final long filesCount = files.count();
			IntStream.range(0, (int) filesCount).forEach(i -> {
				String filePath = "levels/level" + i + ".txt";
				Path path = Paths.get(filePath);
				Path localFileName = path.getFileName();
				fileName = localFileName.toString();
				String levelString = null;
				try {
					levelString = readFile(filePath, Charset.defaultCharset());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				map = stringToTwoDimCharArr(levelString, map);
				levels.add(levelString);
			});
			files.close();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addBackgrounds() {
		try {
			final Stream<Path> images = Files.list(Paths.get("backgrounds"));
			final long imagesCount = images.count();
			final int width = (int) Main.dim.getWidth();
			final int height = (int) Main.dim.getHeight();
			IntStream.range(0, (int) imagesCount).forEach(i -> {
				File sourceImage = new File("backgrounds/bg" + i + ".jpg");
				Image levelBG = null;
				try {
					levelBG = ImageIO.read(sourceImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				backgrounds.add(levelBG.getScaledInstance(width, height, Image.SCALE_SMOOTH));
			});
			images.close();
		} catch (final IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static char[][] stringToTwoDimCharArr(String str, char[][] boardArray) {
		int rows = 0, columns = 0, offset = 0;
		try {
			rows = getNumOfRows(fileName);
			columns = getNumOfCols(fileName);
			boardArray = new char[rows][columns];
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				boardArray[i][j] = str.charAt(offset++);
				System.out.print(boardArray[i][j]);
			}
			System.out.println();
		}
		return boardArray;
	}

	public static int getNumOfRows(String filename) throws IOException {
		final InputStream is = new BufferedInputStream(new FileInputStream("levels/" + filename));
		try {
			final byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}

	public static int getNumOfCols(String filename) {
		final File file = new File("levels/" + filename);
		Scanner scanner;
		try {
			scanner = new Scanner(file);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		}

		int number = 0;
		if (scanner.hasNextLine()) {
			number = scanner.nextLine().split(",").length;
		}
		scanner.close();
		return number;
	}
}