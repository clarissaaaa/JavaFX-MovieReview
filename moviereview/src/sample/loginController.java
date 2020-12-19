package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class loginController {
    @FXML
    private Label loginMessage;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    //Class instance to get username value
    private static loginController instance;
    public loginController(){
        instance = this;
    }

    public static loginController getInstance(){
        return instance;
    }

    public String username(){
        return usernameTextField.getText();
    }

    //Login button
    public void loginButtonOnAction(ActionEvent event) {
        if (usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank()== false) {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            validateLogin();
            stage.close();
        } else {
            loginMessage.setTextFill(Color.RED);
            loginMessage.setText("Please enter username and password");
        }
    }

    //Register button
    public void registerButtonOnAction(ActionEvent event){
        Stage stage = (Stage) registerButton.getScene().getWindow();
        createAccountForm();
        stage.close();
    }

    //Login validation
    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM member WHERE username = '" + usernameTextField.getText() + "' AND password ='" + passwordTextField.getText() + "'" ;

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                //Valid login username and password
                if(queryResult.getInt(1) == 1){
                    //Proceed to movie list
                    goToMovielist();
                } else {
                    //Invalid login username and password
                    loginMessage.setText("Invalid login. Please register or try again");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //
    public void createAccountForm(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();

        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    //Login success -> go to movie list page
    public void goToMovielist(){
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
}

