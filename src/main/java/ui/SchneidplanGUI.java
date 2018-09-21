package ui;

import debug.CustomLogger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Schneidplan;
import processing.CSVProcessor;
import processing.Parser;
import processing.Processor;
import processing.XLXSProcessor;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SchneidplanGUI extends Application {

    private Stage stage;
    //
    private MenuItem about;
    private MenuItem showLog;
    //
    private Button chooseHTMLFile;
    private TextField locationOfHTML;
    private WebEngine htmlPreview;
    //
    private Button saveCSV;
    private Button savexlSX;
    private TextField locationOfCSV;
    private TextArea csvPreview;
    //
    private Parser parser;
    //private CSVProcessor csvProcessor;
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

   private void close(WindowEvent event){
       if (logWindow != null) {

           logWindow.close();
       }
   }


    private void setUpUi() throws IOException {
        //setUpUi
        FXMLLoader loader = new FXMLLoader();
        URL url = this.getClass().getResource("ui.fxml");
        loader.setLocation(url);
        SplitPane rootLayout = loader.load();
        stage.setResizable(false);
        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
        stage.show();
    }


    private void obtainUiElements() {
        Scene sc = stage.getScene();
        //menu
        MenuBar bar = (MenuBar) sc.lookup("#bar");
        ObservableList<MenuItem> menuItems = bar.getMenus().get(0).getItems();
        about = menuItems.get(0);
        showLog = menuItems.get(1);
        //top pane
        chooseHTMLFile = (Button) sc.lookup("#choosehtml");
        locationOfHTML = (TextField) sc.lookup("#htmlpath");
        htmlPreview = ((WebView) sc.lookup("#webview")).getEngine();
        //bottom pane
        saveCSV = (Button) sc.lookup("#savecsv");
        savexlSX = (Button) sc.lookup("#savexlsx");
        locationOfCSV = (TextField) sc.lookup("#csvpath");
        csvPreview = (TextArea) sc.lookup("#csvpreview");
    }

    private void setUpFunctionality() {

        //let the user choose a file and set the path into the text field and display it as preview and convert it in bacground
        chooseHTMLFile.setOnAction(actionEvent -> chooseHTMLFileAction());
        //let the user choose the location on where to write the file
        savexlSX.setOnAction(event -> saveAction(new XLXSProcessor()));
        saveCSV.setOnAction(actionEvent -> saveAction(new CSVProcessor()));
        showLog.setOnAction(actionEvent -> openLogWindow());
    }

    private void openLogWindow() {
        CustomLogger.getInstance().log("Opened log window");

        if (logWindow == null) {
            logWindow = new LogWindow();
            try {
                logWindow.start(new Stage());
            } catch (Exception e) {
                CustomLogger.getInstance().log(e);

            }
        }
    }

    private void convertAction() {
        schneidplan = parser.parseSchneidplan(locationOfHTML.getText());
        CSVProcessor proc = new CSVProcessor();
        csvPreview.setText(proc.writeToString(schneidplan));
        CustomLogger.getInstance().log(String.format("Schneidplan %s konvertiert",locationOfHTML.getText()));

    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR, "Der Schneidplan muss zuerst konvertiert werden!");
        alert.show();
    }


    private void saveAction(Processor processor) {
        if (schneidplan != null) {
            String path = openFileChooser(processor.getFileExtensionName(), processor.getFileExtension(), FileActionType.SAVE);
            locationOfCSV.setText(path);
            processor.processAndWrite(schneidplan, path);
            CustomLogger.getInstance().log(String.format("Schneidplan nach %s geschrieben",path));

        }else{
            CustomLogger.getInstance().log("Kein Schneidplan vorhanden. Input HTML wahrscheinlich fehlerhaft");

        }
    }

    private void chooseHTMLFileAction() {
        String path = openFileChooser("HTML", "*.htm*", FileActionType.OPEN);
        if (path != null) {
            locationOfHTML.setText(path);

            File file = new File(path);
            URL url = null;
            try {
                htmlPreview.load(file.toURI().toURL().toString());
            } catch (MalformedURLException e) {
                CustomLogger.getInstance().log(e);

            }
            CustomLogger.getInstance().log(String.format("HTML File aus %s geladen",locationOfHTML.getText()));

            convertAction();
        }


    }



    private String openFileChooser(String showType, String filetype, FileActionType type) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(showType, filetype);
        fileChooser.getExtensionFilters().add(extFilter);

        switch (type) {
            case OPEN:
                return fileChooser.showOpenDialog(stage).getAbsolutePath();

            case SAVE:
                return fileChooser.showSaveDialog(stage).getAbsolutePath();

        }
        return null;
    }


}
