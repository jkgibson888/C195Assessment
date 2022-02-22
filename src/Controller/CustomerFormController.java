package Controller;

import DAO.AppointmentDaoImpl;
import DAO.CustomerDaoImpl;
import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

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

    public void PopulateAppt(ObservableList<Appointment> tableList, TableView<Appointment> tableView, TableColumn<Appointment, String> Column1, TableColumn<Appointment, Timestamp> Column2, TableColumn<Appointment, Timestamp> Column3){

        tableView.setItems(tableList);

        Column1.setCellValueFactory(new PropertyValueFactory<>("type"));
        Column2.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        Column3.setCellValueFactory(new PropertyValueFactory<>("endTime"));

    }

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, Timestamp> startCol;

    @FXML
    private TableColumn<Appointment, Timestamp> stopCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TextFlow errorTextFlow;

    //pass the customer to the modify screen
    private static Customer modifyCustomer = null;

    public static Customer getCustomer(){
        return modifyCustomer;
    }

    @FXML
    void addAppointmentBtn(ActionEvent event) throws IOException {

        try {
            TableView.TableViewSelectionModel<Customer> selectionModel = customerTableView.getSelectionModel();
            selectionModel.setSelectionMode(SelectionMode.SINGLE);
            ObservableList<Customer> selectedCustomer = selectionModel.getSelectedItems();
            modifyCustomer = selectedCustomer.get(0);


            ChangeScene(event, "/View/AddAppointmentForm.fxml");

        }
        catch(IndexOutOfBoundsException e) {

            Text errorText = new Text("Please select a customer");
            errorTextFlow.getChildren().add(errorText);

        }

    }

    @FXML
    void addCustomerBtn(ActionEvent event) throws IOException {

        ChangeScene(event, "/View/AddCustomerForm.fxml");

    }

    private static Customer customer;

    @FXML
    void customerSelectedClick(MouseEvent event) throws Exception {
        try {
            //get selected customer from customer table
            TableView.TableViewSelectionModel<Customer> selectionModel = customerTableView.getSelectionModel();
            selectionModel.setSelectionMode(SelectionMode.SINGLE);
            ObservableList<Customer> selectedCustomer = selectionModel.getSelectedItems();
            customer = selectedCustomer.get(0);
            modifyCustomer = selectedCustomer.get(0);


            ObservableList<Appointment> customerAppointments = AppointmentDaoImpl.getCustomerAppointments(customer);

            PopulateAppt(customerAppointments, appointmentTableView, typeCol, startCol, stopCol);
            //DateTimeParseException orignally had localdatetime
        }
        catch(IndexOutOfBoundsException e){

        }
    }

    @FXML
    void deleteAppointmentBtn(ActionEvent event) throws Exception {

        //get selected appointment from appointment table
        TableView.TableViewSelectionModel<Appointment> selectionModel = appointmentTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Appointment> selectedCustomer = selectionModel.getSelectedItems();
        Appointment appointment = selectedCustomer.get(0);

        //delete appointment from database
        AppointmentDaoImpl.deleteAppointment(appointment);

        //repopulate table

        ObservableList<Appointment> customerAppointments = AppointmentDaoImpl.getCustomerAppointments(customer);

        PopulateAppt(customerAppointments, appointmentTableView, typeCol, startCol, stopCol);

    }

    @FXML
    void deleteCustomerBtn(ActionEvent event) {

        //get selected customer from customer table
        TableView.TableViewSelectionModel<Customer> selectionModel = customerTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Customer> selectedCustomer = selectionModel.getSelectedItems();
        Customer customer = selectedCustomer.get(0);

        //delete customer from database
        CustomerDaoImpl.deleteCustomer(customer);

        //repopulate the table
        try {
            allCustomers = CustomerDaoImpl.getAllCustomers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PopulateTable(allCustomers, customerTableView, nameCol, addressCol, phoneCol);

    }

    //appointment to be passed on
    private static Appointment passedAppointment;

    public static Appointment getPassedAppointment(){
        return passedAppointment;
    }

    @FXML
    void modifyAppointmentBtn(ActionEvent event) throws IOException {

        //get selected appointment from appointment table
        TableView.TableViewSelectionModel<Appointment> selectionModel = appointmentTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Appointment> selectedCustomer = selectionModel.getSelectedItems();
        passedAppointment = selectedCustomer.get(0);

        ChangeScene(event, "/View/ModifyAppointmentForm.fxml");

    }

    @FXML
    void modifyCustomerBtn(ActionEvent event) throws IOException {

        //get selected customer from customer table
        TableView.TableViewSelectionModel<Customer> selectionModel = customerTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Customer> selectedCustomer = selectionModel.getSelectedItems();
        modifyCustomer = selectedCustomer.get(0);

        //change scene to modify customer form
        ChangeScene(event, "/View/ModifyCustomerForm.fxml");

    }

    @FXML
    void returnToMainBtn(ActionEvent event) throws IOException {

        //return to main form
        ChangeScene(event, "/View/MainForm.fxml");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            allCustomers = CustomerDaoImpl.getAllCustomers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PopulateTable(allCustomers, customerTableView, nameCol, addressCol, phoneCol);

    }

}