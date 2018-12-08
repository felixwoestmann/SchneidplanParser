package ui;

import debug.CustomLogger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Schneidplan;
import processing.Parser;

import java.io.IOException;
import java.net.URL;

public class SchneidplanGUI extends Application {

    private Stage stage;
    //
    private MenuItem about;
    private MenuItem showLog;
    //
    private ImageView imageview;
    //
    private ProgressBar progressBar;
    //
    private Parser parser;
    private Schneidplan schneidplan;
    private LogWindow logWindow = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        try {
            setUpUi();
        } catch (IOException e) {
            CustomLogger.getInstance().log(e);

        }
        obtainUiElements();
        parser = new Parser();
        setUpFunctionality();
        //set action which should be performed if windows is closed
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::close);
    }

    private void close(WindowEvent event) {
        if (logWindow != null) {
            logWindow.close();
        }
    }


    private void setUpUi() throws IOException {
        //setUpUi
        FXMLLoader loader = new FXMLLoader();
        URL url = this.getClass().getResource("ui.fxml");
        loader.setLocation(url);
        GridPane rootLayout = loader.load();
        stage.setResizable(false);
        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Schneidplan Parser");
    }


    private void obtainUiElements() {
        Scene sc = stage.getScene();
        //menu
        MenuBar bar = (MenuBar) sc.lookup("#bar");
        ObservableList<MenuItem> menuItems = bar.getMenus().get(0).getItems();
        about = menuItems.get(0);
        showLog = menuItems.get(1);
        progressBar = (ProgressBar) sc.lookup("#progress");
        imageview = (ImageView) sc.lookup("#imageview");
    }

    private void setUpFunctionality() {
        showLog.setOnAction(actionEvent -> openLogWindow());
        imageview.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != imageview
                        && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });
        imageview.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                  CustomLogger.getInstance().log(db.getFiles().toString());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });
    }

    private void openLogWindow() {
        CustomLogger.getInstance().log("Opened log window");

        logWindow = new LogWindow();
        try {
            logWindow.start(new Stage());
        } catch (Exception e) {
            CustomLogger.getInstance().log(e);

        }

    }

}
