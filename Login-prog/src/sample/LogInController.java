package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static sample.DbUtils.changeScene;

public class LogInController implements Initializable {


    @FXML
    private Button button_logout;

    @FXML
    private static Label label_thank;
    @FXML

    private static Label label_fav_movie;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                  DbUtils.changeScene(actionEvent, "Login.fxml", "Log in!", null,null);
            }
        });
    }
    public static void setUserInformation(String name, String movie){
        label_thank.setText("Thanks "+ name + "for loged in");
        label_fav_movie.setText("The movie for you is " + movie);
    }
}
