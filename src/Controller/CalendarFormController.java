package Controller;

import DAO.AppointmentDaoImpl;
import DAO.ContactDaoImp;
import DAO.CustomerDaoImpl;
import DAO.UserDaoImpl;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
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
import java.time.temporal.ChronoField;
import java.util.ResourceBundle;

public class CalendarFormController implements Initializable {

    ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyy HH:mm a");
    //farthest year for the appointments
    private final String LAST_YEAR = "2035";
    private ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private Customer currentCustomer = CustomerFormController.getPassedCustomer();

    //time formatters
    DateTimeFormatter amFormatter = DateTimeFormatter.ofPattern("hh:mm a");
    DateTimeFormatter amReverse = DateTimeFormatter.ofPattern("HH:mm");

    //start and end of business hours
    private static LocalTime businessStart = LocalTime.of(8, 0);
    private static LocalTime businessEnd = LocalTime.of(23, 0);


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

     //populate an appointments table

    public void PopulateAppt(ObservableList<Appointment> tableList, TableView<Appointment> tableView, TableColumn<Appointment, String> Column1, TableColumn<Appointment,Integer> Column2, TableColumn<Appointment, String> Column3, TableColumn<Appointment, String> Column4, TableColumn<Appointment, String> Column5, TableColumn<Appointment, String> Column6, TableColumn<Appointment, String> Column7, TableColumn<Appointment, String> Column8, TableColumn<Appointment, String> Column9, TableColumn<Appointment, String> Column10){

        tableView.setItems(tableList);
        Column1.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        Column2.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        Column3.setCellValueFactory(new PropertyValueFactory<>("title"));
        Column4.setCellValueFactory(new PropertyValueFactory<>("description"));
        Column5.setCellValueFactory(new PropertyValueFactory<>("location"));
        Column6.setCellValueFactory(new PropertyValueFactory<>("type"));
        Column7.setCellValueFactory(new PropertyValueFactory<>("start"));
        Column8.setCellValueFactory(new PropertyValueFactory<>("stop"));
        Column9.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        Column10.setCellValueFactory(new PropertyValueFactory<>("userName"));

    }

    @FXML
    private RadioButton allAppointmentsRB;

    @FXML
    private RadioButton thisMonthAppointmentsRB;

    @FXML
    private RadioButton thisWeekRB;

    @FXML
    private TextField appIdTextField;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, String> customerCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, String> startCol;

    @FXML
    private TableColumn<Appointment, String> stopCol;

    @FXML
    private TableColumn<Appointment,Integer> idCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, String> userCol;

    @FXML
    private TableColumn<Appointment, String> contactCol;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField locationTextField;


    @FXML
    private TextField titleTextField;

    @FXML
    private TextField typeTextField;


    @FXML
    private ComboBox<Contact> contactCombo;

    @FXML
    private ComboBox dayCombo;

    @FXML
    private ComboBox<Customer> customerCombo;

    @FXML
    private ComboBox<Month> monthCombo;

    @FXML
    private ComboBox<String> stopCombo;

    @FXML
    private ComboBox<Year> yearCombo;

    @FXML
    private ComboBox<String> startCombo;

    @FXML
    private ComboBox<User> userCombo;

    @FXML
    private TextFlow errorTextFlow;

    @FXML
    private Button btnToAdd;

    @FXML
    private Button btnToModify;

    //pass the customer to the modify screen
    private static Customer modifyCustomer = null;

    public static Customer getCustomer(){
        return modifyCustomer;
    }

    @FXML
    void allAppointmentsSelected(ActionEvent event) throws Exception {

        appointmentTableView.getItems().clear();

        ObservableList<Appointment> allAppointments= AppointmentDaoImpl.getAllAppointments();

        PopulateAppt(allAppointments, appointmentTableView, customerCol, idCol, titleCol, descriptionCol, locationCol, typeCol, startCol, stopCol, contactCol, userCol);

    }

    @FXML
    void thisMonthSelected(ActionEvent event) throws Exception {

        appointmentTableView.getItems().clear();

        ObservableList<Appointment> allAppointments= AppointmentDaoImpl.getAllAppointments();

        ObservableList<Appointment> thisMonthAppointments = FXCollections.observableArrayList();

        for(Appointment app: allAppointments){

            //System.out.println(app.getStartTime().toLocalDateTime().getMonth());
            //System.out.println(LocalDateTime.now().getMonth());
            if(app.getStartTime().toLocalDateTime().getMonth() == LocalDateTime.now().getMonth()){
                thisMonthAppointments.add(app);
                System.out.println(app.getStartTime().toLocalDateTime().getMonth());
            }
        }

        PopulateAppt(thisMonthAppointments, appointmentTableView, customerCol, idCol, titleCol, descriptionCol, locationCol, typeCol, startCol, stopCol, contactCol, userCol);


    }

    @FXML
    void thisWeekSelected(ActionEvent event) throws Exception {

        appointmentTableView.getItems().clear();

        ObservableList<Appointment> allAppointments= AppointmentDaoImpl.getAllAppointments();

        ObservableList<Appointment> thisWeekAppointments = FXCollections.observableArrayList();

        for(Appointment app: allAppointments){

            //get monday of the current local time week
            LocalDate monday = LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1);
            LocalDate nextMonday = monday.plusDays(7);
            System.out.println(monday);

            //System.out.println(LocalDateTime.now().getMonth());
            if(app.getStartTime().toLocalDateTime().toLocalDate().isAfter(monday) && app.getStartTime().toLocalDateTime().toLocalDate().isBefore(nextMonday)){

                thisWeekAppointments.add(app);
                System.out.println(app.getStartTime().toLocalDateTime().toLocalDate().compareTo(monday));
            }
        }

        PopulateAppt(thisWeekAppointments, appointmentTableView, customerCol, idCol, titleCol, descriptionCol, locationCol, typeCol, startCol, stopCol, contactCol, userCol);

    }

    @FXML
    void addBtn(ActionEvent event) throws Exception {

        //int appId = 0;
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        String type = typeTextField.getText();

        //create string for timestamp for start
        int year = yearCombo.getSelectionModel().getSelectedItem().getValue();
        int month = monthCombo.getSelectionModel().getSelectedItem().getValue();
        int day = Integer.parseInt(dayCombo.getSelectionModel().getSelectedItem().toString());

        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_TIME;
        //format start time for timestamp
        LocalTime reverse = LocalTime.parse(startCombo.getSelectionModel().getSelectedItem(), amFormatter);


        LocalDate ldStart = LocalDate.of(year, month, day);
        LocalDateTime ldtStart = LocalDateTime.of(ldStart, reverse);


        Timestamp start = Timestamp.valueOf(ldtStart);

        //create string for timestamp for end
        LocalTime stopReverse = LocalTime.parse(stopCombo.getSelectionModel().getSelectedItem(), amFormatter);


        //convert to utc for database
        LocalDateTime ldtStop = LocalDateTime.of(ldStart, stopReverse);

        Timestamp stop = Timestamp.valueOf(ldtStop);

        String createdBy = LogInFormController.getCurrentUser().getUserName();
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        int customerId = customerCombo.getSelectionModel().getSelectedItem().getCustomerId();
        int contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();
        int userId = userCombo.getSelectionModel().getSelectedItem().getUserId();


            Appointment appointment = new Appointment(0, title, description, location, type, start, stop, createdBy, createdDate, customerId, contactId, userId);
            AppointmentDaoImpl.insertAppointment(customerCombo.getSelectionModel().getSelectedItem(), appointment);

                //repopulate table

        ObservableList<Appointment> allAppointments = AppointmentDaoImpl.getAllAppointments();

        PopulateAppt(allAppointments, appointmentTableView, customerCol, idCol, titleCol, descriptionCol, locationCol, typeCol, startCol, stopCol, contactCol, userCol);

        //clear form
        titleTextField.clear();
        descriptionTextField.clear();
        locationTextField.clear();
        typeTextField.clear();
        userCombo.setValue(null);
        customerCombo.setValue(null);
        contactCombo.setValue(null);
        monthCombo.setValue(Month.JANUARY);
        dayCombo.setValue(null);
        yearCombo.setValue(Year.of(2022));
        startCombo.setValue(null);
        stopCombo.setValue(null);

        btnToAdd.setDisable(true);
        btnToModify.setDisable(false);
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

        ObservableList<Appointment> allAppointments = AppointmentDaoImpl.getAllAppointments();

        PopulateAppt(allAppointments, appointmentTableView, customerCol, idCol, titleCol, descriptionCol, locationCol, typeCol, startCol, stopCol, contactCol, userCol);
        //clear form
        titleTextField.clear();
        descriptionTextField.clear();
        locationTextField.clear();
        typeTextField.clear();
        userCombo.setValue(null);
        customerCombo.setValue(null);
        contactCombo.setValue(null);
        monthCombo.setValue(Month.JANUARY);
        dayCombo.setValue(null);
        yearCombo.setValue(Year.of(2022));
        startCombo.setValue(null);
        stopCombo.setValue(null);

        btnToAdd.setDisable(true);
        btnToModify.setDisable(false);

    }




    @FXML
    void modifyBtn(ActionEvent event) throws Exception {

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
        int year = yearCombo.getSelectionModel().getSelectedItem().getValue();
        int month = monthCombo.getSelectionModel().getSelectedItem().getValue();
        int day = Integer.parseInt(dayCombo.getSelectionModel().getSelectedItem().toString());

        //format start time for timestamp

        LocalTime reverse = LocalTime.parse(startCombo.getSelectionModel().getSelectedItem(), amFormatter);

        // System.out.println(initial);

        LocalDate ldStart = LocalDate.of(year, month, day);
        LocalDateTime ldtStart = LocalDateTime.of(ldStart, reverse);


        Timestamp start = Timestamp.valueOf(ldtStart);

        //create string for timestamp for end
        LocalTime stopReverse = LocalTime.parse(stopCombo.getSelectionModel().getSelectedItem(), amFormatter);

        //convert to utc for database
        LocalDateTime ldtStop = LocalDateTime.of(ldStart, stopReverse);

        Timestamp stop = Timestamp.valueOf(ldtStop);

        String createdBy = LogInFormController.getCurrentUser().getUserName();
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        int contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();
        int userId = userCombo.getSelectionModel().getSelectedItem().getUserId();


            int customerId = customerCombo.getSelectionModel().getSelectedItem().getCustomerId();

            Appointment updatedAppointment = new Appointment(appId, title, description, location, type, start, stop, createdBy, createdDate, customerId, contactId, userId);
            AppointmentDaoImpl.updateAppointment(customerCombo.getSelectionModel().getSelectedItem(), updatedAppointment);

        //repopulate table

        ObservableList<Appointment> allAppointments = AppointmentDaoImpl.getAllAppointments();

        PopulateAppt(allAppointments, appointmentTableView, customerCol, idCol, titleCol, descriptionCol, locationCol, typeCol, startCol, stopCol, contactCol, userCol);

        //clear form
        titleTextField.clear();
        descriptionTextField.clear();
        locationTextField.clear();
        typeTextField.clear();
        userCombo.setValue(null);
        customerCombo.setValue(null);
        contactCombo.setValue(null);
        monthCombo.setValue(Month.JANUARY);
        dayCombo.setValue(null);
        yearCombo.setValue(Year.of(2022));
        startCombo.setValue(null);
        stopCombo.setValue(null);

        btnToAdd.setDisable(true);
        btnToModify.setDisable(false);

    }

    @FXML
    void returnToMainBtn(ActionEvent event) throws IOException {

        //return to main form
        ChangeScene(event, "/View/MainForm.fxml");

    }

    @FXML
    void selectAppointmentAction(MouseEvent event) throws Exception {

        TableView.TableViewSelectionModel<Appointment> selectionModel = appointmentTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Appointment> selectedAppointment = selectionModel.getSelectedItems();
        Appointment appointment = selectedAppointment.get(0);

        //disable add button and enable modify button
        btnToAdd.setDisable(true);
        btnToModify.setDisable(false);

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

        ObservableList<User> allUsers = UserDaoImpl.getAllUsers();
        for(User user: allUsers){
            if(user.getUserId() == appointment.getUserId()){
                userCombo.setValue(user);
            }
        }

        //set month combo box
        monthCombo.setValue(appointment.getStartTime().toLocalDateTime().getMonth());

        //set year combo box
        yearCombo.setValue(Year.of(Integer.parseInt(appointment.getStartTime().toString().substring(0, 4))));

        //set day combo box
        dayCombo.setValue(Integer.parseInt(appointment.getStartTime().toString().substring(8, 10)));

        startCombo.setValue(amFormatter.format(appointment.getStartTime().toLocalDateTime().toLocalTime()));
        stopCombo.setValue(amFormatter.format(appointment.getEndTime().toLocalDateTime().toLocalTime()));

        Customer selectedCustomer = null;

        for(Customer customer: allCustomers){

            if(customer.getCustomerId() == appointment.getCustomerId()){
                selectedCustomer = customer;
            }

        }

        //set customer combo
        customerCombo.setValue(selectedCustomer);

    }

    @FXML
    void clearBtn(ActionEvent event) {

        TableView.TableViewSelectionModel <Appointment> selectionModel = appointmentTableView.getSelectionModel();
        selectionModel.clearSelection();

        //disable modify button and enable add button
        btnToModify.setDisable(true);
        btnToAdd.setDisable(false);

        titleTextField.clear();
        descriptionTextField.clear();
        locationTextField.clear();
        typeTextField.clear();
        userCombo.setValue(null);
        customerCombo.setValue(null);
        contactCombo.setValue(null);
        monthCombo.setValue(Month.JANUARY);
        dayCombo.setValue(null);
        yearCombo.setValue(Year.of(2022));
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


    private static Customer passedCustomer;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //set modify button disabled
        btnToModify.setDisable(true);

        //set all appointments rb selected
        allAppointmentsRB.setSelected(true);

        ObservableList<Appointment> allAppointments = null;
        ObservableList<User> allUsers = null;

        try {
            allAppointments = AppointmentDaoImpl.getAllAppointments();
            allCustomers = CustomerDaoImpl.getAllCustomers();
            allUsers = UserDaoImpl.getAllUsers();

        } catch (Exception e) {
            e.printStackTrace();
        }



        PopulateAppt(allAppointments, appointmentTableView, customerCol, idCol, titleCol, descriptionCol, locationCol, typeCol, startCol, stopCol, contactCol, userCol);

        //get customer and Appointment from customer form

        passedCustomer = CalendarFormController.getCustomer();

        //populate month combo
        for (int m = 1; m < 13; ++m) {
            monthCombo.getItems().add(Month.of(m));
        }

        monthCombo.setValue(Month.JANUARY);

        //populate yearCombo
        Year ystart = Year.now();
        Year yend = Year.parse(LAST_YEAR);

        while (ystart.isBefore(yend)) {
            yearCombo.getItems().add(ystart);
            ystart = ystart.plusYears(1);
        }
        yearCombo.setValue(Year.now());

        //populate day colum
        for (int d = 1; d < 32; d++) {
            dayCombo.getItems().add(d);
        }

        //populate contact combo


        try {
            allContacts = ContactDaoImp.getAllContacts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        contactCombo.setItems(allContacts);

        //set customer combo box
        customerCombo.setItems(allCustomers);

        //set user combo box
        userCombo.setItems(allUsers);

        //set Time combo boxes
        startCombo.getItems().clear();

        LocalDateTime start = LocalDateTime.of(LocalDate.now(), businessStart);
        LocalDateTime stop = LocalDateTime.of(LocalDate.now(), businessEnd);

        //convert business hours to local time zone
        LocalTime initStart = Timezone.EasternToLocal(start).toLocalTime();
        LocalTime initEnd = Timezone.EasternToLocal(stop).toLocalTime();
        while (initStart.isBefore(initEnd.plusSeconds(1))) {

            startCombo.getItems().add(amFormatter.format(initStart));
            stopCombo.getItems().add(amFormatter.format(initStart));

            String s = amReverse.format(initStart);
            LocalTime reverse = LocalTime.parse(s, amReverse);
            System.out.println(reverse);

            System.out.println(LocalTime.parse(s));
            initStart = initStart.plusMinutes(5);
        }




        //FIX ME change radio buttons based on time;
    }




}