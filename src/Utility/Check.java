package Utility;

import DAO.AppointmentDaoImpl;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Check {

    private ObservableList<Text> errorText = FXCollections.observableArrayList();
    private ObservableList<Appointment> allAppointments = AppointmentDaoImpl.getAllAppointments();

    public Check() throws Exception {
    }

    public ObservableList<Text> CheckTimes(LocalDateTime startTime, LocalDateTime stopTime){


        if(startTime.isAfter(stopTime)){
            Text error = new Text("End time must be after start time");
            errorText.add(error);
        }

        for(Appointment app: allAppointments){

        }


        return errorText;

    }
}
