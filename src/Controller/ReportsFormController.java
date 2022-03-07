package Controller;

import DAO.AppointmentDaoImpl;
import DAO.ContactDaoImp;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.MonthTypeApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;

public class ReportsFormController implements Initializable {

    public ReportsFormController() throws Exception {
    }

    public void PopulateAppt(ObservableList<Appointment> tableList, TableView<Appointment> tableView, TableColumn<Appointment,Integer> Column1, TableColumn<Appointment, String> Column2, TableColumn<Appointment, String> Column3, TableColumn<Appointment, String> Column4, TableColumn<Appointment, String> Column5, TableColumn<Appointment, String> Column6, TableColumn<Appointment, Integer> Column7){

        tableView.setItems(tableList);
        Column1.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        Column2.setCellValueFactory(new PropertyValueFactory<>("title"));
        Column3.setCellValueFactory(new PropertyValueFactory<>("type"));
        Column4.setCellValueFactory(new PropertyValueFactory<>("description"));
        Column5.setCellValueFactory(new PropertyValueFactory<>("start"));
        Column6.setCellValueFactory(new PropertyValueFactory<>("stop"));
        Column7.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }

    public void PopulateMonthTypeTable(ObservableList<MonthTypeApp> tableList, TableView<MonthTypeApp> tableView, TableColumn<MonthTypeApp,String> Column1, TableColumn<MonthTypeApp, String> Column2, TableColumn<MonthTypeApp, String> Column3){

        tableView.setItems(tableList);
        Column1.setCellValueFactory(new PropertyValueFactory<>("month"));
        Column2.setCellValueFactory(new PropertyValueFactory<>("type"));
        Column3.setCellValueFactory(new PropertyValueFactory<>("count"));

    }

    ObservableList<MonthTypeApp> allMTAppointments = AppointmentDaoImpl.getMonthTypeReport();
    ObservableList<Appointment> allAppointments = AppointmentDaoImpl.getAllAppointments();
    ObservableList<Contact> allContacts = ContactDaoImp.getAllContacts();

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private ComboBox<Contact> contactCombo;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, String> endCol;

    @FXML
    private TableColumn<Appointment, Integer> idCol;

    @FXML
    private TableColumn<Appointment, String> startCol;

    @FXML
    private TextArea textArea;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableView<MonthTypeApp> monthTypeTable;

    @FXML
    private TableColumn<MonthTypeApp, String> monthCol;

    @FXML
    private TableColumn<MonthTypeApp, String> monthTypeCol;

    @FXML
    private TableColumn<MonthTypeApp, String> countCol;

    @FXML
    void selectContactCombo(ActionEvent event) {

        //get contact from combo box
        Contact contact = contactCombo.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> contactApp = FXCollections.observableArrayList();

        for(Appointment app: allAppointments){
           if(app.getContactId() == contact.getContactId()){
               contactApp.add(app);
           }
        }

        PopulateAppt(contactApp, appointmentTableView, idCol, titleCol, typeCol, descriptionCol, startCol, endCol, customerIdCol);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //populate month type table
        PopulateMonthTypeTable(allMTAppointments, monthTypeTable, monthCol, monthTypeCol, countCol);


        //set contact combo
        contactCombo.setItems(allContacts);
        contactCombo.setPromptText("Select Contact.");

    }
}
