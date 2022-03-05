package DAO;

import Controller.LogInFormController;
import Controller.MainFormController;
import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentDaoImpl {

    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception {

        //ObservableList to be returned containing all the appointments for a customer
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        //connect to the database
        JDBC.openConnection();

        //sql statement and database query to get all appointments in the database
        String firstSelect = "select * from appointments";
        ///String join = " INNER JOIN appointments ON customers.Customer_ID =  appointments.Customer_ID";
        String sqlStatement = firstSelect;

        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while (result.next()) {
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
            int userId = result.getInt("User_ID");

            Appointment userResult = new Appointment(appointmentId, title, description, location, type, startTime, endTime, createdBy, createDate, customerId, contactId, userId);
            allAppointments.add(userResult);


        }
        //close database connection
        JDBC.closeConnection();
        return allAppointments;
    }

    public static ObservableList<Appointment> getCustomerAppointments(Customer customer) throws SQLException, Exception{

        //ObservableList to be returned containing all the appointments for a customer
        ObservableList<Appointment> allCustomerAppointments= FXCollections.observableArrayList();

        //connect to the database
        JDBC.openConnection();

        //sql statement and database query to get all appointments in the database
        String firstSelect = "select * from appointments";
        ///String join = " INNER JOIN appointments ON customers.Customer_ID =  appointments.Customer_ID";
        String sqlStatement= firstSelect;

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
            int userId = result.getInt("User_ID");

            Appointment userResult = new Appointment(appointmentId, title,description, location, type, startTime, endTime,createdBy, createDate, customerId, contactId, userId);
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
    public static void insertAppointment(Customer customer, Appointment appointment) throws SQLException {

        //connect to the database

        //FIX ME! contact id
        //sql statement and query to insert new customer into database
        /*String insert = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) ";
        String values1 = "VALUES(\'" + appointment.getTitle() + "\', \'" + appointment.getDescription() + "\', \'" + appointment.getLocation() + "\', \'" + appointment.getType() + "\', ";
        String values2 = "\'" + appointment.getStartTime() + "\', \'" + appointment.getEndTime() + "\', NOW(), \'" + LogInFormController.getCurrentUser().getUserName() + "\', NOW(), \'";
        String values3 = LogInFormController.getCurrentUser().getUserName() + "\', \'" + customer.getCustomerId() + "\', \'" + appointment.getCustomerId() + "\', " + appointment.getContactId() + ")";
        String sqlStatement = insert + values1 + values2 + values3;*/


            String query = "INSERT INTO appointments ("
                    + " Title,"
                    + " Description,"
                    + " Location,"
                    + " Type,"
                    + " Start,"
                    + " End,"
                    + " Create_Date,"
                    + " Created_By,"
                    + " Last_Update,"
                    + " Last_Updated_By,"
                    + " Customer_ID,"
                    + " User_ID,"
                    + " Contact_ID) VALUES ("
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try {

                JDBC.openConnection();
                // set all the preparedstatement parameters
                PreparedStatement st = JDBC.connection.prepareStatement(query);
                st.setString(1, appointment.getTitle());
                st.setString(2, appointment.getDescription());
                st.setString(3, appointment.getLocation());
                st.setString(4, appointment.getType());
                st.setTimestamp(5, appointment.getStartTime());
                st.setTimestamp(6, appointment.getEndTime());
                st.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                st.setString(8, LogInFormController.getCurrentUser().getUserName());
                st.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                st.setString(10, LogInFormController.getCurrentUser().getUserName());
                st.setInt(11, appointment.getCustomerId());
                st.setInt(12, LogInFormController.getCurrentUser().getUserId());
                st.setInt(13, appointment.getContactId());

                // execute the preparedstatement insert
                st.executeUpdate();
                st.close();
            }
            catch (SQLException se)
            {
                // log exception
                throw se;
            }

            JDBC.closeConnection();
        }

    //method to update customers appointment

    public static void updateAppointment(Customer customer, Appointment appointment) throws Exception {

        /*connect to database
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
        String userId = "User_ID = " + appointment.getUserId() + ", ";
        String contactId = "Contact_ID = " + appointment.getContactId();
        String where = " WHERE Appointment_ID = " + appointment.getAppointmentId();

        System.out.println("current appointment id " + appointment.getAppointmentId());
        System.out.println(appointment.getAppointmentId());

        String sqlStatement = table + title + description + location + type + start + end + setLastUpdate + setUpdatedBy + customerId + userId + contactId + where;
        Query.makeQuery(sqlStatement);

        //close database connection
        JDBC.closeConnection();*/

        String query = "UPDATE appointments SET"
                + " Title = ?,"
                + " Description = ?,"
                + " Location = ?,"
                + " Type = ?,"
                + " Start = ?,"
                + " End = ?,"
                + " Last_Update = ?,"
                + " Last_Updated_By = ?,"
                + " Customer_ID = ?,"
                + " User_ID = ?,"
                + " Contact_ID = ?"
                + " WHERE Appointment_ID = ?";

        try {

            JDBC.openConnection();
            // set all the preparedstatement parameters
            PreparedStatement st = JDBC.connection.prepareStatement(query);
            st.setString(1, appointment.getTitle());
            st.setString(2, appointment.getDescription());
            st.setString(3, appointment.getLocation());
            st.setString(4, appointment.getType());
            st.setTimestamp(5, appointment.getStartTime());
            st.setTimestamp(6, appointment.getEndTime());
            st.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            st.setString(8, LogInFormController.getCurrentUser().getUserName());
            st.setInt(9, appointment.getCustomerId());
            st.setInt(10, LogInFormController.getCurrentUser().getUserId());
            st.setInt(11, appointment.getContactId());
            st.setInt(12, appointment.getAppointmentId());

            // execute the preparedstatement insert
            st.executeUpdate();
            st.close();
        }
        catch (SQLException se)
        {
            // log exception
            throw se;
        }

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
