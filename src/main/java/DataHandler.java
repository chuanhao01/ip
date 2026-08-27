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

    /**
     * Reads the data file and returns the entire thing as a string
     *
     * @throws Exception Any internal exceptions
     */
    public String read() throws Exception {
        return Files.readString(this.dataFilePath);
    }

    /**
     * Action to write the data into the data file
     *
     * @param data String we want to write into the data file
     * @throws Exception
     */

    public void write(String data) throws Exception {
        Files.writeString(this.dataFilePath, data);
    }

}
