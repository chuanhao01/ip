package datahandler;

import java.nio.file.Files;
import java.nio.file.Path;

public class DataHandler {
    private final Path dataFolderPath;
    private final Path dataFilePath;

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
     * Reads the data file and returns its contents as a string.
     *
     * @return contents of the data file
     * @throws Exception if the data cannot be read
     */
    public String read() throws Exception {
        return Files.readString(this.dataFilePath).strip();
    }

    /**
     * Writes the given data into the data file.
     *
     * @param data string to write into the data file
     * @throws Exception if the data cannot be written
     */
    public void write(String data) throws Exception {
        Files.writeString(this.dataFilePath, data.strip());
    }

}
