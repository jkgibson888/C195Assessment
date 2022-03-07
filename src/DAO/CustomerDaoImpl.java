package DAO;

import Controller.LogInFormController;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Joshua Gibson
 */
public class CustomerDaoImpl {
    /**
     * Retrieves all customers from the database and places them into an observable list.
     * @return Observable list that contains all the customers from the database.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException, Exception{

        //ObservableList to be returned containing all the users
        ObservableList<Customer> allCustomers= FXCollections.observableArrayList();

        //connect to the database
        JDBC.openConnection();

        //sql statement and database query
        String select ="select * from customers ";
        String join = "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";
        String sqlStatement = select + join;
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int customerId = result.getInt("Customer_ID");
            String customerName = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postalCode = result.getString("Postal_Code");
            String phoneNumber = result.getString("Phone");
            Timestamp createDate = result.getTimestamp("Create_Date");
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            String updatedBy = result.getString("Last_Updated_By");
            int divisionId = result.getInt("Division_ID");
            String division = result.getString("Division");
            String fullAddress = address + ", " + division;
            Customer userResult = new Customer(customerId, customerName, address, postalCode, phoneNumber, createDate, createdBy, lastUpdate, updatedBy, divisionId, division, fullAddress);
            allCustomers.add(userResult);
        }

        //close database connection
        JDBC.closeConnection();
        return allCustomers;
    }

    //method to add customers
    /**
     * Inserts a customer into the database.
     * @param customer The customer object that contains the information that will be inserted into the database.
     * @throws SQLException
     */
    public static void insertCustomer(Customer customer){

        //connect to the database
        JDBC.openConnection();

        //sql statement and query to insert new customer into database
        String insert = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) ";
        String values = "VALUES(\'" + customer.getCustomerName() + "\', \'" + customer.getAddress() + "\', \'" + customer.getPostalCode()+ "\', \'" + customer.getPhoneNumber() + "\', NOW(), \'" + LogInFormController.getCurrentUser().getUserName() + "\', NOW(), \'" + LogInFormController.getCurrentUser().getUserName()+ "\', \'" + customer.getDivisionId() + "\')";
        String sqlStatement = insert + values;
        Query.makeQuery(sqlStatement);

        //close database connection
        JDBC.closeConnection();

    }

    //method to update Appointment
    /**
     * Modifies a customer in the database.
     * @param customer The customer object that contains the information that will be changed to in the database.
     * @throws SQLException
     */
    public static void updateCustomer(Customer customer) throws Exception {

        //connect to database
        JDBC.openConnection();

        //sql statement and query to update a customer
        String table = "UPDATE customers ";
        String setCustomerName = "SET Customer_Name = \'" + customer.getCustomerName() + "\', ";
        String setCustomerAddress = "Address = \'" + customer.getAddress() + "\', ";
        String setCustomerPostal = "Postal_Code = \'" + customer.getPostalCode() + "\', ";
        String setCustomerPhone = "Phone = \'" + customer.getPhoneNumber() + "\', ";
        String setLastUpdate = "Last_Update = NOW(), ";
        String setUpdatedBy = "Last_Updated_By = \'" + LogInFormController.getCurrentUser().getUserName() + "\', ";
        String setDivisionId = "Division_Id = " + customer.getDivisionId() + " ";
        String where = "WHERE Customer_ID = " + customer.getCustomerId();

        System.out.println("updating customer.........");

        String sqlStatement = table + setCustomerName + setCustomerAddress + setCustomerPostal + setCustomerPhone + setLastUpdate + setUpdatedBy + setDivisionId + where;
        Query.makeQuery(sqlStatement);

        //close database connection
        JDBC.closeConnection();
    }

    //method to delete customer
    /**
     * Deletes a customer from the database.
     * @param customer The customer to be deleted.
     */
    public static void deleteCustomer(Customer customer){

        //connect to database
        JDBC.openConnection();

        //sql statement and query to delete user from user table
        String sqlStatement = "DELETE FROM customers WHERE Customer_ID = \'" + customer.getCustomerId() +"\'";
        Query.makeQuery(sqlStatement);

        //close connection
        JDBC.closeConnection();
    }

    /*public static Customer searchCustomer(int customerNum) throws Exception {
        //connect to database
        JDBC.openConnection();

        //sql statement and query to retrieve customer from database
        String select = "Select * FROM customers WHERE Customer_ID = " + customerNum;
        String join = " INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";
        String sqlStatement = select + join;
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        Customer userResult = null;
        while (result.next()) {
            int customerId = result.getInt("Customer_ID");
            String customerName = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postalCode = result.getString("Postal_Code");
            String phoneNumber = result.getString("Phone");
            Timestamp createDate = result.getTimestamp("Create_Date");
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            String updatedBy = result.getString("Last_Updated_By");
            int divisionId = result.getInt("Division_ID");
            String division = result.getString("Division");
            String fullAddress = address + ", " + division;
            userResult = new Customer(customerId, customerName, address, postalCode, phoneNumber, createDate, createdBy, lastUpdate, updatedBy, divisionId, division, fullAddress);
        }

        //close connection
        JDBC.closeConnection();


        return userResult;

    }*/
}
