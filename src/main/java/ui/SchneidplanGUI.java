package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Schneidplan;
import processing.CSVProcessor;
import processing.Parser;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SchneidplanGUI extends Application {
    private Stage stage;
    private SplitPane rootLayout;
    //
    private Button chooseHTMLFile;
    private TextField locationOfHTML;
    private Button convert;
    private WebView htmlPreview;
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
        rootLayout = loader.load();
        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
        stage.show();
    }


    private void setUpBusinessLogic() {
        parser = new Parser();
        csvProcessor = new CSVProcessor();
    }

    private void obtainUiElements() {
        //top pane
        GridPane importGrid = (GridPane) ((AnchorPane) rootLayout.getItems().get(0)).getChildren().get(0);
        chooseHTMLFile = (Button) importGrid.getChildren().get(0);
        locationOfHTML = (TextField) importGrid.getChildren().get(1);
        htmlPreview = (WebView) importGrid.getChildren().get(2);
        convert= (Button) importGrid.getChildren().get(3);
        //bottom pane
        GridPane exportGrid = (GridPane) ((AnchorPane) rootLayout.getItems().get(1)).getChildren().get(0);
        saveCSV = (Button) exportGrid.getChildren().get(0);
        locationOfCSV = (TextField) exportGrid.getChildren().get(1);
        csvPreview = (TextArea) exportGrid.getChildren().get(2);
    }

    private void setUpFunctionality() {
        //load the document and display the conversion in the csv window
        convert.setOnAction(actionEvent -> convert());
        //let the user choose a file and set the path into the text field and display it as preview
        chooseHTMLFile.setOnAction(actionEvent -> {
            String path=openFileChooser("HTML","*.htm",FileActionType.OPEN);
             locationOfHTML.setText(path);
          htmlPreview.getEngine().load(path);
        });
        //let the user choose the location oh where to write the file
        saveCSV.setOnAction(actionEvent -> {
            String path=openFileChooser("CSV","*.csv", FileActionType.SAVE);
            locationOfCSV.setText(path);
            csvProcessor.writeToFile(schneidplan,path);
        });
    }

    private void convert(){
        schneidplan=parser.parseSchneidplan(locationOfHTML.getText());
        showCSVPreview();
    }
    private void showCSVPreview() {
        csvPreview.setText(csvProcessor.writeToString(schneidplan));
    }
    private String openFileChooser(String showType, String filetype,FileActionType type){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(showType, filetype);
        fileChooser.getExtensionFilters().add(extFilter);

        switch (type){
            case OPEN:
               return fileChooser.showOpenDialog(stage).getAbsolutePath();

            case SAVE:
                return fileChooser.showSaveDialog(stage).getAbsolutePath();

        }
        return null;
    }

}
