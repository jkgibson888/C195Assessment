package Controller;

import DAO.AppointmentDaoImpl;
import DAO.CountryDaoImpl;
import DAO.CustomerDaoImpl;
import DAO.FirstLevelDivisionDaoImpl;
import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import Utility.Lambda;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class that implements the logic driving the customer form.
 *
 * @author Joshua Gibson
 */
public class CustomerFormController implements Initializable {

    //get all countries, customers, and first level divisions
    ObservableList<Country> allCountries = FXCollections.observableArrayList();
    ObservableList<FirstLevelDivision> allFirst = FXCollections.observableArrayList();
    ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private Customer customer;
    private Country selectedCountry;

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

    //populate customer table

    /**
     * Populates a table view with customers.
     * @param tableList The observable list of customer objects that table data is retrieved from.
     * @param tableView The specific table view that will display the information.
     * @param Column1 The first column, which displays the customer's name.
     * @param Column2 The second column, which displays the customer's address.
     * @param Column3 The third column, which displays the division associated with the customer.
     * @param Column4 The fourth column, which displays the country associated with the customer.
     * @param Column5 The fifth column, which displays the postal code associated with the customer.
     * @param Column6 The sixth column, which displays the customer's phone number.
     */
    public void PopulateTable(ObservableList<Customer> tableList, TableView<Customer> tableView, TableColumn<Customer, String> Column1, TableColumn<Customer, String> Column2, TableColumn<Customer, String> Column3, TableColumn<Customer, String> Column4, TableColumn<Customer, String> Column5, TableColumn<Customer, String> Column6){

        tableView.setItems(tableList);
        Column1.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        Column2.setCellValueFactory(new PropertyValueFactory<>("address"));
        Column3.setCellValueFactory(new PropertyValueFactory<>("division"));
        Column4.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        Column5.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        Column6.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    }

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TextField addressTextField;

    @FXML
    private ComboBox<Country> countryCombo;

    @FXML
    private TextField customerIdTextField;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TableView<Customer> customerView;

    @FXML
    private TextFlow errorTextFlow;

    @FXML
    private TableColumn<Customer, String> divisionCol;

    @FXML
    private TableColumn<Customer, String> countryCol;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private TableColumn<Customer, String> postalCol;

    @FXML
    private ComboBox<FirstLevelDivision> stateCombo;

    @FXML
    private Button addButtonID;

    @FXML
    private Button modifyButtonId;

    @FXML
    private Button deleteButtonId;

    @FXML
    private Button customerAppButtonId;

    /**
     * Method that allows a customer to be added to the database.
     * @param event The button being pressed.
     * @throws Exception
     */
    @FXML
    void addBtn(ActionEvent event) throws Exception {

        String customerName = customerNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String address = addressTextField.getText();
        Timestamp createDate = new Timestamp(System.currentTimeMillis());
        String createdBy = LogInFormController.getCurrentUser().getUserName();
        Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
        String lastUpdatedBy = LogInFormController.getCurrentUser().getUserName();
        int divisionId = stateCombo.getSelectionModel().getSelectedItem().getDivisionId();
        String division = stateCombo.getSelectionModel().getSelectedItem().getDivision();
        String fullAddress = address + ", " + division;

        Customer newCustomer = new Customer(0, customerName, address, postalCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId, division, fullAddress);
        CustomerDaoImpl.insertCustomer(newCustomer);

        allCustomers = CustomerDaoImpl.getAllCustomers();

        PopulateTable(allCustomers, customerView, nameCol, addressCol, divisionCol, countryCol, postalCol, phoneCol);

    }

    private static Customer passedCustomer;
    /**
     * Method to pass a customer to another form.
     * @return The customer object that is being passed to another form.
     */
    public static Customer getPassedCustomer() {
        return passedCustomer;
    }

    /**
     * Clears the forms text fields so that a new customer can be created. A lambda expression was used here to demonstrate how to use a programmer created lambda expression.
     * @param event The button being pressed.
     */
    @FXML
    void clearForm(ActionEvent event) {

        Lambda lambda = () -> {
            System.out.println("i am here");
            //enable and disable appropriate buttons
            addButtonID.setDisable(false);
            modifyButtonId.setDisable(true);
            deleteButtonId.setDisable(true);
            customerAppButtonId.setDisable(true);

            //clear form
            TableView.TableViewSelectionModel<Customer> selectionModel = customerView.getSelectionModel();
            selectionModel.clearSelection();
            customerIdTextField.clear();
            customerNameTextField.clear();
            addressTextField.clear();
            postalCodeTextField.clear();
            phoneNumberTextField.clear();
            countryCombo.setValue(null);
            stateCombo.setValue(null);
        };

        lambda.lambdaFunction();
    }

    /**
     * Retrieves a selected customer from the table view and then changes to the appointment form.
     * @param event The button being pressed
     * @throws IOException
     */
    @FXML
    void customerAppBtn(ActionEvent event) throws IOException {

        //get selected customer from customer table
        TableView.TableViewSelectionModel<Customer> selectionModel = customerView.getSelectionModel();
        ObservableList<Customer> selectedCustomer = selectionModel.getSelectedItems();
        passedCustomer = selectedCustomer.get(0);

        ChangeScene(event, "/View/AppointmentForm.fxml");

    }

    /**
     * Retrieves a selected customer's information from the table and then populates the form's fields.
     * @param event The customer being selected in the table view.
     */
    @FXML
    void customerSelected(MouseEvent event) {

        //enable and disable buttons
        addButtonID.setDisable(true);
        modifyButtonId.setDisable(false);
        deleteButtonId.setDisable(false);
        customerAppButtonId.setDisable(false);

        //get selected customer from customer table
        TableView.TableViewSelectionModel<Customer> selectionModel = customerView.getSelectionModel();
        ObservableList<Customer> selectedCustomer = selectionModel.getSelectedItems();
        customer = selectedCustomer.get(0);
        passedCustomer = selectedCustomer.get(0);

        //set all fields when customer is selected
        customerIdTextField.setText(String.valueOf(customer.getCustomerId()));
        customerNameTextField.setText(customer.getCustomerName());
        addressTextField.setText(customer.getAddress());
        postalCodeTextField.setText(customer.getPostalCode());
        phoneNumberTextField.setText(customer.getPhoneNumber());

        countryCombo.setValue(null);

        //FIX ME!
        try {
            countryCombo.setValue(CountryDaoImpl.searchCountry(customer.getDivisionId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stateCombo.setValue(FirstLevelDivisionDaoImpl.searchDivision(customer.getDivisionId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * Method that allows a customer to be deleted from the database.
     * @param event The button being pressed.
     * @throws Exception
     */
    @FXML
    void deleteBtn(ActionEvent event) throws Exception {

        errorTextFlow.getChildren().clear();


        //get selected customer from customer table
        TableView.TableViewSelectionModel<Customer> selectionModel = customerView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Customer> selectedCustomer = selectionModel.getSelectedItems();
        Customer customer = selectedCustomer.get(0);

        customerAppointments = AppointmentDaoImpl.getCustomerAppointments(customer);

        if(customerAppointments.size() != 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Customer has " + customerAppointments.size() + " appointments, delete appointments first.");
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }

        //display custom message saying customer was deleted

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Customer Deleted");
        alert.setTitle("Notice");
        alert.setContentText(customer.getCustomerName() + " was deleted.");
        alert.showAndWait();
        Text deleteText = new Text("Customer " + customer.getCustomerName() + " deleted. \n");
        errorTextFlow.getChildren().add(deleteText);

        //delete customer from database
        CustomerDaoImpl.deleteCustomer(customer);

        //repopulate the table
        try {
            allCustomers = CustomerDaoImpl.getAllCustomers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PopulateTable(allCustomers, customerView, nameCol, addressCol, divisionCol, countryCol, postalCol, phoneCol);

        //clear form
        customerIdTextField.clear();
        customerNameTextField.clear();
        addressTextField.clear();
        postalCodeTextField.clear();
        phoneNumberTextField.clear();
        countryCombo.setValue(null);
        stateCombo.setValue(null);
    }

    /**
     * Method that allows a customer to be modified in the database.
     * @param event The button being pressed.
     * @throws Exception
     */
    @FXML
    void modifyBtn(ActionEvent event) throws Exception {

        //FIX ME! add combo box functionality to enter in division id
        int customerId = customer.getCustomerId();
        String customerName = customerNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String address = addressTextField.getText();
        Timestamp createDate = customer.getCreateDate();
        String createdBy = customer.getCreatedBy();
        Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
        String lastUpdatedBy = LogInFormController.getCurrentUser().getUserName();
        int divisionId = stateCombo.getSelectionModel().getSelectedItem().getDivisionId();
        String division = stateCombo.getSelectionModel().getSelectedItem().getDivision();
        String fullAddress = address + ", " + division;

        Customer newCustomer = new Customer(customerId, customerName, address, postalCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId, division, fullAddress);
        CustomerDaoImpl.updateCustomer(newCustomer);

        allCustomers = CustomerDaoImpl.getAllCustomers();

        PopulateTable(allCustomers, customerView, nameCol, addressCol, divisionCol, countryCol, postalCol, phoneCol);

    }

    /**
     * Returns to the main form
     * @param event The button being pressed.
     * @throws Exception
     */
    @FXML
    void returnBtn(ActionEvent event) throws IOException {

        ChangeScene(event, "/View/MainForm.fxml");

    }

    /**
     * Gets the selected country from the country combo box and then populates the first level division combo box with appropriate values.
     * @param event The country being selected in the country combo box.
     * @throws Exception
     */
    @FXML
    void selectCountryClick(ActionEvent event) throws Exception {

        ObservableList<FirstLevelDivision> countryFirst= FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> allFirst = FirstLevelDivisionDaoImpl.getAllFirstLevelDivisions();
        //get first level divisions for currently selected country
        selectedCountry = countryCombo.getSelectionModel().getSelectedItem();


        for(FirstLevelDivision fl: allFirst){

            if(selectedCountry != null) {
                if (selectedCountry.getCountryId() == fl.getCountryId()) {

                    countryFirst.add(fl);
                    stateCombo.setValue(null);

                }
            }

        }


        //set first level combo box
        stateCombo.setItems(countryFirst);

    }

    /**
     * Initializes the form and populates the combo boxes and table view.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //disable modify button
        modifyButtonId.setDisable(true);
        deleteButtonId.setDisable(true);
        customerAppButtonId.setDisable(true);

        try {
            allCustomers = CustomerDaoImpl.getAllCustomers();
            allCountries = CountryDaoImpl.getAllCountries();
            allFirst = FirstLevelDivisionDaoImpl.getAllFirstLevelDivisions();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PopulateTable(allCustomers, customerView, nameCol, addressCol, divisionCol, countryCol, postalCol, phoneCol);

        //set country combo box
        countryCombo.setItems(allCountries);
        countryCombo.setPromptText("Choose country");


        selectedCountry = countryCombo.getSelectionModel().getSelectedItem();

        //get first level divisions for currently selected country
        ObservableList<FirstLevelDivision> countryFirst= FXCollections.observableArrayList();

        for(FirstLevelDivision fl: allFirst){

            if(selectedCountry != null) {
                if (selectedCountry.getCountryId() == fl.getCountryId()) {

                    countryFirst.add(fl);

                }
            }

        }

        //set first level combo box
        stateCombo.setItems(countryFirst);
        stateCombo.setPromptText("Select State/Province");
    }
}