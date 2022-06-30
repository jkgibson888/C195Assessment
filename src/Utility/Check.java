package Utility;

import DAO.AppointmentDaoImpl;
import Model.Appointment;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import java.time.LocalDateTime;

/**
 * Class to check appointment times and make sure there are no overlaps
 * @author Joshua Gibson
 */
public class Check {

    private ObservableList<Text> errorText = FXCollections.observableArrayList();
    private ObservableList<Appointment> allAppointments = AppointmentDaoImpl.getAllAppointments();

    public Check() throws Exception {
    }

    /**
     * Checks input start and end time to make sure there is no overlap with any appointment associated with a user.
     * @param startTime Start time to be checked.
     * @param endTime End time to be checked.
     * @param user User to be checked.
     * @return Observable list of errors if a proposed appointments times overlaps time with a currently scheduled appointment.
     */
    public ObservableList<Text> CheckTimes(LocalDateTime startTime, LocalDateTime endTime, User user){


        if(startTime.isAfter(endTime)){
            Text error = new Text("End time must be after start time");
            errorText.add(error);
        }

        for(Appointment app: allAppointments){
            LocalDateTime appStart = app.getStartTime().toLocalDateTime();
            LocalDateTime appEnd = app.getEndTime().toLocalDateTime();

            if(startTime.toLocalDate().isEqual(appStart.toLocalDate())) {

                if (startTime.equals(appStart) && endTime.isAfter(appEnd) && user.getUserId() == app.getUserId()) {
                    Text error = new Text("Times overlap with appointment number " + app.getAppointmentId() + "\n");
                    errorText.add(error);
                }

                if (startTime.isAfter(appStart) && startTime.isBefore(appEnd) && startTime.isBefore(endTime) && endTime.isAfter(appEnd) && user.getUserId() == app.getUserId()) {
                    Text error = new Text("Times overlap with appointment number " + app.getAppointmentId() + "\n");
                    errorText.add(error);
                }

                if (startTime.equals(appStart) && endTime.equals(appEnd) && user.getUserId() == app.getUserId()) {
                    Text error = new Text("Times overlap with appointment number " + app.getAppointmentId() + "\n");
                    errorText.add(error);
                }

                if (startTime.isAfter(appStart) && startTime.isBefore(appEnd) && startTime.isBefore(endTime) && endTime.equals(appEnd) && user.getUserId() == app.getUserId()) {
                    Text error = new Text("Times overlap with appointment number " + app.getAppointmentId() + "\n");
                    errorText.add(error);
                }

                if (startTime.isBefore(appStart) && endTime.equals(appEnd) && user.getUserId() == app.getUserId()) {
                    Text error = new Text("Times overlap with appointment number " + app.getAppointmentId() + "\n");
                    errorText.add(error);
                }

                if (startTime.isBefore(appStart) && endTime.isBefore(appEnd) && endTime.isAfter(appStart) && endTime.isAfter(startTime) && user.getUserId() == app.getUserId()) {
                    Text error = new Text("Times overlap with appointment number " + app.getAppointmentId() + "\n");
                    errorText.add(error);
                }

                if (startTime.isBefore(appStart) && endTime.isAfter(appEnd) && user.getUserId() == app.getUserId()) {
                    Text error = new Text("Times overlap with appointment number " + app.getAppointmentId() + "\n");
                    errorText.add(error);
                }
            }

        }


        return errorText;

    }

    public void emptyList(){
        errorText.clear();
    }

    public ObservableList<Text> getErrorText() {
        return errorText;
    }
}
