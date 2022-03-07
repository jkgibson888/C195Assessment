package DAO;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Joshua Gibson
 */
public class ContactDaoImp {
    /**
     * Retrieves all contacts from the database and places them into an observable list.
     * @return Observable list that contains all the contacts from the database.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException, Exception{

        //ObservableList to be returned containing all the countries
        ObservableList<Contact> allContacts= FXCollections.observableArrayList();

        //connect to the database
        JDBC.openConnection();

        //sql statement and database query
        String sqlStatement="select * from contacts";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int contactId = result.getInt("Contact_ID");
            String contactName = result.getString("Contact_Name");
            String email = result.getString("Email");

            Contact contact = new Contact(contactId, contactName, email);
            allContacts.add(contact);
        }

        //close database connection
        JDBC.closeConnection();
        return allContacts;
    }
}
