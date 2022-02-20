package Controller;

import DAO.CustomerDaoImpl;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    public void PopulateTable(ObservableList<Customer> tableList, TableView<Customer> tableView, TableColumn<Customer, String> Column1, TableColumn<Customer, String> Column2, TableColumn<Customer, String> Column3){

        tableView.setItems(tableList);

        Column1.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        Column2.setCellValueFactory(new PropertyValueFactory<>("fullAddress"));
        Column3.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

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
    private TableView<?> appointmentTableView;

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
    void addCustomerBtn(ActionEvent event) throws IOException {

        ChangeScene(event, "/View/AddCustomerForm.fxml");

    }

    @FXML
    void deleteAppointmentBtn(ActionEvent event) {

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

    //pass the customer to the modify screen
    private static Customer modifyCustomer = null;

    public static Customer getCustomer(){
        return modifyCustomer;
    }

    @FXML
    void modifyAppointmentBtn(ActionEvent event) throws IOException {

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