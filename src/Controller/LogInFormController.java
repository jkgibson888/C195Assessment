package Controller;

import DAO.UserDaoImpl;
import Model.User;

import Utility.Timezone;
import com.sun.java.accessibility.util.Translator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LogInFormController implements Initializable {

    //resource bundle
    private ResourceBundle rb = ResourceBundle.getBundle("language_files/rb", Locale.getDefault());

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

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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

    @FXML
    private Label zoneLabel;

    @FXML
    private Label userNameLbl;

    @FXML
    private Button loginBtn;

    @FXML
    private Label passwordLbl;

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
            System.out.println("in login btnpress....");
         try {
            File loginTracker = new File("login_activity.txt");
            ObservableList<User> allUsers = UserDaoImpl.getAllUsers();
            boolean userFound = false;
            boolean correctPassword = false;
            for(User user: allUsers){
                if(userNameTextField.getText().contentEquals(user.getUserName())) {

                    userFound = true;

                    if (passwordTextField.getText().contentEquals(user.getPassword())) {
                        currentUser = new User(user.getUserId(), user.getUserName(), user.getPassword());
                        System.out.println(currentUser);
                        correctPassword = true;

                        if (currentUser.getUserName().contentEquals("admin")) {
                            ChangeScene(event, "/Testing/UserTestTable.fxml");
                        } else {
                            System.out.println("In the else.......");
                            ChangeScene(event, "/View/MainForm.fxml");
                        }

                        //add successful login to login tracker
                        if(loginTracker.createNewFile()){
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                            FileWriter writer = new FileWriter("login_activity.txt", true);
                            writer.append("Successful login by: " + currentUser.getUserName() + " on " + timestamp + "\n");
                            writer.close();

                        }
                        else{
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            FileWriter writer = new FileWriter("login_activity.txt", true);
                            writer.append("Successful login by: " + currentUser.getUserName() + " on " + timestamp + "\n");
                            writer.close();

                        }

                    }

                }

            }

             if(userFound && !correctPassword){
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setHeaderText(rb.getString("header"));
                 alert.setContentText(rb.getString("error1"));
                 alert.showAndWait();

                 // insert unsuccessful login attempt to tracker
                 if(loginTracker.createNewFile()){
                     Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                     FileWriter writer = new FileWriter("login_activity.txt", true);
                     writer.append("Unsuccessful login by: " + userNameTextField.getText() + " on " + timestamp + " Reason: incorrect password\n");
                     writer.close();

                 }
                 else{
                     Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                     FileWriter writer = new FileWriter("login_activity.txt", true);
                     writer.append("Unsuccessful login by: " + userNameTextField.getText() + " on " + timestamp + " Reason: incorrect password\n");
                     writer.close();

                 }
             }
            if(!userFound) {

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setHeaderText(rb.getString("header"));
                alert.setContentText(rb.getString("error2"));
                alert.show();


                    alert.showAndWait();
                    if(Locale.getDefault().getLanguage().equals("en")) {
                        Text errorText = new Text(rb.getString("error"));
                        System.out.println(rb.getString("error"));
                        errorTextFlow.getChildren().add(errorText);
                    }

                //insert unsuccessful login attempt to tracker
                if(loginTracker.createNewFile()){
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    FileWriter writer = new FileWriter("login_activity.txt", true);
                    writer.append("Unsuccessful login by unknown user on " + timestamp + "\n");
                    writer.close();
                }
                else{
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    FileWriter writer = new FileWriter("login_activity.txt", true);
                    writer.append("Unsuccessful login by unknown user on " + timestamp + "\n");
                    writer.close();

                }
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

        this.rb = ResourceBundle.getBundle("language_files/rb", Locale.getDefault());
        userNameLbl.setText(this.rb.getString("user"));
        loginBtn.setText(this.rb.getString("button"));
        passwordLbl.setText(this.rb.getString("password"));
        zoneLabel.setText(ZoneId.systemDefault().toString());

    }


}
