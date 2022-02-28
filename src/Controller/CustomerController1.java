package Controller;

import DAO.CountryDaoImpl;
import DAO.CustomerDaoImpl;
import DAO.FirstLevelDivisionDaoImpl;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class CustomerController1 implements Initializable {

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
    private TextField customerIdTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private ComboBox<Country> countryCombo;

    @FXML
    private ComboBox<FirstLevelDivision> stateCombo;

    //customer table fxid's
    @FXML
    private TableView<Customer> customerView;

    @FXML
    private TableColumn<Customer, Integer> idCol;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> postalCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    private static Customer thiscustomer;

    @FXML
    void customerSelected(MouseEvent event) throws Exception{

        try {
            //get selected customer from customer table
            TableView.TableViewSelectionModel<Customer> selectionModel = customerView.getSelectionModel();
            selectionModel.setSelectionMode(SelectionMode.SINGLE);
            ObservableList<Customer> selectedCustomer = selectionModel.getSelectedItems();
            thiscustomer = selectedCustomer.get(0);

        }
        catch(IndexOutOfBoundsException e){

        }



        customerIdTextField.setText(String.valueOf(thiscustomer.getCustomerId()));
        customerNameTextField.setText(thiscustomer.getCustomerName());
        addressTextField.setText(thiscustomer.getAddress());
        postalCodeTextField.setText(thiscustomer.getPostalCode());
        phoneNumberTextField.setText(thiscustomer.getPhoneNumber());


    }

    //populate customer table
    public void PopulateTable(ObservableList<Customer> tableList, TableView<Customer> tableView, TableColumn<Customer, Integer> Column1, TableColumn<Customer, String> Column2, TableColumn<Customer, String> Column3, TableColumn<Customer, String> Column4, TableColumn<Customer, String> Column5){

        tableView.setItems(tableList);

        Column1.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        Column2.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        Column3.setCellValueFactory(new PropertyValueFactory<>("fullAddress"));
        Column4.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        Column5.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    }

    @FXML
    void addBtn(ActionEvent event) throws IOException {

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

        ChangeScene(event, "/View/AppointmentForm.fxml");

    }

    @FXML
    public void cancelBtn(ActionEvent event) throws IOException {

        ChangeScene(event, "/View/AppointmentForm.fxml");

    }

    //selected country to pass
    private Country selectedCountry;

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

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

                }
            }

        }

        //set first level combo box
        stateCombo.setItems(countryFirst);

    }


    @FXML
    void modifyBtn(ActionEvent event) {

    }


    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            allCustomers = CustomerDaoImpl.getAllCustomers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //get all countries and first level divisions
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> allFirst = FXCollections.observableArrayList();

        //populate customer table
        PopulateTable(allCustomers, customerView, idCol, nameCol, addressCol, postalCol, phoneCol);

        try {
            allCountries = CountryDaoImpl.getAllCountries();
            allFirst = FirstLevelDivisionDaoImpl.getAllFirstLevelDivisions();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
