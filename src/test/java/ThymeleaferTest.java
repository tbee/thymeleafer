import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ThymeleaferTest {

    @Test
    public void templateOnly() throws IOException {

        // Given
        File tempFile = createTempFile();

        // When
        Thymeleafer.main(new String[]{"src/test/resources/templateOnly.html", "-o", tempFile.getAbsolutePath()});
        String result = readFileThenDelete(tempFile);

        // Then
        Assertions.assertTrue(result.contains("<p>Hello, null!</p>")); // No values, but still replaced
        Assertions.assertTrue(result.contains("<h1>Thymeleaf Fragments header</h1>")); // fragment was resolved
    }

    @Test
    public void templateOnly_ButWithValues() throws IOException {

        // Given
        File tempFile = createTempFile();

        // When
        Thymeleafer.main(new String[]{"src/test/resources/templateOnly.html", "-v", "src/test/resources/templateOnly.properties", "-o", tempFile.getAbsolutePath()});
        String result = readFileThenDelete(tempFile);

        // Then
        Assertions.assertTrue(result.contains("<p>Hello, Thom!</p>")); // Value is replaced
        Assertions.assertTrue(result.contains("<h1>Thymeleaf Fragments header</h1>")); // fragment was resolved
    }

    private File createTempFile() {
        try {
            File tempFile = File.createTempFile(this.getClass().getSimpleName(), ".html");
            tempFile.deleteOnExit();
            return tempFile;
        }
        catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String readFile(File file) {
        try {
            return Files.readString(Path.of(file.toURI()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String readFileThenDelete(File file) {
        String content = readFile(file);
        file.delete();
        return content;
    }
}
