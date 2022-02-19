package Controller;

import DAO.UserDaoImpl;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LogInFormController implements Initializable {

    //current user to be set later
    public static User currentUser;

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

    //fx id's for form
    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField userNameTextField;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        LogInFormController.currentUser = currentUser;
    }

    public static User user;

    @FXML
    void loginBtnPressed(ActionEvent event) throws Exception {
        try {
            ObservableList<User> allUsers = UserDaoImpl.getAllUsers();
            boolean userFound = false;
            for(User user: allUsers){
                if(userNameTextField.getText().contentEquals(user.getUserName()) && passwordTextField.getText().contentEquals(user.getPassword())){
                    currentUser = new User(user.getUserId(), user.getUserName(), user.getPassword());
                    System.out.println(currentUser);

                    if(currentUser.getUserName().contentEquals("admin")){
                        ChangeScene(event, "/Testing/UserTestTable.fxml");
                    }
                    else{
                        ChangeScene(event, "/View/MainForm.fxml");
                    }

                    userFound = true;
                }
            }
            if(!userFound) {
                System.out.println("User name or password incorrect!");
            }

        }
        catch(NullPointerException e){

        } catch (SQLException e) {
           // e.printStackTrace();
        } catch (Exception e) {
           // e.printStackTrace();
        }


    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


}
