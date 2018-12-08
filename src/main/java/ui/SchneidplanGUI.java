package ui;

import debug.CustomLogger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Schneidplan;
import org.apache.commons.compress.compressors.FileNameUtil;
import org.apache.commons.io.FilenameUtils;
import processing.Parser;
import processing.Processor;
import processing.xlxs.XLXSAppender;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SchneidplanGUI extends Application {

    private Stage stage;
    //
    private MenuItem about;
    private MenuItem showLog;
    // step_one
    private ImageView dropzone_one;
    private TextArea text_one;
    // step_two
    private DatePicker datePicker_two;
    private TextArea text_two;
    // step_three
    private ImageView dropzone_three;
    private TextArea text_three;
    //
    private ProgressBar progressBar;
    //
    private Parser parser;
    private Processor processor;
    private Schneidplan schneidplan;
    private LogWindow logWindow = null;
    private File excelFile = null;
    private Image successImage;
    private Image plusImage;

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
        processor = new XLXSAppender();
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
        successImage = new Image("images/success.png");
        plusImage = new Image("images/plus.png");
    }


    private void obtainUiElements() {
        Scene sc = stage.getScene();
        //menu
        MenuBar bar = (MenuBar) sc.lookup("#bar");
        ObservableList<MenuItem> menuItems = bar.getMenus().get(0).getItems();
        about = menuItems.get(0);
        showLog = menuItems.get(1);
        //misc
        progressBar = (ProgressBar) sc.lookup("#progress");
        //step_one
        dropzone_one = (ImageView) sc.lookup("#dropzone_one");
        text_one = (TextArea) sc.lookup("#text_one");
        //step_two
        datePicker_two = (DatePicker) sc.lookup("#datepicker_two");
        text_two = (TextArea) sc.lookup("#text_two");
        //step_one
        dropzone_three = (ImageView) sc.lookup("#dropzone_three");
        text_three = (TextArea) sc.lookup("#text_three");
    }

    private void setUpFunctionality() {
        showLog.setOnAction(actionEvent -> openLogWindow());
        //step_one
        dropzone_one.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != dropzone_one
                        && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });
        dropzone_one.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                //chek if drag contains files
                if (db.hasFiles()) {
                    //check if drag only contains one file
                    if (db.getFiles().size() == 1) {
                        excelFile = db.getFiles().get(0);
                        //chek if the file is correct based on extension in awaiting processor
                        if (FilenameUtils.getExtension(excelFile.getAbsolutePath()).equals(processor.getFileExtensionWithoutDot())) {
                            CustomLogger.getInstance().log(db.getFiles().toString());
                            success = true;
                            dropzone_one.setImage(successImage);
                        }
                    }


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
