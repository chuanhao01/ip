package ui;

import java.io.InputStream;

import friedberg.CommandResult;
import friedberg.Friedberg;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Friedberg friedberg;

    private final Image userImage = loadImageOrPlaceholder("/images/DaUser.png");
    private final Image friedbergImage = loadImageOrPlaceholder("/images/DaDuke.png");

    /**
     * Keeps the newest dialog visible as the conversation grows.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Loads an avatar, using a transparent placeholder when the optional resource is absent.
     *
     * @param path absolute classpath location of the image
     * @return avatar or transparent placeholder
     */
    private Image loadImageOrPlaceholder(String path) {
        InputStream imageStream = this.getClass().getResourceAsStream(path);
        if (imageStream == null) {
            return new WritableImage(1, 1);
        }
        return new Image(imageStream);
    }

    /**
     * Injects the Friedberg instance used to process GUI input.
     *
     * @param friedberg shared Friedberg backend for this GUI session
     */
    public void setFriedberg(Friedberg friedberg) {
        this.friedberg = friedberg;
        dialogContainer.getChildren().add(DialogBox.getFriedbergDialog(friedberg.getGreeting(), friedbergImage));
    }

    /**
     * Creates dialog boxes for the user's input and Friedberg's reply.
     * Clears input and exits JavaFX when the bye command is processed.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        CommandResult result = friedberg.processInput(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getFriedbergDialog(result.message(), friedbergImage)
        );
        userInput.clear();

        if (result.shouldExit()) {
            Platform.exit();
        }
    }
}
