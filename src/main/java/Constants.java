import java.nio.file.Path;

public final class Constants {
    final static Path PROJECT_DATA_DIR_PATH = Path.of("data");
    final static Path FRIEDBERG_DATA_FILE_PATH = PROJECT_DATA_DIR_PATH.resolve("friedberg_data");
}
