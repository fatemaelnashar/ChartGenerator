package com.exercise;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

public class ChartController implements Initializable {
    @FXML
    private Stage primaryStage;
    @FXML
    private TextField filePathField;
    @FXML
    private Pane selectFile;
    @FXML
    private AnchorPane pane;
    @FXML
    private CategoryAxis xAxis ;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private BarChart bc;
    @FXML
    private Label alert;
    @FXML
    private Hyperlink back;
    @FXML
    private GridPane chartGrid;

    private ArrayList<String> columns = new ArrayList<>();
    private ArrayList<RowDataModel> rows = new ArrayList<>();

    private String filePath="";
    private boolean headerLine;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane.getChildren().removeAll(chartGrid);
        headerLine=false;
    }

    private void mapper(String filePath) {
        try {
            Scanner scan = new Scanner(new File(filePath));
            while (scan.hasNext()) {

                //read each line and split by comma
                String curLine = scan.nextLine();
                String[] splitted = curLine.split(",");

                if (headerLine == false) {
                    //read column names from first line
                    for (int i = 0; i < splitted.length; i++) {
                        columns.add(splitted[i]);
                    }
                    headerLine = true;
                } else {
                    //read row values into row datamodel
                    RowDataModel row = new RowDataModel();
                    for (int i = 0; i < splitted.length; i++) {
                        row.addValue(Integer.parseInt(splitted[i]));
                    }
                    rows.add(row);
                }
            }

            scan.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void drawChart() {

        bc.setTitle("Company Statistics");
        xAxis.setLabel(columns.get(0));

        for (int i = 1; i < columns.size(); i++) {
            XYChart.Series series = new XYChart.Series();
            series.setName(columns.get(i)); //add column names from array as category names for barchart
            for (int j = 0; j < rows.size(); j++) {
                //get current category value for each year
                series.getData().add(new XYChart.Data(rows.get(j).getValues().get(0).toString(), rows.get(j).getValues().get
                        (i)));
            }
            bc.getData().add(series);
        }
    }

    @FXML
    private void browse(ActionEvent e) {
        primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv") //filter to choose CSV files only
        );
        File file = fileChooser.showOpenDialog(primaryStage); //show file chooser
        if (file != null) {
            filePath = file.getPath();
            filePathField.setText(filePath);  //add chosen file path to the text field
        }
    }

    @FXML
    private void Select(ActionEvent e) {
        if(filePathField.getText().trim()==null || filePathField.getText().trim().isEmpty()) //check if a file is selected
        {
            alert("Please select a file first.");
        }
        else
        {
            File file = new File(filePath);
            if(!file.exists()) //check if file exists
            {
                alert("The selected file doesn't exist.");
            }
            else {
                if(!FilenameUtils.getExtension(filePath).equals("csv")) //check file is CSV file
                {
                    alert("Only CSV files are supported");

                }
                else
                {
                    pane.getChildren().removeAll(selectFile);
                    mapper(filePath); //read and parse csv file
                    drawChart(); //generate chart
                    pane.getChildren().addAll(chartGrid);
                    back.setVisible(true);

                }
            }
        }
    }

    @FXML
    private void back(ActionEvent actionEvent) {
        //reset arrays, barchart and set headerLine to false
        columns.clear();
        rows.clear();
        headerLine=false;
        bc.getData().clear();
        pane.getChildren().removeAll(chartGrid);
        back.setVisible(false);
        pane.getChildren().add(selectFile);
    }

    private void alert(String message) {
        alert.setText(message);
        alert.setVisible(true);
    }


}
