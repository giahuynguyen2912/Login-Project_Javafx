package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javax.imageio.IIOException;
import java.io.IOException;
import java.sql.*;

public class DbUtils {
    public static void changeScene(ActionEvent actionEvent, String fxmlFile, String title, String username, String movie) {
        Parent root = null;
        if(username != null && movie != null){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(DbUtils.class.getResource(fxmlFile));
                root = fxmlLoader.load();// fehler meldung hier
                LogInController logInController = fxmlLoader.getController();
                logInController.setUserInformation(username, movie);
            } catch (IIOException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try{
                root = FXMLLoader.load(DbUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 600,400));
            stage.show();
        }
    }
    public static  void signUpUser(ActionEvent actionEvent, String username, String password, String movie){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckAvailable = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login-prog",
                    "root","GiaHuy291296");
            psCheckAvailable = connection.prepareStatement("SELECT * FROM user WHERE name = ?" );
            //check if username is already available
            psCheckAvailable.setString(1, username);
            resultSet = psCheckAvailable.executeQuery();
            if(resultSet.isBeforeFirst()){
                System.out.println("username has been taken!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("please chose other Username");
                alert.show();
            }else{
                psInsert = connection.prepareStatement("INSERT INTO user (name, password, movie VALUES (?, ?, ?))");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, movie);
                psInsert.executeUpdate();
                changeScene(actionEvent, "Login.fxml","Thanks", username, movie);
            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckAvailable != null){
                try{
                    psCheckAvailable.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try{
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static  void loginUser(ActionEvent actionEvent, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login-prog",
                    "root","GiaHuy291296");
            preparedStatement = connection.prepareStatement("SELECT password, movie FROM user WHERE name = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()){
                System.out.println("User is not available");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("please try again or create an account");
                alert.show();
            }else{
                while(resultSet.next()){
                    String checkPass = resultSet.getString("password");
                    String checkMovie = resultSet.getString("movie");
                    if(checkPass.equals(password)){
                        changeScene(actionEvent,"Login.fxml", "Thanks", username, checkMovie);
                    }else{
                        System.out.println("Wrong password");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("please try again");
                        alert.show();
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
