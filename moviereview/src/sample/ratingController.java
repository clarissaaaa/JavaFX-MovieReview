package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ratingController implements Initializable{
    @FXML
    private Button closeButton;
    @FXML
    private Button saveButton;
    @FXML
    private ImageView starImageView;
    @FXML
    private TextField rateTextField;
    @FXML
    private TextArea reviewTextArea;
    @FXML
    private Label rating;
    @FXML
    private Button updateButton;

    //All user review table
    @FXML
    private TableView<userReviewTable> reviewTable;
    @FXML
    private TableColumn<userReviewTable, String> userColumn;
    @FXML
    private TableColumn<userReviewTable, String> reviewColumn;
    @FXML
    private TableColumn<userReviewTable, String> ratingColumn;
    @FXML
    private Label reviewMessage;

    ObservableList<userReviewTable> revlist = FXCollections.observableArrayList();

    //User's personal review and rating table
    @FXML
    private TableView<userReviewTable> personalTable;
    @FXML
    private TableColumn<userReviewTable, String> personalReview;
    @FXML
    private TableColumn<userReviewTable, String> personalRating;

    private ObservableList<tableModel> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File starFile = new File("Image/star.png");
        Image starImage = new Image(starFile.toURI().toString());
        starImageView.setImage(starImage);
        populateTableView();
        populatePersonalTableView();
        setAverage();
    }

    //Get movieid (PK, FK)
    public Integer setMovieid(Integer movieid){
        return movieid;
    }

    //Get username (PK, FK)
    public String setUsername(String username){
        return username;
    }

    //Display average of user's rating
    private void setAverage() {
        String query = "SELECT CAST(AVG(userRating) AS decimal (4,2)) AS average FROM review WHERE review.movieid = " + setMovieid(movielistController.getInstance().movieid());
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            ResultSet result = connectDB.createStatement().executeQuery(query);

            while(result.next()) {
                rating.setText(result.getString("average"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Display all user review and rating
    public void populateTableView() {
        try {
            revlist = FXCollections.observableArrayList();
            String query = "SELECT review.username, userReview, userRating FROM review INNER JOIN member ON member.username = review.username WHERE review.movieid = "+ setMovieid(movielistController.getInstance().movieid());

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            ResultSet result = connectDB.createStatement().executeQuery(query);

            while (result.next()) {
                userReviewTable table1 = new userReviewTable();
                table1.setUsername(result.getString("username"));
                table1.setReview(result.getString("userReview"));
                table1.setRating(result.getString("userRating"));

                revlist.add(table1);
            }

            userColumn.setCellValueFactory(new PropertyValueFactory<userReviewTable, String>("username"));
            reviewColumn.setCellValueFactory(new PropertyValueFactory<userReviewTable, String>("review"));
            ratingColumn.setCellValueFactory(new PropertyValueFactory<userReviewTable, String>("rating"));
            reviewTable.setItems(revlist);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Display user's personal review and rating table
    private void populatePersonalTableView() {
        try {
            revlist = FXCollections.observableArrayList();
            String query = "SELECT userReview, userRating FROM review INNER JOIN movie ON movie.movieid = review.movieid WHERE review.username = '" + setUsername(loginController.getInstance().username()) + "'" + "AND review.movieid = " + setMovieid(movielistController.getInstance().movieid());
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            ResultSet result = connectDB.createStatement().executeQuery(query);

            while (result.next()) {
                userReviewTable table2 = new userReviewTable();
                table2.setReview(result.getString("userReview"));
                table2.setRating(result.getString("userRating"));

                revlist.add(table2);
            }

            personalReview.setCellValueFactory(new PropertyValueFactory<userReviewTable, String>("review"));
            personalRating.setCellValueFactory(new PropertyValueFactory<userReviewTable, String>("rating"));
            personalTable.setItems(revlist);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Close button
    public void closeButtonOnAction(ActionEvent event) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            backToMovielist();
            stage.close();
    }

    //Save button
    public void saveButtonOnAction(ActionEvent event){
        if (reviewTextArea.getText().isBlank() == false && rateTextField.getText().isBlank() == false) {
            Stage stage = (Stage) saveButton.getScene().getWindow();
            saveReview();
        } else {
            reviewMessage.setTextFill(Color.RED);
            reviewMessage.setText("Please enter a review and rating!");
        }
    }

    //Update button
    public void updateButtonOnAction(ActionEvent event) {
        if (reviewTextArea.getText().isBlank() == false && rateTextField.getText().isBlank() == false) {
            Stage stage = (Stage) updateButton.getScene().getWindow();
            updateReview();
        } else {
            reviewMessage.setTextFill(Color.RED);
            reviewMessage.setText("Please enter a review and rating!");
        }
    }

    //Close button -> go back to movie list
    public void backToMovielist(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("movielist.fxml"));
            Stage movielistStage = new Stage();
            movielistStage.initStyle(StageStyle.UNDECORATED);
            movielistStage.setScene(new Scene(root, 600, 400));
            movielistStage.show();

        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    //Save button -> save review and rating to database
    public void saveReview(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String userReview = reviewTextArea.getText();
        String userRating = rateTextField.getText();

        String insertFields = "INSERT INTO review (review.movieid, review.username, userReview, userRating) VALUES (";
        String insertValues = setMovieid(movielistController.getInstance().movieid()) + ",'" + setUsername(loginController.getInstance().username()) + "','" +userReview + "','" + userRating + "')";
        String insertToRegister = insertFields + insertValues;

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            reviewMessage.setText("Review successfully posted!");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //Update button -> update the database with new inputted value
    public void updateReview() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String userReview = reviewTextArea.getText();
        String userRating = rateTextField.getText();

        String insertFields = "UPDATE review SET userReview ='" + userReview + "', userRating =" + userRating;
        String insertValues = "WHERE username = '" + setUsername(loginController.getInstance().username()) + "' AND movieid= " + setMovieid(movielistController.getInstance().movieid());
        String insertToRegister = insertFields + insertValues;
        //UPDATE review SET userReview = 'very cool ogre', userRating = 6.9 WHERE movieid = 2 AND username = 'jeff';
        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            reviewMessage.setText("Review successfully updated!");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
