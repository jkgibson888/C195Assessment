package Controller;

import DAO.AppointmentDaoImpl;
import DAO.ContactDaoImp;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {

    //customer and appointment to be received
    Customer passedCustomer;
    Appointment passedAppointment;

    //Method to switch scenes

    Stage stage;
    Parent scene;

    /**
     * Changes the scene based on a button event.
     *
     * @param event       The button event that triggers the change of scene.
     * @param scenestring The location of the new scene to be displayed.
     * @throws IOException
     */
    public void ChangeScene(ActionEvent event, String scenestring) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(scenestring));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //farthest year for the appointments
    private final String LAST_YEAR = "2035";

    @FXML
    private TextField titleTextField;

    @FXML
    private ComboBox<Contact> contactCombo;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private ComboBox dayCombo;

    @FXML
    private ComboBox<Month> monthCombo;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private ComboBox<LocalTime> startCombo;

    @FXML
    private ComboBox<LocalTime> stopCombo;

    @FXML
    private ComboBox<Year> yearCombo;

    @FXML
    private RadioButton endAMRadio;

    @FXML
    private RadioButton endPMRadio;

    @FXML
    private ToggleGroup endTG;

    @FXML
    private RadioButton startAMRadio;

    @FXML
    private RadioButton startPMRadio;

    @FXML
    private ToggleGroup startTG;

    @FXML
    private TextFlow errorTextFlow;

    @FXML
    void addBtn(ActionEvent event) throws Exception {

        //int appId = AppointmentFormController.getPassedAppointment().getAppointmentId();
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        String type = locationTextField.getText();

        //create string for timestamp for start
        int year = yearCombo.getSelectionModel().getSelectedItem().getValue() - 1900;
        int month = monthCombo.getSelectionModel().getSelectedItem().getValue() - 1;
        int date = Integer.parseInt(dayCombo.getSelectionModel().getSelectedItem().toString());
        int hour = startCombo.getSelectionModel().getSelectedItem().getHour();
        int minute = startCombo.getSelectionModel().getSelectedItem().getMinute();
        int second = 0;
        int nano = 0;
        Timestamp start = new Timestamp(year, month, date, hour, minute, second, nano);

        //create string for timestamp for end
        int ehour = stopCombo.getSelectionModel().getSelectedItem().getHour();
        int eminute = stopCombo.getSelectionModel().getSelectedItem().getMinute();
        int esecond = 0;
        int enano = 0;

        Timestamp end = new Timestamp(year, month, date, ehour, eminute, esecond, enano);

        String createdBy = LogInFormController.getCurrentUser().getUserName();
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        int customerId = passedCustomer.getCustomerId();
        int contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();

        //Appointment appointment = new Appointment(appId, title, description, location, type, start, end, createdBy, createdDate, customerId, contactId);
        //AppointmentDaoImpl.updateAppointment(AppointmentFormController.getCustomer(), appointment);

        ChangeScene(event, "/View/AppointmentForm.fxml");
    }

    @FXML
    void cancelBtn(ActionEvent event) throws IOException {

        ChangeScene(event, "/View/AppointmentForm.fxml");

    }

    @FXML
    void setDayAction(MouseEvent event) {
        errorTextFlow.getChildren().clear();
        dayCombo.getItems().clear();
        Month currentMonth = monthCombo.getSelectionModel().getSelectedItem();
        Year currentYear = yearCombo.getSelectionModel().getSelectedItem();
        if(monthCombo.getSelectionModel().getSelectedItem() != null) {
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
            Text errorText = new Text("Please choose a month \n");
            errorTextFlow.getChildren().add(errorText);
        }
    }

    @FXML
    void setStartCorrectTime(MouseEvent event) {
        //startCombo.getItems().clear();

        if(startAMRadio.isSelected()){
            startCombo.getItems().clear();
            System.out.println("entered start AM.....");
            LocalTime initStart = LocalTime.of(8, 0);
            LocalTime initEnd = LocalTime.of(11, 50);
            while (initStart.isBefore(initEnd.plusSeconds(1))) {
                startCombo.getItems().add(initStart);
                initStart = initStart.plusMinutes(5);
            }
        }

        if(startPMRadio.isSelected()){
            System.out.println("entered start PM.....");
            startCombo.getItems().clear();

            LocalTime start = LocalTime.NOON;
            LocalTime end = LocalTime.of(14, 55);
            while (start.isBefore(end.plusSeconds(1))) {

                if(start.getHour() >= 13){
                    start.minusHours(12);
                }
                startCombo.getItems().add(start);
                start = start.plusMinutes(5);

            }

        }

    }

    @FXML
    void setEndCorrectTime(MouseEvent event) {
        if(endAMRadio.isSelected()){
            stopCombo.getItems().clear();
            LocalTime initStart = LocalTime.of(8, 0);
            LocalTime initEnd = LocalTime.of(11, 50);
            while (initStart.isBefore(initEnd.plusSeconds(1))) {
                stopCombo.getItems().add(initStart);
                initStart = initStart.plusMinutes(5);
            }
        }

        if(endPMRadio.isSelected()){
            System.out.println("entered start PM.....");
            stopCombo.getItems().clear();

            LocalTime start = LocalTime.NOON;
            LocalTime end = LocalTime.of(14, 55);
            while (start.isBefore(end.plusSeconds(1))) {

                if(start.getHour() >= 13){
                    start.minusHours(12);
                }
                stopCombo.getItems().add(start);
                start = start.plusMinutes(5);

            }

        }
    }


        @Override
        public void initialize (URL url, ResourceBundle resourceBundle){

            //get customer and Appointment from customer form

            passedCustomer = AppointmentFormController.getCustomer();
           // passedAppointment = AppointmentFormController.getPassedAppointment();

            //populate month combo
            monthCombo.setPromptText("Month");

            for (int m = 1; m < 13; ++m) {
                monthCombo.getItems().add(Month.of(m));
            }

            //set month combo box
            monthCombo.setValue(Month.of(Integer.parseInt(passedAppointment.getStartTime().toString().substring(5, 7))));

            //populate yearCombo
            yearCombo.setPromptText("Year");

            Year ystart = Year.now();
            Year yend = Year.parse(LAST_YEAR);

            while (ystart.isBefore(yend)) {
                yearCombo.getItems().add(ystart);
                ystart = ystart.plusYears(1);
            }
            yearCombo.setValue(Year.now());

            //set year combo box
            yearCombo.setValue(Year.of(Integer.parseInt(passedAppointment.getStartTime().toString().substring(0, 4))));

            //populate day colum
            dayCombo.setPromptText("Day");

            for (int d = 1; d < 29; d++) {
                dayCombo.getItems().add(d);
            }

            //set day combo box
            dayCombo.setValue(Integer.parseInt(passedAppointment.getStartTime().toString().substring(8, 10)));

            //populate contact combo
            ObservableList<Contact> allContacts = FXCollections.observableArrayList();

            try {
                allContacts = ContactDaoImp.getAllContacts();
            } catch (Exception e) {
                e.printStackTrace();
            }
            contactCombo.setItems(allContacts);
            contactCombo.setPromptText("Choose contact");
            //set contact combo box
            for(Contact contact: allContacts) {
                if(contact.getContactId() == passedAppointment.getContactId()) {
                    contactCombo.setValue(contact);
                }
            }

            startAMRadio.setSelected(true);
            endAMRadio.setSelected(true);
            startCombo.setPromptText("Start time");
            stopCombo.setPromptText("Start time");

            int startHour = Integer.parseInt(passedAppointment.getStartTime().toString().substring(11, 12));
            int startMin = Integer.parseInt(passedAppointment.getStartTime().toString().substring(14, 15));
            int startSec = Integer.parseInt(passedAppointment.getStartTime().toString().substring(17, 18));
            int startNano = Integer.parseInt(passedAppointment.getStartTime().toString().substring(20));

            //LocalTime startTime = new LocalTime(startHour, startMin, startSec, startNano);

            startCombo.setValue(passedAppointment.getStartTime().toLocalDateTime().toLocalTime());
            stopCombo.setValue(passedAppointment.getEndTime().toLocalDateTime().toLocalTime());

            //set text fields
            titleTextField.setText(passedAppointment.getTitle());
            descriptionTextField.setText(passedAppointment.getDescription());
            typeTextField.setText(passedAppointment.getType());
            locationTextField.setText(passedAppointment.getLocation());


            //FIX ME change radio buttons based on time;
        }

}
