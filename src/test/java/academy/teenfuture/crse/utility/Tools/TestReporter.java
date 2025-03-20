package academy.teenfuture.crse.utility.Tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TestReporter {
    private static PrintWriter writer;
    private static final String REPORT_DIR = System.getProperty("user.dir") + File.separator + "report";

    // Initialize the reporter using a custom report file
    public static void init(String reportFileName) {
        try {
            // Create the report directory if does not exist
            File reportDir = new File(REPORT_DIR);
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }
            
            // Build the full file path
            String reportFilePath = reportDir.getAbsolutePath() + File.separator + reportFileName;

            // 'false' will overwrite any existing file
            writer = new PrintWriter(new BufferedWriter(new FileWriter(reportFilePath, false)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Write a log message to the report
    public static void log(String message) {
        if (writer != null) {
            writer.println(message);
            writer.flush();
        }
        // also print it to the console
        System.out.println(message);
    }

    public static void close() {
        if (writer != null) {
            writer.close();
            writer = null;
        }
    }
}
