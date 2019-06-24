package launcher;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class UpdateLogger {
	final Logger logger = Logger.getLogger(UpdateLogger.class.getName());
	private FileHandler fh = null;

	{
		File f = new File("logs/");
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

	public UpdateLogger() {
		// just to make our log file nicer :)
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fh = new FileHandler(System.getProperty("user.dir") + "/logs/UpdateLog_"
					+ format.format(Calendar.getInstance().getTime()) + ".log");
		} catch (Exception e) {
			e.printStackTrace();
		}

		fh.setFormatter(new SimpleFormatter());
		logger.addHandler(fh);
	}

	public static void main(String[] args) {
		new UpdateLogger();
	}
}