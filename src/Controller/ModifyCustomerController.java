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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {

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
        private TextField addressTextField;

        @FXML
        private ComboBox<Country> countryCombo;

        @FXML
        private TextField customerNameTextField;

        @FXML
        private TextField phoneNumberTextField;

        @FXML
        private TextField postalCodeTextField;

        @FXML
        private ComboBox<FirstLevelDivision> stateCombo;

        private Country selectedCountry;

        public Country getSelectedCountry() {
        return selectedCountry;
    }

        public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

        @FXML
        void saveBtn(ActionEvent event) throws Exception {

            Customer tempCustomer = AppointmentFormController.getCustomer();
            //FIX ME! add combo box functionality to enter in division id
            int customerId = tempCustomer.getCustomerId();
            String customerName = customerNameTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String address = addressTextField.getText();
            Timestamp createDate = tempCustomer.getCreateDate();
            String createdBy = tempCustomer.getCreatedBy();
            Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
            String lastUpdatedBy = LogInFormController.getCurrentUser().getUserName();
            int divisionId = stateCombo.getSelectionModel().getSelectedItem().getDivisionId();
            String division = stateCombo.getSelectionModel().getSelectedItem().getDivision();
            String fullAddress = address + ", " + division;

            Customer newCustomer = new Customer(customerId, customerName, address, postalCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId, division, fullAddress);
            CustomerDaoImpl.updateCustomer(newCustomer);

            ChangeScene(event, "/View/AppointmentForm.fxml");

        }

        @FXML
        public void cancelBtn(ActionEvent event) throws IOException {

            ChangeScene(event, "/View/AppointmentForm.fxml");

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get customer from previous screen
        Customer customer = AppointmentFormController.getCustomer();
        customerNameTextField.setText(customer.getCustomerName());
        addressTextField.setText(customer.getAddress());
        postalCodeTextField.setText(customer.getPostalCode());
        phoneNumberTextField.setText(customer.getPhoneNumber());

        //get all countries and first level divisions
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> allFirst = FXCollections.observableArrayList();
        try {
            allCountries = CountryDaoImpl.getAllCountries();
            allFirst = FirstLevelDivisionDaoImpl.getAllFirstLevelDivisions();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //set country combo box
        countryCombo.setItems(allCountries);
        countryCombo.setPromptText("Choose country");
        try {
            countryCombo.setValue(CountryDaoImpl.searchCountry(customer.getDivisionId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }


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
        try {
            stateCombo.setValue(FirstLevelDivisionDaoImpl.searchDivision(customer.getDivisionId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
