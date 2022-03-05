package Controller;

import DAO.AppointmentDaoImpl;
import DAO.CustomerDaoImpl;
import Model.Appointment;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class MainFormController implements Initializable {

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

        ChangeScene(event, "/View/CalendarForm.fxml");

    }

    @FXML
    void customerBtnPressed(ActionEvent event) throws IOException {

        ChangeScene(event, "/View/CustomerForm.fxml");

    }

    @FXML
    void logOutBtn(ActionEvent event) throws IOException {

        ChangeScene(event, "/View/LogInForm.fxml");

    }

    @FXML
    void reportsBtn(ActionEvent event) throws IOException {

        ChangeScene(event, "/View/ReportsForm.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointment> allAppointments = null;
        ObservableList<Customer> allCustomers = null;

        try {
            allAppointments = AppointmentDaoImpl.getAllAppointments();
            allCustomers = CustomerDaoImpl.getAllCustomers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean appointment = false;
        for(Appointment app: allAppointments){
            Customer customer = null;

            if(LogInFormController.getCurrentUser().getUserId() == app.getUserId() && app.getStartTime().toLocalDateTime().toLocalDate().compareTo(LocalDate.now()) == 0){

                System.out.println(app.getStartTime().toLocalDateTime().toLocalTime().getMinute() - LocalTime.now().getMinute());
                if((app.getStartTime().toLocalDateTime().toLocalTime().getMinute() - LocalTime.now().getMinute()) < 15 && (app.getStartTime().toLocalDateTime().toLocalTime().getMinute() - LocalTime.now().getMinute()) > 0) {

                    appointment = true;
                    for(Customer cust: allCustomers){
                        if(cust.getCustomerId() == app.getCustomerId());
                        customer = cust;
                    }

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Appointment with " + customer.getCustomerName() + " in " + (app.getStartTime().toLocalDateTime().toLocalTime().getMinute() - LocalTime.now().getMinute()) + " minutes.");

                    alert.show();

                }
            }

        }

        if(!appointment){

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("No upcoming appointments.");

                alert.show();

        }

    }
}
