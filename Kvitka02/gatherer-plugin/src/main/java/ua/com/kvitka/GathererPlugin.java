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

@Mojo(name="gather-code")
public class GathererPlugin extends AbstractMojo {

    @Parameter(defaultValue = "${project.basedir}", readonly = true)
    private File baseDir;

    @Parameter(defaultValue = "${project.build.directory}/gathered-code.txt")
    private File outputFile;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Path sourceDir = new File(baseDir, "src/main/java").toPath();
        Path outputPath = outputFile.toPath();

        try {
            Files.deleteIfExists(outputPath);
            Files.createFile(outputPath);

            try(Stream<Path> paths = Files.walk(sourceDir)) {
                List<Path> files = paths.filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().endsWith(".java"))
                        .toList();

                for(Path file : files) {
                    String content = Files.readString(file);

                    String separator = "\n==================== ";
                    separator += " File: " + baseDir.toPath().relativize(file);
                    separator += " ==================== \n\n";

                    Files.writeString(outputPath, separator + content, StandardOpenOption.APPEND);
                }

                getLog().info("Java files are gathered in: " + outputPath);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
