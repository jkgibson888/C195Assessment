package Controller;

import DAO.UserDaoImpl;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import javax.swing.*;
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
    public void ChangeScene(KeyEvent event, String scenestring) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(scenestring));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    //fx id's for form

    @FXML
    private TextFlow errorTextFlow;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField userNameTextField;

    //method to get and set the current user of the system
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

                Text errorText = new Text("User name or password incorrect!");
                errorTextFlow.getChildren().add(errorText);
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
    //FIX ME!! check user and password if enter key is pressed
    @FXML
    void enterPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            try {
                ObservableList<User> allUsers = UserDaoImpl.getAllUsers();
                boolean userFound = false;
                for(User user: allUsers){
                    if(userNameTextField.getText().contentEquals(user.getUserName()) && passwordTextField.getText().contentEquals(user.getPassword())){
                        currentUser = new User(user.getUserId(), user.getUserName(), user.getPassword());
                        System.out.println(currentUser);

                        if(currentUser.getUserName().contentEquals("admin")){
                            System.out.println("Enter key pressed");
                            Parent root1 = FXMLLoader.load(getClass().getResource("/View/UserTestTable.fxml"));
                            Scene scene1 = new Scene(root1);
                            stage.setScene(scene1);
                            stage.show();
                        }
                        else{
                            ChangeScene(event, "/View/MainForm.fxml");
                        }

                        userFound = true;
                    }
                }
                if(!userFound) {

                    Text errorText = new Text("User name or password incorrect!");
                    errorTextFlow.getChildren().add(errorText);
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

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


}
