package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private Button button_create;
    @FXML
    private Button button_login;
    @FXML
    private RadioButton rb_naruto;
    @FXML
    private  RadioButton rb_none;
    @FXML
    private TextField text_username;
    @FXML
    private TextField text_password;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        rb_naruto.setToggleGroup(toggleGroup);
        rb_none.setToggleGroup(toggleGroup);
        rb_naruto.setSelected(true);
        button_create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
                if(!text_username.getText().trim().isEmpty() && !text_password.getText().trim().isEmpty()){
                    DbUtils.signUpUser(actionEvent, text_username.getText(), text_password.getText(), toggleName);
                }else{
                    System.out.println("you need to fill all of the Information to proceed");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("you need to fill all the blank box");
                    alert.show();
                }
            }
        });
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DbUtils.changeScene(actionEvent, "Signin.fxml", "log in!", null, null);
            }
        });
    }
}
