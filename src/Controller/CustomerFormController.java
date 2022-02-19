package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerFormController {

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
    private TableColumn<?, ?> addressCol;

    @FXML
    private TableView<?> appointmentTableView;

    @FXML
    private TableView<?> customerTableView;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> phoneCol;

    @FXML
    private TableColumn<?, ?> startCol;

    @FXML
    private TableColumn<?, ?> stopCol;

    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    void addAppointmentBtn(ActionEvent event) {

    }

    @FXML
    void addCustomerBtn(ActionEvent event) {

    }

    @FXML
    void modifyAppointmentBtn(ActionEvent event) {

    }

    @FXML
    void modifyCustomerBtn(ActionEvent event) {

    }

    @FXML
    void returnToMainBtn(ActionEvent event) throws IOException {

        ChangeScene(event, "/View/MainForm.fxml");

    }

}