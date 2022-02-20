package Controller;

import DAO.CustomerDaoImpl;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCustomerController {

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
        void addBtn(ActionEvent event) throws IOException {
            //FIX ME! add combo box functionality to enter in division id
            String customerName = customerNameTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String address = addressTextField.getText();
            int divisionId = 1;
            String fullAddress = address + ", " + postalCode;

            Customer newCustomer = new Customer(0, customerName, address, postalCode, phoneNumber, divisionId, fullAddress);
            CustomerDaoImpl.insertCustomer(newCustomer);

            ChangeScene(event, "/View/CustomerForm.fxml");

        }

        @FXML
        public void cancelBtn(ActionEvent event) throws IOException {

            ChangeScene(event, "/View/CustomerForm.fxml");

        }


}
