package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button button_login;
    @FXML
    private TextField text_username;
    @FXML
    private TextField text_password;
    @FXML
    private  Button button_click_here;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         button_login.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 DbUtils.loginUser(actionEvent, text_username.getText(), text_password.getText());
             }
         });
         button_click_here.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 DbUtils.changeScene(actionEvent, "sample.fxml","Sign in", null, null);
             }
         });
    }
}
