package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInFormController implements Initializable {

    //Method to switch scenes

    Stage stage;
    Parent scene;

    /**
     * Changes the scene based on a button event.
     * @param event The button event that triggers the change of scene.
     * @param scenestring The location of the new scene to be displayed.
     * @throws IOException
     */
    public void ChangeScene(ActionEvent event, String scenestring) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(scenestring));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //fx id's for form
    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField userNameTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


}
