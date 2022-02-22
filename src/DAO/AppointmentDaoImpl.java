package DAO;

import Controller.LogInFormController;
import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentDaoImpl {

    public static ObservableList<Appointment> getCustomerAppointments(Customer customer) throws SQLException, Exception{

        //ObservableList to be returned containing all the appointments for a customer
        ObservableList<Appointment> allCustomerAppointments= FXCollections.observableArrayList();

        //connect to the database
        JDBC.openConnection();

        //sql statement and database query to get all appointments in the database
        String firstSelect = "select * from customers";
        String join = " INNER JOIN appointments ON customers.Customer_ID =  appointments.Customer_ID";
        String sqlStatement= firstSelect + join;

        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int appointmentId = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String location = result.getString("Location");
            String type = result.getString("Type");
            Timestamp startTime = result.getTimestamp("Start"); //works?
            Timestamp endTime = result.getTimestamp("End"); //works?
            String createdBy = result.getString("Created_By");
            Timestamp createDate = result.getTimestamp("Create_Date"); //works?
            int customerId = result.getInt("Customer_ID");
            int contactId = result.getInt("Contact_ID");

            Appointment userResult = new Appointment(appointmentId, title,description, location, type, startTime, endTime,createdBy, createDate, customerId, contactId);
            allCustomerAppointments.add(userResult);
        }

        //get appointments specific to the customer
        ObservableList<Appointment> thisCustomerAppointments = FXCollections.observableArrayList();
        for(Appointment app: allCustomerAppointments){
            if(app.getCustomerId() == customer.getCustomerId()) {
                thisCustomerAppointments.add(app);
            }
        }

        //close database connection
        JDBC.closeConnection();
        return thisCustomerAppointments;
    }

    //method to add appointment
    public static void insertAppointment(Customer customer, Appointment appointment){

        //connect to the database
        JDBC.openConnection();
        //FIX ME! contact id
        //sql statement and query to insert new customer into database
        String insert = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) ";
        String values1 = "VALUES(\'" + appointment.getTitle() + "\', \'" + appointment.getDescription() + "\', \'" + appointment.getLocation() + "\', \'" + appointment.getType() + "\', ";
        String values2 = "\'" + appointment.getStartTime() + "\', \'" + appointment.getEndTime() + "\', NOW(), \'" + LogInFormController.getCurrentUser().getUserName() + "\', NOW(), \'";
        String values3 = LogInFormController.getCurrentUser().getUserName() + "\', \'" + customer.getCustomerId() + "\', \'" + LogInFormController.getCurrentUser().getUserId() + "\', " + appointment.getContactId() + ")";
        String sqlStatement = insert + values1 + values2 + values3;
        Query.makeQuery(sqlStatement);

        //close database connection
        JDBC.closeConnection();

    }

    //method to update customers appointment

    public static void updateAppointment(Customer customer, Appointment appointment) throws Exception {

        //connect to database
        JDBC.openConnection();
        //sql statement and query to update a customer
        String table = "UPDATE appointments ";
        String title = "SET Title = \'" + appointment.getTitle() + "\', ";
        String description = "Description = \'" + appointment.getDescription() + "\', ";
        String location = "Location = \'" + appointment.getLocation() + "\', ";
        String type = "Type = \'" + appointment.getType() + "\', ";
        String start = "Start = \'" + appointment.getStartTime() + "\', ";
        String end = "End = \'" + appointment.getEndTime() + "\', ";
        String setLastUpdate = "Last_Update = NOW(), ";
        String setUpdatedBy = "Last_Updated_By = \'" + LogInFormController.getCurrentUser().getUserName() + "\', ";
        String customerId = "Customer_ID = " + customer.getCustomerId() + ", ";
        String userId = "User_ID = " + LogInFormController.getCurrentUser().getUserId() + ", ";
        String contactId = "Contact_ID = " + appointment.getContactId();
        String where = " WHERE Appointment_ID = " + appointment.getAppointmentId();

        System.out.println("current appointment id " + appointment.getAppointmentId());
        System.out.println(appointment.getAppointmentId());

        String sqlStatement = table + title + description + location + type + start + end + setLastUpdate + setUpdatedBy + customerId + userId + contactId + where;
        Query.makeQuery(sqlStatement);

        //close database connection
        JDBC.closeConnection();
    }

    //method to delete appointment
    public static void deleteAppointment(Appointment appointment){

        //connect to database
        JDBC.openConnection();

        //sql statement and query to delete user from user table
        String sqlStatement = "DELETE FROM appointments WHERE Appointment_ID = \'" + appointment.getAppointmentId() +"\'";
        Query.makeQuery(sqlStatement);

        //close connection
        JDBC.closeConnection();
    }
}
