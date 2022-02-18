package Controller;

import DAO.UserDaoImpl;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class UserTestTableController implements Initializable {

    ObservableList<User> allUsers = FXCollections.observableArrayList();


    @FXML
    private TableColumn<User, String> passwordCol;

    @FXML
    private TableColumn<User, Integer> userIdCol;

    @FXML
    private TableColumn<User, String> userNameCol;

    @FXML
    private TableView<User> userTable;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            allUsers = UserDaoImpl.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //initiallize tables
        PopulateTable(allUsers, userTable, userIdCol, userNameCol, passwordCol);

    }

}