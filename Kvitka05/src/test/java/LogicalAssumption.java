import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class LogicalAssumption {
    @Tag("system")
    @Test
    void onlyWindowsTest() {
        String osName = System.getProperty("os.name");
        assumeTrue(osName.contains("Windows"), "The test should only be run on Windows");

        Path winJavaPath = Paths.get("C:\\Program Files\\Java\\jdk-21");
        assumeTrue(Files.exists(winJavaPath), "The path " + winJavaPath + " not found on this PC");

        assertTrue(Files.isDirectory(winJavaPath), "\"jdk-21\" must be a folder");
        assertEquals("C:\\", winJavaPath.getRoot().toString(), "The root drive should be C:\\");
        assertEquals("jdk-21", winJavaPath.getFileName().toString(), "The final folder should be \"jdk-21\"");
    }
}
