/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package c195assessment;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

import DAO.JDBC;
import Utility.Timezone;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author techt
 */
public class C195Assessment extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        /*test
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.of(7, 0);

        LocalDateTime current = LocalDateTime.of(today, now);
        System.out.println(current);
        LocalDateTime local = Timezone.LocalToEastern(current);
        System.out.println(local);
        LocalDateTime utc = Timezone.LocalToUtc(local);
        System.out.println(utc);
        LocalDateTime back = Timezone.UTCToLocalTime(utc);
        System.out.println(back);*/

        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
    
    @Override
    public void start(Stage stage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogInForm.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
}
