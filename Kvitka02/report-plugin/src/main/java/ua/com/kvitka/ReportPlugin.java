package ua.com.kvitka;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

@Mojo(name = "create-report")
public class ReportPlugin extends AbstractMojo {
    @Parameter(defaultValue = "${project.basedir}", readonly = true)
    private File baseDir;

    @Parameter(defaultValue = "${project.build.directory}/report-file.txt")
    private File outputFile;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Path sourceDir = new File(baseDir, "src/main/java").toPath();
        Path reportFile = outputFile.toPath();

        try {
            Files.deleteIfExists(reportFile);
            Files.createFile(reportFile);

            try(Stream<Path> paths = Files.walk(sourceDir)) {
                List<Path> files = paths.filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().endsWith(".java"))
                        .toList();

                int totalFiles = files.size();
                int totalLines = 0;
                int codeLines = 0;
                int commentLines = 0;
                int emptyLines = 0;

                StringBuilder report = new StringBuilder();
                report.append("========================================\n");
                report.append("       PROJECT'S CODE LINES REPORT      \n");
                report.append("========================================\n\n");

                for(Path file : files) {
                    List<String> lines = Files.readAllLines(file);
                    boolean isBlockCom = false;

                    int fileTotalLines = 0;
                    int fileCodeLines = 0;
                    int fileCommentLines = 0;
                    int fileEmptyLines = 0;

                    for(String line : lines) {
                        fileTotalLines++;
                        totalLines++;

                        String trimmedLine = line.trim();
                        if(trimmedLine.isEmpty()) {
                            fileEmptyLines++;
                            emptyLines++;
                            continue;
                        }

                        if(isBlockCom) {
                            fileCommentLines++;
                            commentLines++;
                            if(trimmedLine.contains("*/")) {
                                isBlockCom = false;
                            }
                            continue;
                        }

                        String lineWithoutStrings = trimmedLine.replaceAll("\".*?\"", "");


                        if(lineWithoutStrings.contains("/*")) {
                            if(!lineWithoutStrings.contains("*/")) {
                                isBlockCom = true;
                            }
                            fileCommentLines++;
                            commentLines++;
                            continue;
                        }

                        if(lineWithoutStrings.contains("//")) {
                            fileCommentLines++;
                            commentLines++;
                            continue;
                        }

                        fileCodeLines++;
                        codeLines ++;
                    }

                    report.append("File: ").append(baseDir.toPath().relativize(file)).append("\n");
                    report.append("Lines number: ").append(fileTotalLines)
                            .append(" | Code lines: ").append(fileCodeLines)
                            .append(" | Comment lines: ").append(fileCommentLines)
                            .append(" | Empty lines: ").append(fileEmptyLines).append("\n\n");

                }

                report.append("========================================\n" +
                                "             OVERALL RESULT            \n" +
                                "========================================\n" +
                                "Files analyzed: " + totalFiles + "\n\n" +
                                "--- LINES STATISTICS ---\n" +
                                "Total lines:    " + totalLines + "\n" +
                                "Lines with code:       " + codeLines + "\n" +
                                "Lines with comments: " + commentLines + "\n" +
                                "Empty lines:   " + emptyLines + "\n" +
                                "========================================\n");

                Files.writeString(reportFile, report.toString(), StandardOpenOption.APPEND);

                getLog().info("The report is in: " + reportFile);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}