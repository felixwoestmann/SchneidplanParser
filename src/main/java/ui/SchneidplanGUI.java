package ui;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Schneidplan;
import processing.CSVProcessor;
import processing.Parser;

public class SchneidplanGUI extends Application {

  private Stage stage;
  //
  private Button chooseHTMLFile;
  private TextField locationOfHTML;
  private WebEngine htmlPreview;
  //
  private Button saveCSV;
  private TextField locationOfCSV;
  private TextArea csvPreview;
  //
  private Parser parser;
  private CSVProcessor csvProcessor;
  private Schneidplan schneidplan;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    this.stage = stage;
    try {
      setUpUi();
    } catch (IOException e) {
      e.printStackTrace();
    }
    obtainUiElements();
    setUpBusinessLogic();
    setUpFunctionality();

  }


  private void setUpUi() throws IOException {
    //setUpUi
    FXMLLoader loader = new FXMLLoader();
    URL url = this.getClass().getResource("ui.fxml");
    loader.setLocation(url);
    SplitPane rootLayout = loader.load();
    Scene scene = new Scene(rootLayout);
    stage.setScene(scene);
    stage.show();
  }


  private void setUpBusinessLogic() {
    parser = new Parser();
    csvProcessor = new CSVProcessor();
  }

  private void obtainUiElements() {
    chooseHTMLFile = (Button) stage.getScene().lookup("#choosehtml");
    locationOfHTML = (TextField) stage.getScene().lookup("#htmlpath");
    htmlPreview = ((WebView) stage.getScene().lookup("#webview")).getEngine();
    //bottom pane
    saveCSV = (Button) stage.getScene().lookup("#savecsv");
    locationOfCSV = (TextField) stage.getScene().lookup("#csvpath");
    csvPreview = (TextArea) stage.getScene().lookup("#csvpreview");
  }

  private void setUpFunctionality() {

    //let the user choose a file and set the path into the text field and display it as preview and convert it in bacground
    chooseHTMLFile.setOnAction(actionEvent -> chooseHTMLFileAction());
    //let the user choose the location oh where to write the file
    saveCSV.setOnAction(actionEvent -> saveCSVAction());
  }

  private void convertAction() {
    schneidplan = parser.parseSchneidplan(locationOfHTML.getText());
    csvPreview.setText(csvProcessor.writeToString(schneidplan));
  }

  private void showError(String message) {
    Alert alert = new Alert(AlertType.ERROR, "Der Schneidplan muss zuerst konvertiert werden!");
    alert.show();
  }

  private void saveCSVAction() {
    String path = openFileChooser("CSV", "*.csv", FileActionType.SAVE);
    locationOfCSV.setText(path);
    csvProcessor.writeToFile(schneidplan, path);
  }

  private void chooseHTMLFileAction() {
    String path = openFileChooser("HTML", "*.htm", FileActionType.OPEN);
    if (path != null) {
      locationOfHTML.setText(path);

      File file = new File(path);
      URL url = null;
      try {
        htmlPreview.load(file.toURI().toURL().toString());
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
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
