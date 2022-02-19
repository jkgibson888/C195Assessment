package Testing;

import DAO.UserDaoImpl;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserTestTableController implements Initializable {

    ObservableList<User> allUsers = FXCollections.observableArrayList();

    @FXML
    private TextField newPasswordTxt;

    @FXML
    private TextField newUserTxt;

    @FXML
    private TableColumn<User, String> passwordCol;

    @FXML
    private TableColumn<User, Integer> userIdCol;

    @FXML
    private TableColumn<User, String> userNameCol;

    @FXML
    private TableView<User> userTable;

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

    public void PopulateTable(ObservableList<User> tableList, TableView<User> tableView, TableColumn<User, Integer> Column1, TableColumn<User, String> Column2, TableColumn<User, String> Column3){

        tableView.setItems(tableList);

        Column1.setCellValueFactory(new PropertyValueFactory<>("userId"));
        Column2.setCellValueFactory(new PropertyValueFactory<>("userName"));
        Column3.setCellValueFactory(new PropertyValueFactory<>("password"));

    }

    @FXML
    void addNewUser(ActionEvent event) {

        User newUser = new User(1, newUserTxt.getText(), newPasswordTxt.getText());
        UserDaoImpl.insertUser(newUser);

        try {
            allUsers = UserDaoImpl.getAllUsers();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        //initiallize tables
        PopulateTable(allUsers, userTable, userIdCol, userNameCol, passwordCol);

    }

    @FXML
    void ReturnToLogin(ActionEvent event) throws IOException {

        ChangeScene(event, "/View/LogInForm.fxml");

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            allUsers = UserDaoImpl.getAllUsers();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        //initiallize tables
        PopulateTable(allUsers, userTable, userIdCol, userNameCol, passwordCol);

    }

}