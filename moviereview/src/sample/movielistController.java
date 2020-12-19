package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class movielistController implements Initializable {

    @FXML
    private TableColumn<tableModel, String> movieColumn;
    @FXML
    private TableColumn<tableModel, String> descColumn;
    @FXML
    private TableColumn<tableModel, Integer> movieidColumn;
    @FXML
    private Button rateButton;
    @FXML
    private TableView<tableModel> table;

    private ObservableList<tableModel> list;

    //Class instance to get movieid value
    private static movielistController instance;

    public movielistController(){
        instance = this;
    }

    public Integer movieid (){
        tableModel tableModel = table.getSelectionModel().getSelectedItem();
        return tableModel.getMovieid();
    }
    public static movielistController getInstance(){
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTableView();
    }

    //Display movie list table
    private void populateTableView() {
        try {
            list = FXCollections.observableArrayList();
            String query = "SELECT * FROM movie";

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            ResultSet result = connectDB.createStatement().executeQuery(query);
            while (result.next()) {
                tableModel table = new tableModel();
                table.setMovieid(result.getInt("movieid"));
                table.setTitle(result.getString("title"));
                table.setDesc(result.getString("moviedesc"));

                list.add(table);
            }
            movieidColumn.setCellValueFactory(new PropertyValueFactory<tableModel, Integer>("movieid"));
            movieColumn.setCellValueFactory(new PropertyValueFactory<tableModel, String>("title"));
            descColumn.setCellValueFactory(new PropertyValueFactory<tableModel, String>("desc"));

            table.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Rate button
    public void rateButtonOnAction (ActionEvent event){
        Stage stage = (Stage) rateButton.getScene().getWindow();
        goToRating();
        stage.close();
    }

    //Rate button -> go to rating page
    public void goToRating(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("rating.fxml"));
            Stage ratingStage = new Stage();
            ratingStage.initStyle(StageStyle.UNDECORATED);
            ratingStage.setScene(new Scene(root, 800, 750));
            ratingStage.show();

        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    //Specify which movieid
    public Integer clickColumn(MouseEvent event) {
        tableModel tableModel = table.getSelectionModel().getSelectedItem();
        return tableModel.getMovieid();
    }
}
