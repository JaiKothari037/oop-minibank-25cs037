import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

class TreeReportVisitor extends SimpleFileVisitor<Path> {
    private final BufferedWriter writer;
    private int fileCount = 0;
    private long totalBytes = 0;

    public TreeReportVisitor(BufferedWriter writer) {
        this.writer = writer;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        fileCount++;
        totalBytes += attrs.size();

        String record = String.format("File: %-25s | Size: %-8d bytes | Modified: %s",
                file.getFileName(), attrs.size(), attrs.lastModifiedTime());
        
        writer.write(record);
        writer.newLine();
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        writer.write("FAILED to read: " + file.getFileName() + " (" + exc.getMessage() + ")");
        writer.newLine();
        return FileVisitResult.CONTINUE;
    }

    public int getFileCount() {
        return fileCount;
    }

    public long getTotalBytes() {
        return totalBytes;
    }
}

public class FolderTreeWalkerReport {
    public static void main(String[] args) {
        Path startDir = Paths.get("sample_workspace");
        Path reportFile = Paths.get("directory_summary.txt");

        try {
            // Setup demo folder structure
            Files.createDirectories(startDir.resolve("src/model"));
            Files.createDirectories(startDir.resolve("docs"));
            Files.write(startDir.resolve("src/Main.java"), "public class Main {}".getBytes());
            Files.write(startDir.resolve("src/model/User.java"), "class User {}".getBytes());
            Files.write(startDir.resolve("docs/readme.txt"), "Documentation content".getBytes());

            // Walk folder tree and build report file
            try (BufferedWriter bw = Files.newBufferedWriter(reportFile)) {
                bw.write("=========================================================================");
                bw.newLine();
                bw.write("                       DIRECTORY TREE SUMMARY REPORT                      ");
                bw.newLine();
                bw.write("=========================================================================");
                bw.newLine();

                TreeReportVisitor visitor = new TreeReportVisitor(bw);
                Files.walkFileTree(startDir, visitor);

                bw.newLine();
                bw.write("-------------------------------------------------------------------------");
                bw.newLine();
                bw.write(String.format("Total Files Visited : %d", visitor.getFileCount()));
                bw.newLine();
                bw.write(String.format("Total Folder Size   : %d bytes", visitor.getTotalBytes()));
                bw.newLine();
            }

            System.out.println("Directory walk completed successfully!");
            System.out.println("Summary report written to: " + reportFile.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error processing folder walk: " + e.getMessage());
        }
    }
}