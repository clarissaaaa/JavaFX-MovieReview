package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.Connection;
import java.sql.Statement;

public class registerController {

    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label registerMessage;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;

    public void loginButtonOnAction(ActionEvent event){
        Stage stage = (Stage) loginButton.getScene().getWindow();
        backToLogin();
        stage.close();
    }


    public void registerButtonOnAction(ActionEvent event){
        if (firstnameTextField.getText().isBlank() == false && lastnameTextField.getText().isBlank() == false && usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank()== false) {
            Stage stage = (Stage) registerButton.getScene().getWindow();
            registerUser();
        } else {
            registerMessage.setTextFill(Color.RED);
            registerMessage.setText("Please enter all of the information!");
        }
    }

    public void registerUser(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        String insertFields = "INSERT INTO member(firstname, lastname, username, password) VALUES ('";
        String insertValues = firstname + "','" + lastname + "','" + username + "','" + password + "')";
        String insertToRegister = insertFields + insertValues;

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            registerMessage.setText("User has been registered successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void backToLogin() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage loginStage = new Stage();
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.setScene(new Scene(root, 600, 400));
            loginStage.show();

        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}
