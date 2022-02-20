package Controller;

import DAO.CustomerDaoImpl;
import Model.Customer;
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
        private ComboBox<?> countryCombo;

        @FXML
        private TextField customerNameTextField;

        @FXML
        private TextField phoneNumberTextField;

        @FXML
        private TextField postalCodeTextField;

        @FXML
        private ComboBox<?> stateCombo;

        @FXML
        void saveBtn(ActionEvent event) throws Exception {
            //FIX ME! add combo box functionality to enter in division id
            String customerName = customerNameTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String address = addressTextField.getText();
            int divisionId = CustomerFormController.getCustomer().getDivisionId();
            int customerId = CustomerFormController.getCustomer().getCustomerId();
            String fullAddress = address + ", " + postalCode;

            Customer newCustomer = new Customer(customerId, customerName, address, postalCode, phoneNumber, divisionId, fullAddress);
            CustomerDaoImpl.updateCustomer(newCustomer);

            ChangeScene(event, "/View/CustomerForm.fxml");

        }

        @FXML
        public void cancelBtn(ActionEvent event) throws IOException {

            ChangeScene(event, "/View/CustomerForm.fxml");

        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get customer from previous screen
        Customer customer = CustomerFormController.getCustomer();
        customerNameTextField.setText(customer.getCustomerName());
        addressTextField.setText(customer.getAddress());
        postalCodeTextField.setText(customer.getPostalCode());
        phoneNumberTextField.setText(customer.getPhoneNumber());

    }
}
