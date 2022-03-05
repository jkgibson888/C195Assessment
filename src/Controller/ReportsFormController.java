package Controller;

import DAO.AppointmentDaoImpl;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;

public class ReportsFormController implements Initializable {

    ObservableList<String> allTypes = FXCollections.observableArrayList();
    ObservableList<String> months = FXCollections.observableArrayList();
    ObservableList<Appointment> allAppointments = AppointmentDaoImpl.getAllAppointments();

    @FXML
    private ToggleGroup appointment;

    @FXML
    private TableView<?> appointmentTableView;

    @FXML
    private ComboBox<?> contactCombo;

    @FXML
    private TextField countTextField;

    @FXML
    private TableColumn<?, ?> customerIdCol;

    @FXML
    private TableColumn<?, ?> endCol;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> startCol;

    @FXML
    private ComboBox<String> stringCombo;

    @FXML
    private TableColumn<?, ?> titleCol;

    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private RadioButton monthRB;

    @FXML
    private RadioButton typeRB;

    public ReportsFormController() throws Exception {
    }

    @FXML
    void monthRB(ActionEvent event) {
        stringCombo.getItems().clear();

        stringCombo.setPromptText("Choose Month");

        //populate month combo
        for (int m = 1; m < 13; ++m) {
            stringCombo.getItems().add(Month.of(m).toString());
        }

    }

    @FXML
    void selectContactCombo(ActionEvent event) {

    }


    @FXML
    void stringSelect(ActionEvent event) {

        int count = 0;

        if(monthRB.isSelected()){

            System.out.println(Month.valueOf(stringCombo.getSelectionModel().getSelectedItem()));

            for(Appointment app: allAppointments){

                System.out.println(app.getStartTime().toLocalDateTime().toLocalDate().getMonth());
                if(app.getStartTime().toLocalDateTime().toLocalDate().getMonth().equals(Month.valueOf(stringCombo.getSelectionModel().getSelectedItem()))){
                    count += 1;
                }
            }

            countTextField.setText(String.valueOf(count));

        }

    }

    @FXML
    void typeRB(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}
