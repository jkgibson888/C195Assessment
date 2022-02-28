package Controller;

import DAO.AppointmentDaoImpl;
import DAO.ContactDaoImp;
import DAO.CustomerDaoImpl;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Utility.Timezone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class AppointmentFormController implements Initializable {

    ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyy HH:mm a");
    //farthest year for the appointments
    private final String LAST_YEAR = "2035";
    private ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private Customer currentCustomer = CustomerFormController.getPassedCustomer();

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

    /**
     * Populates a table with a list of users.
     * @param tableList The observable list to be displayed.
     * @param tableView The specific table view that will be populated.
     * @param Column1 The first column of the table view.
     * @param Column2 The second column of the table view.
     * @param Column3 The third column of the table view.
     */

    //populate a customer table
    public void PopulateTable(ObservableList<Customer> tableList, TableView<Customer> tableView, TableColumn<Customer, String> Column1, TableColumn<Customer, String> Column2, TableColumn<Customer, String> Column3){

        tableView.setItems(tableList);

        Column1.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        Column2.setCellValueFactory(new PropertyValueFactory<>("fullAddress"));
        Column3.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    }

    //populate an appointments table

    public void PopulateAppt(ObservableList<Appointment> tableList, TableView<Appointment> tableView, TableColumn<Appointment,Integer> Column1, TableColumn<Appointment, String> Column2, TableColumn<Appointment, String> Column3, TableColumn<Appointment, String> Column4, TableColumn<Appointment, String> Column5, TableColumn<Appointment, Timestamp> Column6, TableColumn<Appointment, Timestamp> Column7){

        tableView.setItems(tableList);

        Column1.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        Column2.setCellValueFactory(new PropertyValueFactory<>("title"));
        Column3.setCellValueFactory(new PropertyValueFactory<>("description"));
        Column4.setCellValueFactory(new PropertyValueFactory<>("location"));
        Column5.setCellValueFactory(new PropertyValueFactory<>("type"));
        Column6.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        Column7.setCellValueFactory(new PropertyValueFactory<>("endTime"));

    }

    @FXML
    private TextField appIdTextField;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, Timestamp> startCol;

    @FXML
    private TableColumn<Appointment, Timestamp> stopCol;

    @FXML
    private TableColumn<Appointment,Integer> idCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField locationTextField;


    @FXML
    private TextField titleTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private RadioButton endAMRadio;

    @FXML
    private RadioButton endPMRadio;

    @FXML
    private RadioButton startAMRadio;

    @FXML
    private RadioButton startPMRadio;

    @FXML
    private ComboBox<Contact> contactCombo;

    @FXML
    private ComboBox dayCombo;

    @FXML
    private ComboBox<Customer> customerCombo;

    @FXML
    private ComboBox<Month> monthCombo;

    @FXML
    private ComboBox<LocalTime> stopCombo;

    @FXML
    private ComboBox<Year> yearCombo;

    @FXML
    private ComboBox<LocalTime> startCombo;

    @FXML
    private ToggleGroup startTG;
    @FXML
    private ToggleGroup stopTG;

    @FXML
    private TextFlow errorTextFlow;

    //pass the customer to the modify screen
    private static Customer modifyCustomer = null;

    public static Customer getCustomer(){
        return modifyCustomer;
    }

    @FXML
    void addBtn(ActionEvent event) throws Exception {

        //int appId = 0;
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        String type = typeTextField.getText();

        //create string for timestamp for start
        int year = yearCombo.getSelectionModel().getSelectedItem().getValue() - 1900;
        int month = monthCombo.getSelectionModel().getSelectedItem().getValue() - 1;
        int date = Integer.parseInt(dayCombo.getSelectionModel().getSelectedItem().toString());
        int hour = startCombo.getSelectionModel().getSelectedItem().getHour();
        int minute = startCombo.getSelectionModel().getSelectedItem().getMinute();
        int second = 0;
        int nano = 0;

        if(startPMRadio.isSelected() && startCombo.getSelectionModel().getSelectedItem().getHour() >= 1){
            hour = hour + 12;
        }

        Timestamp start = new Timestamp(year, month, date, hour, minute, second, nano);

        //create string for timestamp for end
        int ehour = stopCombo.getSelectionModel().getSelectedItem().getHour();
        int eminute = stopCombo.getSelectionModel().getSelectedItem().getMinute();
        int esecond = 0;
        int enano = 0;

        if(endPMRadio.isSelected() && stopCombo.getSelectionModel().getSelectedItem().getHour() >= 1){
            hour = hour + 12;
        }

        Timestamp end = new Timestamp(year, month, date, ehour, eminute, esecond, enano);

        String createdBy = LogInFormController.getCurrentUser().getUserName();
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        int customerId = customerCombo.getSelectionModel().getSelectedItem().getCustomerId();
        int contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();

        //check if customer combo is still the same selected
        if(customerCombo.getSelectionModel().getSelectedItem() != currentCustomer){

            System.out.println("new customer was selected");
            int changedCustomerId = customerCombo.getSelectionModel().getSelectedItem().getCustomerId();
            Customer changedCustomer = customerCombo.getSelectionModel().getSelectedItem();
            Appointment newAppointment = new Appointment(0, title, description, location, type, start, end, createdBy, createdDate, changedCustomerId, contactId);

            AppointmentDaoImpl.insertAppointment(changedCustomer, newAppointment);
        }
        else {
            Appointment appointment = new Appointment(0, title, description, location, type, start, end, createdBy, createdDate, customerId, contactId);
            AppointmentDaoImpl.insertAppointment(CustomerFormController.getPassedCustomer(), appointment);
        }
        //repopulate table

        ObservableList<Appointment> customerAppointments = AppointmentDaoImpl.getCustomerAppointments(currentCustomer);

        PopulateAppt(customerAppointments, appointmentTableView, idCol, titleCol, descriptionCol, locationCol, typeCol, startCol, stopCol);

    }


    private static Customer customer;


    @FXML
    void deleteBtn(ActionEvent event) throws Exception {

        //get selected appointment from appointment table
        TableView.TableViewSelectionModel<Appointment> selectionModel = appointmentTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Appointment> selectedCustomer = selectionModel.getSelectedItems();
        Appointment appointment = selectedCustomer.get(0);

        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setHeaderText("Confirm");
        deleteAlert.setContentText("Are you sure you wish to delete this appointment?");
        deleteAlert.showAndWait();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Appointment Deleted");
        alert.setTitle("Notice");
        alert.setContentText("Appointment number " + appointment.getAppointmentId() + " : " + appointment.getType());
        alert.showAndWait();

        //delete appointment from database
        AppointmentDaoImpl.deleteAppointment(appointment);

        //repopulate table

        ObservableList<Appointment> customerAppointments = AppointmentDaoImpl.getCustomerAppointments(CustomerFormController.getPassedCustomer());

        PopulateAppt(customerAppointments, appointmentTableView, idCol, titleCol, descriptionCol, locationCol, typeCol, startCol, stopCol);

        //clear form
        titleTextField.clear();
        descriptionTextField.clear();
        locationTextField.clear();
        typeTextField.clear();
        customerCombo.setValue(null);
        contactCombo.setValue(null);
        monthCombo.setValue(null);
        dayCombo.setValue(null);
        yearCombo.setValue(null);
        startAMRadio.setSelected(true);
        endAMRadio.setSelected(true);
        startCombo.setValue(null);
        stopCombo.setValue(null);

    }




    @FXML
    void modifyBtn(ActionEvent event) throws Exception {
        //FIX ME!

        TableView.TableViewSelectionModel<Appointment> selectionModel = appointmentTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Appointment> selectedCustomer = selectionModel.getSelectedItems();
        Appointment appointment = selectedCustomer.get(0);

        int appId = appointment.getAppointmentId();
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        String type = locationTextField.getText();

        //create string for timestamp for start
        int year = yearCombo.getSelectionModel().getSelectedItem().getValue() - 1900;
        int month = monthCombo.getSelectionModel().getSelectedItem().getValue() - 1;
        int date = Integer.parseInt(dayCombo.getSelectionModel().getSelectedItem().toString());
        int hour = startCombo.getSelectionModel().getSelectedItem().getHour();

        if(startPMRadio.isSelected() && startCombo.getSelectionModel().getSelectedItem().getHour() >= 1){
            hour = hour + 12;
        }
        int minute = startCombo.getSelectionModel().getSelectedItem().getMinute();
        int second = 0;
        int nano = 0;
        Timestamp start = new Timestamp(year, month, date, hour, minute, second, nano);

        //create string for timestamp for end
        int ehour = stopCombo.getSelectionModel().getSelectedItem().getHour();
        int eminute = stopCombo.getSelectionModel().getSelectedItem().getMinute();
        int esecond = 0;
        int enano = 0;

        if(endPMRadio.isSelected() && stopCombo.getSelectionModel().getSelectedItem().getHour() >= 1){
            hour = hour + 12;
        }

        Timestamp end = new Timestamp(year, month, date, ehour, eminute, esecond, enano);

        String createdBy = LogInFormController.getCurrentUser().getUserName();
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        int contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();

        //check if customer combo has been changed
        if(customerCombo.getSelectionModel().getSelectedItem() != currentCustomer){

            System.out.println("new customer was selected");
            int customerId = customerCombo.getSelectionModel().getSelectedItem().getCustomerId();
            Customer changedCustomer = customerCombo.getSelectionModel().getSelectedItem();
            Appointment newAppointment = new Appointment(0, title, description, location, type, start, end, createdBy, createdDate, customerId, contactId);

            AppointmentDaoImpl.insertAppointment(changedCustomer, newAppointment);
        }
        else {
            int customerId = customerCombo.getSelectionModel().getSelectedItem().getCustomerId();

            Appointment updatedAppointment = new Appointment(appId, title, description, location, type, start, end, createdBy, createdDate, customerId, contactId);
            AppointmentDaoImpl.updateAppointment(CustomerFormController.getPassedCustomer(), updatedAppointment);
        }
        //repopulate table

        ObservableList<Appointment> customerAppointments = AppointmentDaoImpl.getCustomerAppointments(CustomerFormController.getPassedCustomer());

        PopulateAppt(customerAppointments, appointmentTableView, idCol, titleCol, descriptionCol, locationCol, typeCol, startCol, stopCol);


    }

    @FXML
    void returnToMainBtn(ActionEvent event) throws IOException {

        //return to main form
        ChangeScene(event, "/View/CustomerForm.fxml");

    }

    @FXML
    void selectAppointmentAction(MouseEvent event) {

        TableView.TableViewSelectionModel<Appointment> selectionModel = appointmentTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Appointment> selectedCustomer = selectionModel.getSelectedItems();
        Appointment appointment = selectedCustomer.get(0);

        //set text fields

        appIdTextField.setText(String.valueOf(appointment.getAppointmentId()));
        titleTextField.setText(appointment.getTitle());
        descriptionTextField.setText(appointment.getDescription());
        typeTextField.setText(appointment.getType());
        locationTextField.setText(appointment.getLocation());



        //set contact combo box
        for(Contact contact: allContacts) {
            if(contact.getContactId() == appointment.getContactId()) {
                contactCombo.setValue(contact);
            }
        }

        //set month combo box
        monthCombo.setValue(appointment.getStartTime().toLocalDateTime().getMonth());

        //set year combo box
        yearCombo.setValue(Year.of(Integer.parseInt(appointment.getStartTime().toString().substring(0, 4))));

        //set day combo box
        dayCombo.setValue(Integer.parseInt(appointment.getStartTime().toString().substring(8, 10)));

        //set customer combo
        customerCombo.setValue(currentCustomer);

        if(appointment.getStartTime().toLocalDateTime().getHour() < 12){
            startAMRadio.setSelected(true);
        }
        else{
            startPMRadio.setSelected(true);
            startCombo.getItems().clear();

            LocalTime nstart = LocalTime.NOON;
            LocalTime nend = LocalTime.of(14, 55);
            while (nstart.isBefore(nend.plusSeconds(1))) {

                if(nstart.getHour() < 13){
                    startCombo.getItems().add(nstart);
                    nstart = nstart.plusMinutes(15);
                }
                else {
                    startCombo.getItems().add(nstart.minusHours(12));
                    nstart = nstart.plusMinutes(15);
                }

            }

        }

        if(appointment.getEndTime().toLocalDateTime().getHour() < 12){
            endAMRadio.setSelected(true);
        }
        else{
            endPMRadio.setSelected(true);

            startCombo.getItems().clear();
            LocalTime sstart = LocalTime.NOON;
            LocalTime send = LocalTime.of(14, 55);
            while (sstart.isBefore(send.plusSeconds(1))) {

                if(sstart.getHour() < 13){
                    startCombo.getItems().add(sstart);
                    sstart = sstart.plusMinutes(15);
                }
                else {
                    startCombo.getItems().add(sstart.minusHours(12));
                    sstart = sstart.plusMinutes(15);
                }
            }


            stopCombo.getItems().clear();

            LocalTime start = LocalTime.NOON;
            LocalTime end = LocalTime.of(14, 55);
            while (start.isBefore(end.plusSeconds(1))) {

                if(start.getHour() < 13){
                    stopCombo.getItems().add(start);
                    start = start.plusMinutes(15);
                }
                else {
                    stopCombo.getItems().add(start.minusHours(12));
                    start = start.plusMinutes(15);
                }
            }
        }

        startCombo.setValue(appointment.getStartTime().toLocalDateTime().toLocalTime());
        stopCombo.setValue(appointment.getEndTime().toLocalDateTime().toLocalTime());

    }

    @FXML
    void clearBtn(ActionEvent event) {

        TableView.TableViewSelectionModel <Appointment> selectionModel = appointmentTableView.getSelectionModel();
        selectionModel.clearSelection();

        titleTextField.clear();
        descriptionTextField.clear();
        locationTextField.clear();
        typeTextField.clear();
        customerCombo.setValue(null);
        contactCombo.setValue(null);
        monthCombo.setValue(null);
        dayCombo.setValue(null);
        yearCombo.setValue(null);
        startAMRadio.setSelected(true);
        endAMRadio.setSelected(true);
        startCombo.setValue(null);
        stopCombo.setValue(null);

    }

    @FXML
    void setDayAction(MouseEvent event) {
        errorTextFlow.getChildren().clear();
        dayCombo.getItems().clear();
        Month currentMonth = monthCombo.getSelectionModel().getSelectedItem();
        Year currentYear = yearCombo.getSelectionModel().getSelectedItem();
        if(monthCombo.getSelectionModel().getSelectedItem() != null && yearCombo.getSelectionModel().getSelectedItem() != null) {
            if (currentMonth.getValue() == 1 || currentMonth.getValue() == 3 || currentMonth.getValue() == 5 || currentMonth.getValue() == 7 || currentMonth.getValue() == 8 || currentMonth.getValue() == 10 || currentMonth.getValue() == 12) {
                for (int i = 1; i < 32; ++i) {
                    dayCombo.getItems().add(i);
                }
            } else if (currentMonth.getValue() == 4 || currentMonth.getValue() == 6 || currentMonth.getValue() == 9 || currentMonth.getValue() == 11) {
                for (int i = 1; i < 31; ++i) {
                    dayCombo.getItems().add(i);
                }
            } else if (currentMonth.getValue() == 2 && currentYear.isLeap()) {
                for (int i = 1; i < 30; ++i) {
                    dayCombo.getItems().add(i);
                }

            } else {
                for (int i = 1; i < 29; ++i) {
                    dayCombo.getItems().add(i);
                }
            }
        }
        else{
            Text errorText = new Text("Please choose a month or year \n");
            errorTextFlow.getChildren().add(errorText);
        }
    }

    @FXML
    void setStartCorrectTime(MouseEvent event) {
        if(startAMRadio.isSelected()){
            //startAMRadio.setSelected(true);
            startCombo.setValue(null);
            stopCombo.setValue(null);
            stopCombo.getItems().clear();
            LocalTime initStart = LocalTime.of(8, 0);
            LocalTime initEnd = LocalTime.of(11, 55);
            while (initStart.isBefore(initEnd.plusSeconds(1))) {
                stopCombo.getItems().add(initStart);
                initStart = initStart.plusMinutes(15);
            }

            startCombo.getItems().clear();
            System.out.println("entered start AM.....");
            LocalTime ninitStart = LocalTime.of(8, 0);
            LocalTime ninitEnd = LocalTime.of(11, 50);
            while (ninitStart.isBefore(ninitEnd.plusSeconds(1))) {
                startCombo.getItems().add(ninitStart);
                ninitStart = ninitStart.plusMinutes(15);
            }

        }

        if(startPMRadio.isSelected()){
            endPMRadio.setSelected(true);
            System.out.println("entered start PM.....");
            startCombo.getItems().clear();
            stopCombo.getItems().clear();

            LocalTime start = LocalTime.NOON;
            LocalTime end = LocalTime.of(14, 55);
            while (start.isBefore(end.plusSeconds(1))) {

                if(start.getHour() < 13){
                    startCombo.getItems().add(start);
                    start = start.plusMinutes(15);
                }
                else {
                    startCombo.getItems().add(start.minusHours(12));
                    start = start.plusMinutes(15);
                }

            }

            LocalTime st = LocalTime.NOON;
            LocalTime en = LocalTime.of(14, 55);
            while (st.isBefore(en.plusSeconds(1))) {

                if(st.getHour() < 13){
                    stopCombo.getItems().add(st);
                    st = st.plusMinutes(15);
                }
                else {
                    stopCombo.getItems().add(st.minusHours(12));
                    st = st.plusMinutes(15);
                }

            }

        }
    }

    @FXML
    void setEndCorrectTime(MouseEvent event) {
        if(endAMRadio.isSelected()){
            startAMRadio.setSelected(true);
            startCombo.setValue(null);
            stopCombo.setValue(null);
            stopCombo.getItems().clear();
            LocalTime initStart = LocalTime.of(8, 0);
            LocalTime initEnd = LocalTime.of(11, 55);
            while (initStart.isBefore(initEnd.plusSeconds(1))) {
                stopCombo.getItems().add(initStart);
                initStart = initStart.plusMinutes(15);
            }

            startCombo.getItems().clear();
            System.out.println("entered start AM.....");
            LocalTime ninitStart = LocalTime.of(8, 0);
            LocalTime ninitEnd = LocalTime.of(11, 50);
            while (ninitStart.isBefore(ninitEnd.plusSeconds(1))) {
                startCombo.getItems().add(ninitStart);
                ninitStart = ninitStart.plusMinutes(15);
            }

        }

        if(endPMRadio.isSelected()){
            System.out.println("entered start PM.....");
            stopCombo.getItems().clear();

            LocalTime start = LocalTime.NOON;
            LocalTime end = LocalTime.of(14, 55);
            while (start.isBefore(end.plusSeconds(1))) {

                if(start.getHour() < 13){
                    stopCombo.getItems().add(start);
                    start = start.plusMinutes(15);
                }
                else {
                    stopCombo.getItems().add(start.minusHours(12));
                    start = start.plusMinutes(15);
                }

            }

        }
    }

    private static Customer passedCustomer;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Appointment> customerAppointments = null;

        try {
            customerAppointments = AppointmentDaoImpl.getCustomerAppointments(CustomerFormController.getPassedCustomer());
            allCustomers = CustomerDaoImpl.getAllCustomers();

        } catch (Exception e) {
            e.printStackTrace();
        }

        PopulateAppt(customerAppointments, appointmentTableView, idCol, titleCol, descriptionCol, locationCol, typeCol, startCol, stopCol);

        //get customer and Appointment from customer form

        passedCustomer = AppointmentFormController.getCustomer();

        //populate month combo
        monthCombo.setPromptText("Month");

        for (int m = 1; m < 13; ++m) {
            monthCombo.getItems().add(Month.of(m));
        }

        //populate yearCombo
        yearCombo.setPromptText("Year");

        Year ystart = Year.now();
        Year yend = Year.parse(LAST_YEAR);

        while (ystart.isBefore(yend)) {
            yearCombo.getItems().add(ystart);
            ystart = ystart.plusYears(1);
        }
        yearCombo.setValue(Year.now());

        //populate day colum
        dayCombo.setPromptText("Day");

        for (int d = 1; d < 29; d++) {
            dayCombo.getItems().add(d);
        }

        //populate contact combo


        try {
            allContacts = ContactDaoImp.getAllContacts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        contactCombo.setItems(allContacts);
        contactCombo.setPromptText("Choose contact");


        startAMRadio.setSelected(true);
        endAMRadio.setSelected(true);
        startCombo.setPromptText("Start time");
        stopCombo.setPromptText("Stop time");

        //set customer combo box
        customerCombo.setItems(allCustomers);

        //set Time combo boxes
        startCombo.getItems().clear();
        System.out.println("entered start AM.....");
        LocalTime initStart = LocalTime.of(8, 0);
        LocalTime initEnd = LocalTime.of(11, 55);
        while (initStart.isBefore(initEnd.plusSeconds(1))) {
            startCombo.getItems().add(initStart);
            initStart = initStart.plusMinutes(15);
        }

        stopCombo.getItems().clear();
        LocalTime einitStart = LocalTime.NOON;
        LocalTime einitEnd = LocalTime.of(15, 55);
        while (einitStart.isBefore(einitEnd.plusSeconds(1))) {

            if(einitStart.getHour() < 13){
                stopCombo.getItems().add(einitStart);
                einitStart = einitStart.plusMinutes(15);
            }
            else {
                stopCombo.getItems().add(einitStart.minusHours(12));
                einitStart = einitStart.plusMinutes(15);
            }
        }


        //FIX ME change radio buttons based on time;
    }




}