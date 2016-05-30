package ru.nsu.ccfit.chirikhin.chat.client;

import javafx.application.Platform;
import javafx.scene.control.*;
import org.apache.log4j.Logger;

import java.io.Closeable;
import java.util.Optional;

public class EnterUsernameView implements Closeable{
    private static final Logger logger = Logger.getLogger(EnterUsernameView.class.getName());

    private TextInputDialog dialog = new TextInputDialog("Wind");

    public String show() {
        dialog.setTitle("Windogram");
        dialog.setHeaderText("Windogram");
        dialog.setContentText("Please, enter your nickname:");

        dialog.setResultConverter((dialogButton) -> {
            if (dialogButton == ButtonType.OK) {
                return dialog.getEditor().getText();
            } else {
                return null;
            }
        });

        Optional<String> str = dialog.showAndWait();

        if (!str.isPresent()) {
            return null;
        }

        return str.get();
    }

    @Override
    public void close() {
        Platform.runLater(() -> {
            Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancelButton.fire();
        });
    }
}
