package ui;

import debug.CustomLogger;
import debug.LogObserver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LogWindow extends Application implements LogObserver {
    private Stage stage;
    private TextArea logspace;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        setUpUi();
        CustomLogger.getInstance().addObserver(this);
    }

    private void setUpUi() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL url = this.getClass().getResource("logwindow.fxml");
        loader.setLocation(url);
        FlowPane root = loader.load();
        //    stage.setResizable(false);
        Scene scene = new Scene(root);
        logspace = (TextArea) scene.lookup("#logspace");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void logForUI(String message) {
        if (logspace != null) {
            logspace.appendText(String.format("%s %n", message));
        }
    }

    void close() {
        Platform.exit();
        stage.close();

    }
}
