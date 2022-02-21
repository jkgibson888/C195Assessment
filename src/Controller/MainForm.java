package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainForm {

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

    @FXML
    void appointmentBtnPressed(ActionEvent event) throws IOException {



    }

    @FXML
    void customerBtnPressed(ActionEvent event) throws IOException {

        ChangeScene(event, "/View/CustomerForm.fxml");

    }

    @FXML
    void logOutBtn(ActionEvent event) throws IOException {

        ChangeScene(event, "/View/LogInForm.fxml");

    }

}
