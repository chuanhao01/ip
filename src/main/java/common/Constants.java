package common;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

public final class Constants {
    public final static Path PROJECT_DATA_DIR_PATH = Path.of("data");
    public final static Path FRIEDBERG_DATA_FILE_PATH = PROJECT_DATA_DIR_PATH.resolve("friedberg_data");

    public final static DateTimeFormatter DATETIME_RENDER_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyy (E)");
}
