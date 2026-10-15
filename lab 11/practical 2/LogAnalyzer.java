import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

public class LogAnalyzer {
    public static void main(String[] args) {
        Path logDir = Paths.get("logs");
        String keyword = "ERROR";

        try {
            // Setup sample log files
            Files.createDirectories(logDir);
            Files.write(logDir.resolve("server.log"), Arrays.asList(
                "INFO: Server started",
                "ERROR: Database connection failed",
                "INFO: Retrying connection"
            ));
            Files.write(logDir.resolve("app.log"), Arrays.asList(
                "INFO: App launched",
                "ERROR: Invalid user input",
                "ERROR: Timeout occurred",
                "INFO: Shutting down"
            ));

            int totalLines = 0;
            int keywordCount = 0;

            System.out.println("--- Analyzing Log Files ---");
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(logDir, "*.log")) {
                for (Path entry : stream) {
                    // Get file attributes
                    BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                    System.out.println("File: " + entry.getFileName() + " | Size: " + attrs.size() + " bytes");

                    // Read line by line
                    try (BufferedReader reader = Files.newBufferedReader(entry)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            totalLines++;
                            if (line.contains(keyword)) {
                                keywordCount++;
                            }
                        }
                    }
                }
            }

            System.out.println("\n--- Summary ---");
            System.out.println("Total Lines Processed : " + totalLines);
            System.out.println("Keyword '" + keyword + "' Found : " + keywordCount + " times");

        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        }
    }
}