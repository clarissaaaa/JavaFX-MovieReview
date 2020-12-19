package sample;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databasename = "movierev";
        String databaseUser = "root";
        String databasePassword = "password";
        String url = "jdbc:mysql://localhost/" + databasename;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        }catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }

}
