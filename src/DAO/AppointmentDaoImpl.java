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
            Timestamp createDate = result.getTimestamp("Create_Date"); //works?
            int customerId = result.getInt("Customer_ID");
            int contactId = result.getInt("Contact_ID");

            Appointment userResult = new Appointment(appointmentId, title,description, location, type, startTime, endTime, createDate, customerId, contactId);
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
        String insert = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID) ";
        String values1 = "VALUES(\'" + appointment.getTitle() + "\', \'" + appointment.getDescription() + "\', \'" + appointment.getLocation() + "\', \'" + appointment.getType() + "\', ";
        String values2 = "\'" + appointment.getStartTime() + "\', \'" + appointment.getEndTime() + "\', NOW(), \'" + LogInFormController.getCurrentUser().getUserName() + "\', NOW(), \'";
        String values3 = LogInFormController.getCurrentUser().getUserName() + "\', \'" + customer.getCustomerId() + "\', \'" + LogInFormController.getCurrentUser().getUserId() + "\')";
        String sqlStatement = insert + values1 + values2 + values3;
        Query.makeQuery(sqlStatement);

        //close database connection
        JDBC.closeConnection();

    }

    //method to update customers appointment

    public static void updateAppointment(Customer customer, Appointment appointment) throws Exception {

        //connect to database
        JDBC.openConnection();
//FINISH
       /* //sql statement and query to update a customer
        String table = "UPDATE appointments ";
        String title = "SET Customer_Name = \'" + customer.getCustomerName() + "\', ";
        String description = "Address = \'" + customer.getAddress() + "\', ";
        String location = "Postal_Code = \'" + customer.getPostalCode() + "\', ";
        String type = "Phone = \'" + customer.getPhoneNumber() + "\', ";
        Date
        String setLastUpdate = "Last_Update = NOW(), ";
        String setUpdatedBy = "Last_Updated_By = \'" + LogInFormController.getCurrentUser().getUserName() + "\', ";
        String setDivisionId = "Division_Id = " + customer.getDivisionId();
        String where = " WHERE Customer_ID = " + customer.getCustomerId();

        String sqlStatement = table + setCustomerName + setCustomerAddress + setCustomerPostal + setCustomerPhone + setLastUpdate + setUpdatedBy + setDivisionId + where;
        Query.makeQuery(sqlStatement);

        //close database connection
        JDBC.closeConnection();*/
    }

    //method to delete appointment
    public static void deleteCustomer(Appointment appointment){

        //connect to database
        JDBC.openConnection();

        //sql statement and query to delete user from user table
        String sqlStatement = "DELETE FROM appointments WHERE Appointment_ID = \'" + appointment.getAppointmentId() +"\'";
        Query.makeQuery(sqlStatement);

        //close connection
        JDBC.closeConnection();
    }
}
