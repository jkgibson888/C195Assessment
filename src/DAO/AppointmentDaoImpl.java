package DAO;

import Controller.LogInFormController;
import Controller.MainFormController;
import Model.Appointment;
import Model.Customer;
import Model.MonthTypeApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Joshua Gibson
 */
public class AppointmentDaoImpl {
    /**
     * Retrieves all appointments from the database and places them into an observable list.
     * @return Observable list that contains all the appointments from the database.
     * @throws SQLException
     * @throws Exception
     */
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

    /**
     * Retrieves appointments from the database that are associated with the customer being passed into the method.
     * @param customer The customer whose appointments are to be retrieved.
     * @return
     * @throws SQLException
     * @throws Exception
     */
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

    /**
     * Inserts an appointment into the database.
     * @param appointment The appointment object that contains the information that will be inserted into the database.
     * @throws SQLException
     */
    public static void insertAppointment(Appointment appointment) throws SQLException {

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

    /**
     * Modifies an appointment in the database.
     * @param appointment The appointment object that contains the information that will be changed to in the database.
     * @throws SQLException
     */
    public static void updateAppointment(Appointment appointment) throws Exception {

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

    /**
     * Deletes an appointment from the database.
     * @param appointment The appointment to be deleted.
     */
    public static void deleteAppointment(Appointment appointment){

        //connect to database
        JDBC.openConnection();

        //sql statement and query to delete user from user table
        String sqlStatement = "DELETE FROM appointments WHERE Appointment_ID = \'" + appointment.getAppointmentId() +"\'";
        Query.makeQuery(sqlStatement);



        //close connection
        JDBC.closeConnection();
    }

    /**
     * Returns an observable list of Month type appointments and their counts.
     * @return List of month types and their counts for the month type report.
     */
    public static ObservableList<MonthTypeApp> getMonthTypeReport(){

        JDBC.openConnection();
        String sql = "Select MonthName(start) AS Month_Name, Type, count(Type) AS cnt FROM appointments Group By MonthName(start), type ORDER BY Month_Name";
        ObservableList<MonthTypeApp> monthTypes = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                monthTypes.add(new MonthTypeApp(rs.getString("Month_Name"), rs.getString("Type"), String.valueOf(rs.getInt("cnt"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JDBC.closeConnection();

        return monthTypes;

    }
}
