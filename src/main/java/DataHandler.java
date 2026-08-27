import java.nio.file.Files;
import java.nio.file.Path;

public class DataHandler {
    private Path dataFolderPath;
    private Path dataFilePath;
    public DataHandler(Path dataFolderPath, Path dataFilePath) throws Exception {
        this.dataFolderPath = dataFolderPath;
        this.dataFilePath = dataFilePath;
        // On initialization, it will create the directory and file if it does not
        // exists
        if (!(Files.exists(this.dataFolderPath) && Files.isDirectory(this.dataFolderPath))) {
            // Data directory does not exist
            Files.createDirectories(this.dataFolderPath);
        }
        if (!Files.exists(this.dataFilePath)) {
            Files.createFile(this.dataFilePath);
        }
    }

}
